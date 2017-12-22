package com.acc.bt.managementtool.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.acc.bt.managementtool.model.Domain;
import com.acc.bt.managementtool.model.DomainDetails;
import com.acc.bt.managementtool.model.PooledResource;
import com.acc.bt.managementtool.model.ReleaseManagerDetails;
import com.acc.bt.managementtool.model.Resource;
import com.acc.bt.managementtool.model.ResourceAttendance;
import com.acc.bt.managementtool.model.ResourceDetails;
import com.acc.bt.managementtool.model.ResourceDomain;
import com.acc.bt.managementtool.model.ResourcePool;
import com.acc.bt.managementtool.model.ResourceRole;
import com.acc.bt.managementtool.model.ResourceStore;
import com.acc.bt.managementtool.model.TeamLeadDetails;
import com.acc.bt.managementtool.model.WorkingResourceDetails;
import com.acc.bt.managementtool.repo.DomainRepo;
import com.acc.bt.managementtool.repo.ResourceAttendanceRepo;
import com.acc.bt.managementtool.repo.ResourceDomainRepo;
import com.acc.bt.managementtool.repo.ResourcePoolRepo;
import com.acc.bt.managementtool.repo.ResourceRepo;
import com.acc.bt.managementtool.repo.ResourceRoleRepo;
import com.acc.bt.managementtool.repo.RoleRepo;
import com.acc.bt.managementtool.util.CustomRsqlVisitor;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

@RestController
public class ManagementToolController {

	@Autowired
	private ResourceRepo resourceRepo;
	
	@Autowired
	private ResourceAttendanceRepo resourceAttendRepo;
	
	@Autowired
	private ResourcePoolRepo resourcePoolRepo;
	
	@Autowired
	private ResourceDomainRepo resourceDomainRepo;
	
	@Autowired
	private DomainRepo domainRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ResourceRoleRepo resourceRoleRepo;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(method = RequestMethod.GET, value = "/resource/query")
	@ResponseBody
	public Page<Resource> findResourceByRsql(@RequestParam(value = "search") String search, Pageable pageable) {
		Node rootNode = new RSQLParser().parse(search);
	    Specification<Resource> spec = rootNode.accept(new CustomRsqlVisitor<Resource>());
	    return resourceRepo.findAll(spec, pageable);
	}
	
	@RequestMapping("/resource")
	public Map<String,Object> home() {
	    Map<String,Object> model = new HashMap<>();
	    model.put("id", UUID.randomUUID().toString());
	    model.put("content", "Hello World");
	    return model;
	}
	
