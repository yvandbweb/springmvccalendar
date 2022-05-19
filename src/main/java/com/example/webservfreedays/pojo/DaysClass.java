package com.example.webservfreedays.pojo;

public class DaysClass implements DaysInterface {
	private String dayname = "";
	private String daystringmonth = "";
	private String daystring = "";
	
	public DaysClass() {

	}
	
	public DaysClass(String dayname, String daystringmonth, String daystring) {
		super();
		this.dayname = dayname;
		this.daystringmonth = daystringmonth;
		this.daystring = daystring;
	}
	@Override
	public String toString() {
		return "DaysClass [dayname=" + dayname + ", daystringmonth=" + daystringmonth + ", daystring=" + daystring
				+ "]";
	}
	public void setDayname(String dayname) {
		this.dayname = dayname;
	}
	public void setDaystringmonth(String daystringmonth) {
		this.daystringmonth = daystringmonth;
	}
	public void setDaystring(String daystring) {
		this.daystring = daystring;
	}
	@Override
	public String getDayname() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getDaystringmonth() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getDaystring() {
		// TODO Auto-generated method stub
		return null;
	}




}	