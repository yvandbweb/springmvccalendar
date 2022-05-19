package com.example.webservfreedays.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="freedays")
public class Freedays {
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String freename;	
	private Date freedate;
	  
    @ManyToOne
    @JoinColumn(name="country_id", nullable=false)
    private Countries cntry;	  
	  
	  
	public Freedays() {
	}

	public Countries getCntry() {
		return cntry;
	}

	public void setCntry(Countries cntry) {
		this.cntry = cntry;
	}

	public Freedays(Long id, String freename, Date freedate) {
		super();
		this.id = id;
		this.freename = freename;
		this.freedate = freedate;
	}
		
	@Override
	public String toString() {
		return "Freedays [id=" + id + ", freename=" + freename + ", freedate=" + freedate + ", cntry=" + cntry + "]";
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFreename() {
		return freename;
	}
	public void setFreename(String freename) {
		this.freename = freename;
	}
	public Date getFreedate() {
		return freedate;
	}
	public void setFreedate(Date freedate) {
		this.freedate = freedate;
	}
	  
	  

}
