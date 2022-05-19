package com.example.webservfreedays.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.webservfreedays.entity.Countries;
import com.example.webservfreedays.pojo.DaysInterface;
import com.example.webservfreedays.pojo.MonthsInterface;

@Repository
public interface CountriesRepository extends CrudRepository<Countries, Integer> {	
	
	@Query(value = "SELECT q.* FROM ("
		         + "SELECT fd.freename as dayname,MONTH(fd.freedate) as daystringmonth,DAY(fd.freedate) as daystring FROM freedays fd WHERE "
			     + "fd.country_id = :countrycode AND fd.freedate BETWEEN :datefrom AND :dateto"
		         + " UNION "
		         + "SELECT 'EAST1' as dayname,MONTH(es.date) as daystringmonth,DAY(es.date) as daystring "
		         + "FROM easter es WHERE YEAR(es.date) = :year "
		         + " UNION "
		         + "SELECT 'EAST2' as dayname,MONTH(DATE_ADD(es2.date,INTERVAL 39 DAY)) as daystringmonth,DAY(DATE_ADD(es2.date,INTERVAL 39 DAY)) as daystring " 
		         + "FROM easter es2 WHERE YEAR(es2.date) = :year "	
		         + " UNION "
		         + "SELECT 'EAST3' as dayname,MONTH(DATE_ADD(es3.date,INTERVAL 49 DAY)) as daystringmonth,DAY(DATE_ADD(es3.date,INTERVAL 49 DAY)) as daystring " 
		         + "FROM easter es3 WHERE YEAR(es3.date) = :year "		         
		         + ") q ORDER BY q.daystringmonth, q.daystring",			     
		            nativeQuery = true)
	List<DaysInterface> getDataMonth(String countrycode, String year,String datefrom,String dateto);
		
	@Query(value = "SELECT q.* FROM ("
			     + "SELECT DISTINCT(MONTH(fd.freedate)) as month,"
			     + "(MONTHNAME(fd.freedate)) as monthname "
			     + "FROM freedays fd WHERE "
		         + "fd.country_id = :country_id"
		         + " UNION "
		         + "SELECT MONTH(es.date) as month," 
		         + "(MONTHNAME(es.date)) as monthname "
		         + "FROM easter es WHERE YEAR(es.date) = :year "
		         + " UNION "
		         + "SELECT MONTH(DATE_ADD(es2.date,INTERVAL 39 DAY)) as month," 
		         + "(MONTHNAME(DATE_ADD(es2.date,INTERVAL 39 DAY))) as monthname "
		         + "FROM easter es2 WHERE YEAR(es2.date) = :year "	
		         + " UNION "
		         + "SELECT MONTH(DATE_ADD(es3.date,INTERVAL 49 DAY)) as month," 
		         + "(MONTHNAME(DATE_ADD(es3.date,INTERVAL 49 DAY))) as monthname "
		         + "FROM easter es3 WHERE YEAR(es3.date) = :year "		         
		         + ") q ORDER BY q.month",
	               nativeQuery = true)
    List<MonthsInterface> getMonth(String country_id, String year);	
	
	
	@Query(value = "SELECT YEAR(es.date) FROM easter es WHERE "
		     + "es.date >= '1950-01-01'", 
	            nativeQuery = true)
	List<String> getYears();
	
	@Query(value = "SELECT * FROM countries co WHERE "
		     + "co.id=:country_id", 
	            nativeQuery = true)	
	Countries findCountry(String country_id);

}
