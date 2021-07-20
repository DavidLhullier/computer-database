package service;

import java.util.List;

import model.Company;
import persistence.dao.CompanyDAO;

public class CompanyService {

	private static CompanyService instance;
	private CompanyDAO companyDAO;

	//Singleton
	/*public static  CompanyService getInstance() {
		if(instance == null) {
			instance = new CompanyService();
		}
		return instance;
	}*/

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
