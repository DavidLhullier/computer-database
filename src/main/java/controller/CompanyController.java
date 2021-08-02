package controller;

import java.util.List;

import model.Company;
import service.CompanyService;

public class CompanyController {

	private static CompanyController instance;
	private CompanyService companyService;
	
	//Singleton
	public static  CompanyController getInstance() {
		if(instance == null) {
			instance = new CompanyController();
		}
		return instance;
	}

	public CompanyController() {
		this.companyService = new CompanyService();
	}
	
	public List<Company> getAllCompany() {
		return companyService.getAllCompany();
	}

	public Company getCompanyById(int id) {
		return companyService.getCompanyById(id);
	}

	public void deleteCompanyById(int id) {
		companyService.deleteCompanyById(id);		
	}
	
	
}
