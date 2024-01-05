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
import com.pisight.pimoney.repository.entities.Insurance;
import com.pisight.pimoney.repository.entities.Rider;

@Entity
@Table(name = "insurance_rider_map")
public class InsuranceRiderMap implements Serializable {

	private static final long serialVersionUID = 6279158456341240602L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type = "uuid-char")
	private UUID id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "insurance_id", nullable = false)
	private Insurance insurance;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "rider_id", nullable = false)
	private Rider rider;
	
	@JsonIgnore
	@OneToMany(mappedBy = "insuranceRiderMap", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RiderPremium> riderPremium = new ArrayList<>();

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
	 * @return the insurance
	 */
	public Insurance getInsurance() {
		return insurance;
	}

	/**
	 * @param insurance the insurance to set
	 */
	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	/**
	 * @return the rider
	 */
	public Rider getRider() {
		return rider;
	}

	/**
	 * @param rider the rider to set
	 */
	public void setRider(Rider rider) {
		this.rider = rider;
	}
	
}
