package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.model.Company;
import persistence.dao.CompanyDAO;

@Service
public class CompanyService {
	
	private ComputerService computerService;
	private CompanyDAO companyDAO;

	@Autowired
	public CompanyService(CompanyDAO companyDAO, ComputerService computerService) {
		this.companyDAO = companyDAO;
		this.computerService = computerService;
	}


	public List<Company> getAllCompany() {
		return companyDAO.getAllCompany();
	}

	public Company getCompanyById(int id) {
		return companyDAO.getCompanyById(id);
	}

	@Transactional
	public void deleteCompanyById(int id) {
		this.computerService.deleteComputerByCompanyId(id);
		this.companyDAO.deleteCompanyById(id);
		
		
	}

}
