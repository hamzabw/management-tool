package com.acc.bt.managementtool.model;

import java.util.List;

public class ReleaseManagerDetails {
	private List<PooledResource> pooledResources;
	private List<DomainDetails> domainDetails;

	public List<PooledResource> getPooledResources() {
		return pooledResources;
	}

	public void setPooledResources(List<PooledResource> pooledResources) {
		this.pooledResources = pooledResources;
	}

	public List<DomainDetails> getDomainDetails() {
		return domainDetails;
	}

	public void setDomainDetails(List<DomainDetails> domainDetails) {
		this.domainDetails = domainDetails;
	}
}
