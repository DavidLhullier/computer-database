package ui;

import java.time.LocalDate;
import java.util.List;

import controller.CompanyController;
import controller.ComputerController;
import model.Company;
import model.Computer;

public class CLImain {

	public static void main(String[] args) {
		CompanyController companyController = new CompanyController();
		//List<Company> listCompany =  companyController.getAllCompany();
		//listCompany.stream().forEach(c -> System.out.println(c)); 
		
		
		ComputerController computerController = new ComputerController();
		//List<Computer> listComputer = computerController.getAllComputer();
		//listComputer.stream().forEach(c -> System.out.println(c));
		
		int id = 1;
		Company company = companyController.getCompanyById(id);
		//System.out.println(company);
		
		
		String name = "blorpf";
		LocalDate introduced = LocalDate.parse("1971-08-23");
		LocalDate discontinued = LocalDate.parse("1979-12-21");
		int company_id = 4;
		
		/*
		Computer computerAdd = new Computer(0, name, introduced, discontinued, new Company(company_id, "rmn"));
		computerController.addComputerById(computerAdd);
		computerAdd = computerController.getComputerById(589);
		System.out.println(computerAdd);
		
		
		id = 589;
		name = "plouf";
		introduced = LocalDate.parse("1991-07-10");
		discontinued = LocalDate.parse("1999-08-23");
		company_id = 6;
		
		Computer computerEdit = new Computer(id, name, introduced, discontinued, new Company(company_id, "rmn"));
		computerController.editComputerById(id, computerEdit);
		computerEdit = computerController.getComputerById(589);
		System.out.println(computerEdit);
		
		
		id = 589;
		computerController.deleteComputerById(id);
		List<Computer> listComputer = computerController.getAllComputer();
		listComputer.stream().forEach(c -> System.out.println(c));*/
	}

}
