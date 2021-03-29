package models;

public class Salary {
	private Integer id;


	private long salary;

	private Integer days;

	private String year_month;

	private long total_time;

	private long wage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getYear_month() {
		return year_month;
	}

	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}

	public long getTotal_time() {
		return total_time;
	}

	public void setTotal_time(long total_time) {
		this.total_time = total_time;
	}

	public long getWage() {
		return wage;
	}

	public void setWage(long wage) {
		this.wage = wage;
	}



}
