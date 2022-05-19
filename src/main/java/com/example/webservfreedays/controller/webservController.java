package com.example.webservfreedays.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webservfreedays.entity.Countries;
import com.example.webservfreedays.pojo.Daysofmonth;
import com.example.webservfreedays.pojo.Months;
import com.example.webservfreedays.service.CountriesService;

@RestController
public class webservController {
	@Autowired
	CountriesService cntserv;
	
	@CrossOrigin(origins = "https://ydbweb.com")
	@GetMapping("/countries")
	public List<Countries> getCountries() {
		return cntserv.getCountries();
	}
	
	@CrossOrigin(origins = "https://ydbweb.com")
	@GetMapping("/months")
	public List<Months> getMonths(@RequestParam String country_id,@RequestParam String year) {
		return cntserv.getMonths(country_id,year);
	}	
	
	@CrossOrigin(origins = "https://ydbweb.com")
	@GetMapping("/years")
	public List<String> getYears() {
		return cntserv.getYears();
	}	
	
	@CrossOrigin(origins = "https://ydbweb.com")
	@GetMapping("/daysofmonth")
	public List<Daysofmonth> getDaysofmonth(@RequestParam String month, @RequestParam String year,@RequestParam String country_id) throws Exception {
		return cntserv.getDaysofmonth(month,year,country_id);
	}	
	
	@CrossOrigin(origins = "https://ydbweb.com")
	@GetMapping("/freedaysyear")
	public List<Daysofmonth> getFreedaysYear(@RequestParam String year,@RequestParam String country_id) throws Exception {
		return cntserv.getFreedaysYear(country_id,year);
	}
	
	/*
	@GetMapping("/generatetranslate")
	public Map<String,String> generatetranslate() throws IOException, ParseException {
		return cntserv.generatetranslate();
	}
	*/		
	
	/*
	@GetMapping("/countriesdata")
	public List<Days> getDataMonth(@RequestParam String country_id,@RequestParam String year,@RequestParam String month) {
		return cntserv.getDataMonth(country_id,year,month);
	}	

	
	@GetMapping("/importeaster")
	public List<String> importEaster() throws FileNotFoundException, IOException, ParseException {
		return cntserv.importeaster();
	}	
	
	@GetMapping("/importcountries")
	public Map<String,String> importcountries() throws FileNotFoundException, IOException, ParseException {
		return cntserv.importcountries();
	}	
	*/	
		

}
