package com.acc.bt.managementtool.model;

public class DomainDetails {
	private Domain domain;
	private int totalCapacity;
	private int todaysCapacity;
	private int pendingRequests;
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public int getTotalCapacity() {
		return totalCapacity;
	}
	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}
	public int getTodaysCapacity() {
		return todaysCapacity;
	}
	public void setTodaysCapacity(int todaysCapacity) {
		this.todaysCapacity = todaysCapacity;
	}
	public int getPendingRequests() {
		return pendingRequests;
	}
	public void setPendingRequests(int pendingRequests) {
		this.pendingRequests = pendingRequests;
	}
	
}
