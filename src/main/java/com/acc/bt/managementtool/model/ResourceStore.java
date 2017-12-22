package com.acc.bt.managementtool.model;

import java.util.List;

public class ResourceStore {
	private String name;
	private long iuser;
	private int primaryDomain;
	private List<Integer> secondaryDomains;
	private int roleId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getIuser() {
		return iuser;
	}
	public void setIuser(long iuser) {
		this.iuser = iuser;
	}
	public int getPrimaryDomain() {
		return primaryDomain;
	}
	public void setPrimaryDomain(int primaryDomain) {
		this.primaryDomain = primaryDomain;
	}
	public List<Integer> getSecondaryDomains() {
		return secondaryDomains;
	}
	public void setSecondaryDomains(List<Integer> secondaryDomains) {
		this.secondaryDomains = secondaryDomains;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
}
