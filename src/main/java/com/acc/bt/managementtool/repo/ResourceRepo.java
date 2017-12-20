package com.acc.bt.managementtool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.acc.bt.managementtool.model.Resource;

public interface ResourceRepo extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource>  {
	
	Resource findByIuser(long iuser);
	Resource findById(Integer id);
}
