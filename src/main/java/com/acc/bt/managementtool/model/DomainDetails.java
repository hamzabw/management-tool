package com.acc.bt.managementtool.model;

public class DomainDetails {
	private Domain domain;
	private int totalCapacity;
	private int releasedResources;
	
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
	public int getReleasedResources() {
		return releasedResources;
	}
	public void setReleasedResources(int releasedResources) {
		this.releasedResources = releasedResources;
	}
	
}
