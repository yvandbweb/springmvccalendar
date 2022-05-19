package com.example.webservfreedays.service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.type.LocalDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.webservfreedays.entity.Countries;
import com.example.webservfreedays.entity.Easter;
import com.example.webservfreedays.entity.Freedays;
import com.example.webservfreedays.entity.Translate;
import com.example.webservfreedays.pojo.DaysInterface;
import com.example.webservfreedays.pojo.Daysofmonth;
import com.example.webservfreedays.pojo.Months;
import com.example.webservfreedays.pojo.MonthsInterface;
import com.example.webservfreedays.repository.CountriesRepository;
import com.example.webservfreedays.repository.TranslateRepository;
import com.example.webservfreedays.service.ImportService;


@Service
public class CountriesService {
	
	@Autowired
	CountriesRepository cntryRepo;
	
	@Autowired
	TranslateRepository trslRepo;	
	
	@Autowired
	ImportService impContr;
	
    @PersistenceContext // or even @Autowired
    private EntityManager em;	
	
	public List<Countries> getCountries(){
		return (List<Countries>) cntryRepo.findAll();
		
	}
	
	public List<DaysInterface> getDataMonth(String countrycode,String year,String month){
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("u.M.d");
	    LocalDate datefrom = LocalDate.parse( "0001."+month+".1"  , dateFormatter);
		LocalDate dateto = datefrom.plusMonths(12); 			
		return cntryRepo.getDataMonth(countrycode.toString(),year.toString(),datefrom.toString(),dateto.toString());
		
	}
	
	public List<Months> getMonths(String country_id, String year) {
		List<Translate> translate = (List<Translate>) trslRepo.findAll();
		Map<String,String> translateMap = new HashMap<>();
		List<Months> months = new ArrayList<>();
		
		Countries country = cntryRepo.findCountry(country_id);
		String country_code_lng = country.getCode();
		
		for (Translate tr : translate) {
			translateMap.put(tr.getCodeid(), tr.getName());			
		}
		
		for (MonthsInterface m : cntryRepo.getMonth(country_id,year)) {			
			Months month = new Months();
			month.setMonth(m.getMonth());
			month.setMonthname(translateMap.get(country_code_lng+"_M_"+m.getMonth()));
			months.add(month);
			System.out.println(country_code_lng+"_M_"+m.getMonth());
		}		
		
		return months;
	}
	
	
	
	public List<Daysofmonth> getFreedaysYear(String country_id,String year){
		List<Daysofmonth> daysofmonths = new ArrayList<>();
		List<Translate> translate = (List<Translate>) trslRepo.findAll();
		Map<String,String> translateMap = new HashMap<>();
		Countries country = cntryRepo.findCountry(country_id);
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("u.M.d");
	    LocalDate datefrom = LocalDate.parse( "0001.01.1"  , dateFormatter);
		LocalDate dateto = datefrom.plusMonths(12); 		
		List<DaysInterface> freedays=cntryRepo.getDataMonth(country_id.toString(),year.toString(),datefrom.toString(),dateto.toString());
		
		for (Translate tr : translate) {
			translateMap.put(tr.getCodeid(), tr.getName());			
		}	
		
		for (DaysInterface day :freedays) {
			Daysofmonth dayofmonth=new Daysofmonth(); 
			dayofmonth.setFreedayname(day.getDayname());
			dayofmonth.setMonth(day.getDaystringmonth());
			dayofmonth.setDaynumber(day.getDaystring());
		    LocalDate dateofweek = LocalDate.parse( year+"."+day.getDaystringmonth()+"."+day.getDaystring()  , dateFormatter);					
			DayOfWeek dayofweek = dateofweek.getDayOfWeek();			
			dayofmonth.setDayname(translateMap.get(country.getCode()+"_D_"+dayofweek.getValue()));
			dayofmonth.setMonthname(translateMap.get(country.getCode()+"_M_"+day.getDaystringmonth()));
			if (day.getDayname().contains("EAST")) {
				dayofmonth.setFreedayname(translateMap.get(country.getCode()+"_P_"+day.getDayname()));
				dayofmonth.setEaster("yes");			
			}
			daysofmonths.add(dayofmonth);
		}		
					
		return daysofmonths;			
	}
	
	
	
	public List<String> getYears(){
		return cntryRepo.getYears();
		
	}	
	
