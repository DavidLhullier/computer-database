package controller;

import java.util.List;

import model.Company;
import model.Computer;
import service.CompanyService;

public class CompanyController {

	private CompanyService companyService;

	public CompanyController() {
		this.companyService = new CompanyService();
	}
	
	public List<Company> getAllCompany() {
		return companyService.getAllCompany();
	}

	public Company getCompanyById(int id) {
		return companyService.getCompanyById(id);
	}
	
	
}
