package ui;

import java.util.List;

import controller.CompanyController;
import controller.ComputerController;
import model.Company;

public class CLImain {

	public static void main(String[] args) {
		//CDBLogger.logInfo(CLImain.class.toString(), new Exception()) ;
		
		
		// TRANSACTION DELETE COMPANY AND COMPUTER EN CLI
		/*
		//AFFICHER TOUTES LES COMPANY ANVANT TRANSACTION
		CompanyController companyController = new CompanyController().getInstance();
		List<Company> listCompany =  companyController.getAllCompany();
		listCompany.stream().forEach(c -> System.out.println(c));
		
		int id = 9;
		companyController.deleteCompanyById(id);
		//AFFICHER TOUTES LES COMPANY APRES TRANSACTION
		List<Company> listCompany1 =  companyController.getAllCompany();
		listCompany1.stream().forEach(c -> System.out.println(c));
		*/
		
		
		
		//AFFICHER TOUS LES ORDIS EN CLI
		
		new ComputerController();
		//ComputerController computerController = ComputerController.getInstance();
		//List<Computer> listComputer = computerController.getAllComputer();
		//listComputer.stream().forEach(c -> System.out.println(c));
		
		
		/*
		
		int id = 1;
		Company company = companyController.getCompanyById(id);
		//System.out.println(company);
		
		
		String name = "blorpf";
		LocalDate introduced = LocalDate.parse("1971-08-23");
		LocalDate discontinued = LocalDate.parse("1979-12-21");
		int company_id = 4;
		
		//ADD COMPUTER EN CLI
		Computer computerAdd = new ComputerBuilder().setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(new Company(company_id, "rmn"))
				.build();
		//computerController.addComputer(computerAdd);
		//computerAdd = computerController.getComputerById(590);
		//System.out.println(computerAdd);
		
		
		id = 590;
		name = "plouf";
		introduced = LocalDate.parse("1991-07-10");
		discontinued = LocalDate.parse("1999-08-23");
		company_id = 6;
		
		//EDIT COMPUTER EN CLI
		Computer computerEdit = new ComputerBuilder().setName(name)
				.setIntroduced(introduced)
				.setDiscontinued(discontinued)
				.setCompany(new Company(company_id, "rmn"))
				.build();
		//computerController.editComputerById(id, computerEdit);
		//computerEdit = computerController.getComputerById(590);
		//System.out.println(computerEdit);
		
		
		id = 594;
		//DELETE COMPUTER EN CLI
		//computerController.deleteComputerById(id);
		//List<Computer> listComputer = computerController.getAllComputer();
		//listComputer.stream().forEach(c -> System.out.println(c));

		
		*/
		
	}

}
