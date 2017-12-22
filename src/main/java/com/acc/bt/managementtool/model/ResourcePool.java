package com.acc.bt.managementtool.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="resource_pool")
public class ResourcePool {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JsonIgnore
	private Resource resource;
	
	@Column(name = "date", columnDefinition = "DATETIME")
	private Date date;
	
	@Column(name = "status", nullable = false)
	private char status;

	@ManyToOne
	@JoinColumn(name = "requested_by", nullable = true)
	private Resource requestedBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Resource getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(Resource requestedBy) {
		this.requestedBy = requestedBy;
	}
}
