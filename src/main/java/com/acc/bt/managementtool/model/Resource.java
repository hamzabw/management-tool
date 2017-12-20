package com.acc.bt.managementtool.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="resource")
public class Resource {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(length = 128)
	private String name;
	
	private Long iuser;

	@OneToMany(mappedBy = "resource", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Cascade({ org.hibernate.annotations.CascadeType.ALL })
	private List<ResourceDomain> resourceDomains;
	
	@OneToOne(mappedBy = "resource", orphanRemoval = true)
	private ResourceRole resourceRole;
	
	@OneToMany(mappedBy = "resource", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@Cascade({ org.hibernate.annotations.CascadeType.ALL })
	private List<ResourceAttendance> resourceAttendances;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getIuser() {
		return iuser;
	}

	public void setIuser(long iuser) {
		this.iuser = iuser;
	}

	public List<ResourceDomain> getResourceDomains() {
		return resourceDomains;
	}

	public void setResourceDomains(List<ResourceDomain> resourceDomains) {
		this.resourceDomains = resourceDomains;
	}

	public ResourceRole getResourceRole() {
		return resourceRole;
	}

	public void setResourceRole(ResourceRole resourceRole) {
		this.resourceRole = resourceRole;
	}

}
