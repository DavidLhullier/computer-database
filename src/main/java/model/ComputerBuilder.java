package model;

import java.time.LocalDate;

public class ComputerBuilder {
	
	private Company company;
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	
	public Computer build() {
		Computer computer = new Computer();
		computer.setId(id);
		computer.setName(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany(company);
		return computer;
	}
	

}