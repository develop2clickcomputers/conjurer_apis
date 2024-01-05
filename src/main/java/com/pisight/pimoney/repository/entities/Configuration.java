package com.pisight.pimoney.repository.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.*;

@Data
@Entity
@Table(name="Configuration")
public class Configuration {	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = null;
	
	
	@Column(name="type", nullable=false)
	private String type = null;
	
	@Column(name="name", nullable=false)
	private String key = null;
	
	@Column(name="value", nullable=false)
	private String value = null;
	
	public Configuration() {
		
	}
	
	public Configuration(Long id, String type, String key, String value ) {
		this.id = id;
		this.type = type;
		this.key = key;
		this.value = value;
	}
	
}

