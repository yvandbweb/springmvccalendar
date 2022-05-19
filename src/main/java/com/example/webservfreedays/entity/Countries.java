package com.example.webservfreedays.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Countries {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String code;
	  
    @OneToMany(mappedBy="id")
	private Set<Freedays> freedays;		  
	  
    public Countries(Long id, String name, String code, Set<Freedays> freedays) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.freedays = freedays;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}  
	  

	@Override
	public String toString() {
		return "Countries [id=" + id + ", name=" + name + ", code=" + code + ", freedays=" + freedays + "]";
	}

	public Set<Freedays> getFreedays() {
		return freedays;
	}

	public void setFreedays(Set<Freedays> freedays) {
		this.freedays = freedays;
	}

	public Countries() {

	  }	  
	  
	  public Countries(String name) {
		  this.name=name;
	  }

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	  
	  

}
