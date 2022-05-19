package com.example.webservfreedays.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Translate {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String codeid;
	private String name;
	
	public Translate() {
	}	
	
	public Translate(Long id, String codeid, String name) {
		super();
		this.id = id;
		this.codeid = codeid;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeid() {
		return codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Translate [id=" + id + ", codeid=" + codeid + ", name=" + name + "]";
	}
	
	
	
	
}