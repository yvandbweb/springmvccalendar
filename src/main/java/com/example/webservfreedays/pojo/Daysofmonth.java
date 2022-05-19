package com.example.webservfreedays.pojo;

public class Daysofmonth {
	private String dayname;
	private String freedayname="no";
	private String weekend="no";
	private String daynumber;
	private String easter="no";
	private String month="none";
	private String monthname;
	private String type;
	
	public Daysofmonth(String dayname, String freedayname, String weekend, String daynumber, String easter,
			String month, String monthname, String type) {
		super();
		this.dayname = dayname;
		this.freedayname = freedayname;
		this.weekend = weekend;
		this.daynumber = daynumber;
		this.easter = easter;
		this.month = month;
		this.monthname = monthname;
		this.type = type;
	}

	public Daysofmonth() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMonthname() {
		return monthname;
	}

	public void setMonthname(String monthname) {
		this.monthname = monthname;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getEaster() {
		return easter;
	}

	public void setEaster(String easter) {
		this.easter = easter;
	}

	public String getDayname() {
		return dayname;
	}
	public void setDayname(String dayname) {
		this.dayname = dayname;
	}
	public String getFreedayname() {
		return freedayname;
	}
	public void setFreedayname(String freedayname) {
		this.freedayname = freedayname;
	}
	public String getWeekend() {
		return weekend;
	}
	public void setWeekend(String weekend) {
		this.weekend = weekend;
	}
	public String getDaynumber() {
		return daynumber;
	}
	public void setDaynumber(String daynumber) {
		this.daynumber = daynumber;
	}

	@Override
	public String toString() {
		return "Daysofmonth [dayname=" + dayname + ", freedayname=" + freedayname + ", weekend=" + weekend
				+ ", daynumber=" + daynumber + ", easter=" + easter + ", month=" + month + ", monthname=" + monthname
				+ ", type=" + type + "]";
	}
	
	

}
