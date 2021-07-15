package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Computer {

	private Company company;

	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	
	public Computer(int id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public Computer() {
		
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	
	
	@Override
	public String toString() {
		return ", id=" + id + ", name=" + name + ", introduced=" + introduced
				+ ", discontinued=" + discontinued + ", company_id=" + this.company + "]";
	}
	
}
