package com.pisight.pimoney.repository.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="rider")
public class Rider implements Serializable{

	private static final long serialVersionUID = -1521804613779733930L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "carrier_id", nullable = false)
	private Carrier carrier;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "plan_id", nullable = false)
	private PolicyPlan policyPlan;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@OneToMany(mappedBy="rider",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<InsuranceRiderMap> insuranceRiderMap = new ArrayList<InsuranceRiderMap>();

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the carrier
	 */
	public Carrier getCarrier() {
		return carrier;
	}

	/**
	 * @param carrier the carrier to set
	 */
	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	/**
	 * @return the policyPlan
	 */
	public PolicyPlan getPolicyPlan() {
		return policyPlan;
	}

	/**
	 * @param policyPlan the policyPlan to set
	 */
	public void setPolicyPlan(PolicyPlan policyPlan) {
		this.policyPlan = policyPlan;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
