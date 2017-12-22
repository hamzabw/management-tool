package com.acc.bt.managementtool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.acc.bt.managementtool.model.ResourceRole;

public interface ResourceRoleRepo extends JpaRepository<ResourceRole, Long>, JpaSpecificationExecutor<ResourceRole> {

}
