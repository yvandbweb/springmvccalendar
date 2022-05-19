package com.example.webservfreedays.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Easter {
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private Long id;
	  @Column(name = "date")
	  @Temporal(TemporalType.DATE)	  
	  private Date date;
	  
	@Override
	public String toString() {
		return "Easter [id=" + id + ", date=" + date + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Easter(Long id, Date date) {
		super();
		this.id = id;
		this.date = date;
	}	
	
	public Easter() {

	}		
	  
	  
	

}
