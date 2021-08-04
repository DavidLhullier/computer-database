package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import model.Company;
import service.CompanyService;

@Controller
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
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
