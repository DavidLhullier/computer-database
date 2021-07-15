package service;

import java.util.List;

import model.Company;
import model.Computer;
import persistence.dao.CompanyDAO;

public class CompanyService {
	
	private CompanyDAO companyDAO;
	
	public CompanyService() {
		this.companyDAO = new CompanyDAO();
	}

	public List<Company> getAllCompany() {
		return companyDAO.getAllCompany();
	}

	public Company getCompanyById(int id) {
		return companyDAO.getCompanyById(id);
	}

}
