package com.acc.bt.managementtool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.acc.bt.managementtool.model.Domain;

public interface DomainRepo extends JpaRepository<Domain, Long>, JpaSpecificationExecutor<Domain> {
	
}
