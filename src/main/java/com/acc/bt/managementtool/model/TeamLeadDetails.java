package com.acc.bt.managementtool.model;

import java.util.List;

public class TeamLeadDetails {
	
	private List<Resource> fixedResources;
	private List<Resource> pooledResources;
	public List<Resource> getFixedResources() {
		return fixedResources;
	}
	public void setFixedResources(List<Resource> fixedResources) {
		this.fixedResources = fixedResources;
	}
	public List<Resource> getPooledResources() {
		return pooledResources;
	}
	public void setPooledResources(List<Resource> pooledResources) {
		this.pooledResources = pooledResources;
	}

}
