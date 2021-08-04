package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Company;
import persistence.dao.CompanyDAO;

@Service
public class CompanyService {

	@Autowired
	private CompanyDAO companyDAO;


	public List<Company> getAllCompany() {
		return companyDAO.getAllCompany();
	}

	public Company getCompanyById(int id) {
		return companyDAO.getCompanyById(id);
	}

	public void deleteCompanyById(int id) {
		companyDAO.deleteCompanyById(id);
	}

}
