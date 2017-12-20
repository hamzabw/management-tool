package com.acc.bt.managementtool.model;

public class WorkingResourceDetails {
	
	private String primaryDomain;
	private String assignedDomain;
	private String requestedBy;
	private ResourceStatus status;
	
	public String getPrimaryDomain() {
		return primaryDomain;
	}
	public void setPrimaryDomain(String primaryDomain) {
		this.primaryDomain = primaryDomain;
	}
	public String getAssignedDomain() {
		return assignedDomain;
	}
	public void setAssignedDomain(String assignedDomain) {
		this.assignedDomain = assignedDomain;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public ResourceStatus getStatus() {
		return status;
	}
	public void setStatus(ResourceStatus status) {
		this.status = status;
	}
}
