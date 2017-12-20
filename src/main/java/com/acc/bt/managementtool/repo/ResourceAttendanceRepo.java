package com.acc.bt.managementtool.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.acc.bt.managementtool.model.Resource;
import com.acc.bt.managementtool.model.ResourceAttendance;

public interface ResourceAttendanceRepo extends JpaRepository<ResourceAttendance, Long>, JpaSpecificationExecutor<ResourceAttendance> {

	ResourceAttendance findByResourceAndDate(Resource resource, Date date);
}