	public List<Daysofmonth> getDaysofmonth(String month, String year, String country_id) throws Exception {
		List<Daysofmonth> daysofmonths = new ArrayList<>();			
		List<Translate> translate = (List<Translate>) trslRepo.findAll();
		Map<String,String> translateMap = new HashMap<>();
		List<Months> months = new ArrayList<>();
		
		Countries country = cntryRepo.findCountry(country_id);
		String country_code_lng = country.getCode();
		
		for (Translate tr : translate) {
			translateMap.put(tr.getCodeid(), tr.getName());			
		}
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("u.M.d");
	    LocalDate datefrom = LocalDate.parse( "0001."+month+".1"  , dateFormatter);
		LocalDate dateto = datefrom.plusMonths(1);
		
		List<DaysInterface> freedays = cntryRepo.getDataMonth(country_id,year,datefrom.toString(),dateto.toString());
		
		YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(year), Integer.parseInt(month));
		
		
	    LocalDate dateofweek = LocalDate.parse( year+"."+month+".1"  , dateFormatter);					
		DayOfWeek dayofweek = dateofweek.getDayOfWeek();	
		Integer i;
		Integer b;
		Boolean fourtyhfour=false;
		for (b=1;b<dayofweek.getValue();b++) {
			Daysofmonth dayofmonth = new Daysofmonth();
			dayofmonth.setType("empty");
			daysofmonths.add(dayofmonth);
		}
		
		for (i=1;i<=yearMonthObject.lengthOfMonth();i++) {
			Daysofmonth dayofmonth = new Daysofmonth();			
		    dateofweek = LocalDate.parse( year+"."+month+"."+i.toString()  , dateFormatter);					
			dayofweek = dateofweek.getDayOfWeek();
		    
			if (dayofweek.getValue()==6 || dayofweek.getValue()==7)
				dayofmonth.setWeekend("yes");
			else
				dayofmonth.setWeekend("no");
			
			dayofmonth.setDayname(translateMap.get(country_code_lng+"_D_"+dayofweek.getValue()));
			dayofmonth.setFreedayname("none");					
			dayofmonth.setDaynumber(i.toString());
			dayofmonth.setEaster("no");
			
			for (DaysInterface day :freedays) {
				if (Integer.parseInt(day.getDaystring())==i && month.equals(day.getDaystringmonth())) {
					dayofmonth.setFreedayname(day.getDayname());
					dayofmonth.setType("freeday");
					if (day.getDayname().contains("EAST")) {
						dayofmonth.setFreedayname(translateMap.get(country_code_lng+"_P_"+day.getDayname()));
						dayofmonth.setEaster("yes");
						dayofmonth.setType("Easter");
						
					}
					
				}				
			}
			
			daysofmonths.add(dayofmonth);
						
		}
		
		
	    dateofweek = LocalDate.parse( year+"."+month+"."+(i-1)  , dateFormatter);					
		dayofweek = dateofweek.getDayOfWeek();
		Integer end;
		
		if ((b+(i-2)+(7-dayofweek.getValue()))<=35)
			end=35;
		else
			end=42;
		
		for (i=(i+b-1);i<=end;i++) {
			Daysofmonth dayofmonth = new Daysofmonth();
			dayofmonth.setType("empty");
			daysofmonths.add(dayofmonth);
		}
		
		
		return daysofmonths;
		
	}	
	


	@Transactional
	public List<String> importeaster() throws FileNotFoundException, IOException, ParseException {
		
		List<String> dates=impContr.importeaster();

	    	    	
		for (String date2  :  dates) {
		    Easter easter = new Easter();
		    
		    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String dateString = date2;
		    Date dat = format.parse(dateString);
		    
		    easter.setDate(dat);
		    em.persist(easter);				
		}
				
		
		
		return dates;
		
	}	
	
	
	@Transactional
	public List<String> importcountry() throws FileNotFoundException, IOException, ParseException {
		
		List<String> dates=impContr.importeaster();

	    	    	
		for (String date2  :  dates) {
		    Easter easter = new Easter();
		    
		    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String dateString = date2;
		    Date dat = format.parse(dateString);
		    
		    easter.setDate(dat);
		    em.persist(easter);				
		}
				
		
		
		return dates;
		
	}	
	
	
	@Transactional
	public Map<String,String> importcountries() throws IOException, ParseException{
		
		Map<String,String> countries=impContr.countrySimple("nl");
		
	    Countries country = new Countries();	    	    
	    country.setCode("NL");
	    country.setName("Nederland");
	    em.persist(country);			
		
        for (String key : countries.keySet()) { 
            System.out.println("key: " + key);	
            System.out.println("value: " + countries.get(key.toString()).toString());
            
		    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    String dateString = "0001-"+key;
		    Date dat = format.parse(dateString);            
            
            Freedays frd=new Freedays();  
            frd.setCntry(country);
            frd.setFreedate(dat);
            frd.setFreename(countries.get(key.toString()).toString());

		    em.persist(frd);		            
        }
	    	    	
				
		
		
		return countries;
		
	}	
	
	
	@Transactional
	public Map<String,String> generatetranslate() throws IOException, ParseException{
		
		Map<String,String> trans1=impContr.generatetranslate();
		
	    for (Map.Entry <String,String> entry : trans1.entrySet()) {
	    	
	        Translate translate=new Translate();
	        translate.setCodeid(entry.getKey());
	        translate.setName(entry.getValue());
	        
	        em.persist(translate);
	    }
		
	    	    	
				
		
		
		return trans1;
		
	}	
	

}
