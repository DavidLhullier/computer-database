package model;

import java.time.LocalDate;

public class Computer  extends ComputerBuilder{

	
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

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}


	public LocalDate getIntroduced() {
		return introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}
	
	@Override
	public String toString() {
		return ", id=" + id + ", name=" + name + ", introduced=" + introduced
				+ ", discontinued=" + discontinued + ", company_id=" + this.company + "]";
	}
	
	
}
