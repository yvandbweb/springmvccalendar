package com.example.webservfreedays.pojo;

public class Months implements MonthsInterface{
	private String month;
	private String monthname;	

	public Months() {
	}
		

	public void setMonth(String month) {
		this.month = month;
	}

	public void setMonthname(String monthname) {
		this.monthname = monthname;
	}

	@Override
	public String getMonth() {
		// TODO Auto-generated method stub
		return this.month;
	}

	@Override
	public String getMonthname() {
		// TODO Auto-generated method stub
		return this.monthname;
	}

}
