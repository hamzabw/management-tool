package com.acc.bt.managementtool.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.acc.bt.managementtool.model.Domain;
import com.acc.bt.managementtool.model.ResourceDomain;

public interface ResourceDomainRepo extends JpaRepository<ResourceDomain, Long>, JpaSpecificationExecutor<ResourceDomain> {
	
	List<ResourceDomain> findAllByDomain(Domain domain);
}
