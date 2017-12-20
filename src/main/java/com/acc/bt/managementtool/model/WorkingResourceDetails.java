package com.acc.bt.managementtool.model;

public class WorkingResourceDetails {
	
	private String name;
	private String primaryDomain;
	private String assignedDomain;
	private String requestedBy;
	private char status;
	
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
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