	@RequestMapping("/user")
	public Principal user(Principal user) {
	   return user;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/resource/save")
	public void saveResource(@RequestBody ResourceStore resourceStore) {
		Resource resource = new Resource();
		resource.setIuser(resourceStore.getIuser());
		resource.setName(resourceStore.getName());
		resource = resourceRepo.save(resource);
		List<ResourceDomain> resourceDomains = new ArrayList<>();
		ResourceDomain rd = new ResourceDomain();
		rd.setResource(resource);
		rd.setDomainType('P');
		rd.setDomain(domainRepo.findById(resourceStore.getPrimaryDomain()));
		resourceDomains.add(rd);
		if(null != resourceStore.getSecondaryDomains() && !resourceStore.getSecondaryDomains().isEmpty()) {
			for(int id : resourceStore.getSecondaryDomains()) {
				ResourceDomain srd = new ResourceDomain();
				srd.setResource(resource);
				srd.setDomainType('S');
				srd.setDomain(domainRepo.findById(id));
				resourceDomains.add(srd);
			}
		}
		resourceDomainRepo.save(resourceDomains);
		ResourceRole role = new ResourceRole();
		role.setResource(resource);
		role.setRole(roleRepo.findById(resourceStore.getRoleId()));
		resourceRoleRepo.save(role);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/resource/attendance/{iuser}")
	public Resource markAttendance(@PathVariable long iuser) {
		Resource resource = resourceRepo.findByIuser(iuser);
		ResourceAttendance attendance = resourceAttendRepo.findByResourceAndDate(resource, getTodayDate());
		if(null == attendance) {
			attendance = new ResourceAttendance();
			attendance.setResource(resource);
			attendance.setDate(getTodayDate());
			resourceAttendRepo.save(attendance);
		}
		return resource;
	}
	
	@RequestMapping("/resource/pool/{iuser}")
	public List<PooledResource> getResourcePool(@PathVariable long iuser) {
		Resource resource = resourceRepo.findByIuser(iuser);
		Domain primaryDomain = getPrimaryDomain(resource.getResourceDomains());
		List<ResourcePool> resourcePools = resourcePoolRepo.findAllByDate(getTodayDate());
		List<PooledResource> pooledResources = new ArrayList<>();
		if(null != resourcePools && !resourcePools.isEmpty()) {
			for(ResourcePool rp : resourcePools) {
				Resource rs = rp.getResource();
				List<ResourceDomain> resourceDomains = rs.getResourceDomains();
				if(null != resourceDomains && !resourceDomains.isEmpty()) {
					for(ResourceDomain rd : resourceDomains) {
						if(rd.getDomain().equals(primaryDomain)) {
							PooledResource pr = new PooledResource();
							pr.setId(rs.getId());
							pr.setIuser(rs.getIuser());
							pr.setName(rs.getName());
							pr.setStatus(rp.getStatus());
							pr.setRequestedBy((rp.getRequestedBy() != null) ? rp.getRequestedBy().getName() : null);
							pooledResources.add(pr);
							break;
						}
					}
					
				}
			}
		}
		return pooledResources;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/resource/release/{iuser}")
	public List<ResourcePool> releaseResources(@RequestBody List<Integer> resourceIds, @PathVariable long iuser) {
		resourceRepo.findByIuser(iuser);
		List<ResourcePool> resourcePools = new ArrayList<>(); 
		if(null != resourceIds && !resourceIds.isEmpty()) {
			for(Integer id : resourceIds) {
				Resource rs = resourceRepo.findById(id);
				ResourcePool rp = new ResourcePool();
				rp.setResource(rs);
				rp.setDate(getTodayDate());
				rp.setStatus('A');
				rp = resourcePoolRepo.save(rp);
				resourcePools.add(rp);
			}
		}
		return resourcePools;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/resource/request/{iuser}")
	public List<ResourcePool> requestResources(@RequestBody List<Integer> resourceIds, @PathVariable long iuser) {
		Resource resource = resourceRepo.findByIuser(iuser);
		List<ResourcePool> resourcePools = new ArrayList<>(); 
		if(null != resourceIds && !resourceIds.isEmpty()) {
			for(Integer id : resourceIds) {
				Resource rs = resourceRepo.findById(id);
				ResourcePool rp = resourcePoolRepo.findByResourceAndDate(rs, getTodayDate());
				rp.setStatus('R');
				rp.setRequestedBy(resource);
				rp = resourcePoolRepo.save(rp);
				resourcePools.add(rp);
			}
		}
		return resourcePools;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/resource/approve/{iuser}")
	public List<ResourcePool> approveResources(@RequestBody List<Integer> resourceIds, @PathVariable long iuser) {
		resourceRepo.findByIuser(iuser);
		List<ResourcePool> resourcePools = new ArrayList<>(); 
		if(null != resourceIds && !resourceIds.isEmpty()) {
			for(Integer id : resourceIds) {
				Resource rs = resourceRepo.findById(id);
				ResourcePool rp = resourcePoolRepo.findByResourceAndDate(rs, getTodayDate());
				rp.setStatus('S');
				rp = resourcePoolRepo.save(rp);
				resourcePools.add(rp);
			}
		}
		return resourcePools;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/resource/reject/{iuser}")
	public List<ResourcePool> rejectResources(@RequestBody List<Integer> resourceIds, @PathVariable long iuser) {
		resourceRepo.findByIuser(iuser);
		List<ResourcePool> resourcePools = new ArrayList<>(); 
		if(null != resourceIds && !resourceIds.isEmpty()) {
			for(Integer id : resourceIds) {
				Resource rs = resourceRepo.findById(id);
				ResourcePool rp = resourcePoolRepo.findByResourceAndDate(rs, getTodayDate());
				rp.setStatus('J');
				rp = resourcePoolRepo.save(rp);
				resourcePools.add(rp);
			}
		}
		return resourcePools;
	}
	
	@RequestMapping("/resource/details/{iuser}")
	public ResourceDetails getResourceDetails(@PathVariable long iuser) {
		Resource resource = resourceRepo.findByIuser(iuser);
		ResourceDetails resourceDetails = new ResourceDetails();
		if(resource.getResourceRole().getRole().getId() == 1) {
			resourceDetails.setWrDetails(getWrDetails(resource));
		} else if(resource.getResourceRole().getRole().getId() == 2) {
			resourceDetails.setTlDetails(getTlDetails(resource));
		} else if(resource.getResourceRole().getRole().getId() == 3) {
			resourceDetails.setRmDetails(getRmDetails());
		} 
		return resourceDetails;
	}
	
	private ReleaseManagerDetails getRmDetails() {
		ReleaseManagerDetails rmDetails = new ReleaseManagerDetails();
		List<ResourcePool> resourcePools = resourcePoolRepo.findAllByDate(getTodayDate());
		List<PooledResource> pooledResources = new ArrayList<>();
		if(null != resourcePools && !resourcePools.isEmpty()) {
			for(ResourcePool rp : resourcePools) {
				Resource rs = rp.getResource();
				PooledResource pr = new PooledResource();
				pr.setId(rs.getId());
				pr.setIuser(rs.getIuser());
				pr.setName(rs.getName());
				pr.setStatus(rp.getStatus());
				pr.setRequestedBy((rp.getRequestedBy() != null) ? rp.getRequestedBy().getName() : null);
				pooledResources.add(pr);
			}
		}
		rmDetails.setPooledResources(pooledResources);
		List<Domain> domains = domainRepo.findAll();
		List<DomainDetails> domainDetails = new ArrayList<>();
		for(Domain domain : domains) {
			List<ResourceDomain> resourceDomains = resourceDomainRepo.findAllByDomainAndDomainType(domain, 'P');
			DomainDetails domDetails = new DomainDetails();
			domDetails.setDomain(domain);
			if(null != resourceDomains && !resourceDomains.isEmpty()) {
				int totalCapacity = resourceDomains.size();
				int resourceReleased = 0;
				domDetails.setTotalCapacity(totalCapacity);
				for(ResourceDomain rd : resourceDomains) {
					ResourcePool rp = resourcePoolRepo.findByResourceAndDate(rd.getResource(), getTodayDate());
					if(null != rp) {
						resourceReleased++;
					}
				}
				domDetails.setReleasedResources(resourceReleased);
			}
			domainDetails.add(domDetails);
		}
		rmDetails.setDomainDetails(domainDetails);
		return rmDetails;
	}
	
	private TeamLeadDetails getTlDetails(Resource resource) {
		TeamLeadDetails tlDetails = new TeamLeadDetails();
		List<ResourceDomain> resourceDomains = resourceDomainRepo.findAllByDomainAndDomainType(getPrimaryDomain(resource.getResourceDomains()), 'P');
		if(null != resourceDomains && !resourceDomains.isEmpty()) {
			List<Resource> fixedResources = new ArrayList<>();
			for(ResourceDomain domain : resourceDomains) {
				Resource res = domain.getResource();
				if(res.getIuser() != resource.getIuser()) {
					ResourcePool resourcePool = resourcePoolRepo.findByResourceAndDate(res, getTodayDate());
					if(null == resourcePool) {
						fixedResources.add(res);
					}
				}
			}
			tlDetails.setFixedResources(fixedResources);
		}
		List<ResourcePool> resourcePools = resourcePoolRepo.findAllByRequestedByAndStatusAndDate(resource, 'S', getTodayDate());
		if(null != resourcePools && !resourcePools.isEmpty()) {
			List<Resource> pooledResources = new ArrayList<>();
			for(ResourcePool rp : resourcePools) {
				pooledResources.add(rp.getResource());
			}
			tlDetails.setPooledResources(pooledResources);
		}
		return tlDetails;
	}
	
	private WorkingResourceDetails getWrDetails(Resource resource) {
		ResourcePool resourcePool = resourcePoolRepo.findByResourceAndDate(resource, getTodayDate());
		WorkingResourceDetails wrDetails = new WorkingResourceDetails();
		wrDetails.setPrimaryDomain(getPrimaryDomain(resource.getResourceDomains()).getName());
		if(null != resourcePool) {
			switch (resourcePool.getStatus()) {
			case 'A':
				wrDetails.setAssignedDomain("");
				wrDetails.setStatus('A');
				break;
			case 'R':
				wrDetails.setAssignedDomain(getPrimaryDomain(resourcePool.getRequestedBy().getResourceDomains()).getName());
				wrDetails.setStatus('R');
				wrDetails.setRequestedBy(resourcePool.getRequestedBy().getName());
				break;
			case 'S':
				wrDetails.setAssignedDomain(getPrimaryDomain(resourcePool.getRequestedBy().getResourceDomains()).getName());
				wrDetails.setStatus('S');
				wrDetails.setRequestedBy(resourcePool.getRequestedBy().getName());
				break;
			case 'J':
				wrDetails.setAssignedDomain(getPrimaryDomain(resourcePool.getRequestedBy().getResourceDomains()).getName());
				wrDetails.setStatus('J');
				wrDetails.setRequestedBy(resourcePool.getRequestedBy().getName());
				break;
			default:
				break;
			}
		} else {
			wrDetails.setAssignedDomain(wrDetails.getPrimaryDomain());
			wrDetails.setStatus('S');
		}
		wrDetails.setName(resource.getName());
		return wrDetails;
	}
	
	private Date getTodayDate() {
		Date date = new Date();
		String dateStr = sdf.format(date);
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date;
	}
	
	private Domain getPrimaryDomain(List<ResourceDomain> resourceDomains) {
		for(ResourceDomain rd : resourceDomains) {
			if(rd.getDomainType() == 'P') {
				return rd.getDomain();
			}
		}
		return null;
	}
	
}
