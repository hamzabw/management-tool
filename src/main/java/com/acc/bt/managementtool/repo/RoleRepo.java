package com.acc.bt.managementtool.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.acc.bt.managementtool.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
	Role findById(Integer id);
}
