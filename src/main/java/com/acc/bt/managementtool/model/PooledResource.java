package com.acc.bt.managementtool.model;

public class PooledResource {
	private int id;
	private String name;
	private long iuser;
	private char status;
	private String requestedBy;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
}
