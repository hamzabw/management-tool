package com.acc.bt.managementtool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="team")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(length = 45)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "charge_type_id")
	private ChargeabilityType chargeabilityType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ChargeabilityType getChargeabilityType() {
		return chargeabilityType;
	}

	public void setChargeabilityType(ChargeabilityType chargeabilityType) {
		this.chargeabilityType = chargeabilityType;
	}
	
	
}
