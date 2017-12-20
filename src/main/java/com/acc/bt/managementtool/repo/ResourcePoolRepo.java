package com.acc.bt.managementtool.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.acc.bt.managementtool.model.Resource;
import com.acc.bt.managementtool.model.ResourcePool;


public interface ResourcePoolRepo extends JpaRepository<ResourcePool, Long>, JpaSpecificationExecutor<ResourcePool> {
	
	ResourcePool findByResourceAndDate(Resource resource, Date date);
	List<ResourcePool> findAllByRequestedByAndStatusAndDate(Resource requestedBy, char status, Date date);
	List<ResourcePool> findAllByDate(Date date);
}
