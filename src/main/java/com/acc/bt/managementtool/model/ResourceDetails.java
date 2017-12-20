package com.acc.bt.managementtool.model;

public class ResourceDetails {
	
	private WorkingResourceDetails wrDetails;
	private TeamLeadDetails tlDetails;
	private ReleaseManagerDetails rmDetails;
	
	public WorkingResourceDetails getWrDetails() {
		return wrDetails;
	}
	public void setWrDetails(WorkingResourceDetails wrDetails) {
		this.wrDetails = wrDetails;
	}
	public TeamLeadDetails getTlDetails() {
		return tlDetails;
	}
	public void setTlDetails(TeamLeadDetails tlDetails) {
		this.tlDetails = tlDetails;
	}
	public ReleaseManagerDetails getRmDetails() {
		return rmDetails;
	}
	public void setRmDetails(ReleaseManagerDetails rmDetails) {
		this.rmDetails = rmDetails;
	}
	
}
