package ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import configuration.RootConfiguration;
import controller.ComputerController;
import logger.CDBLogger;
import model.Company;
import model.Computer;
import model.Computer.ComputerBuilder;
import service.ComputerService;

public class CLImain {
	
	private static ApplicationContext context;

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
		
		
		/*
		//AFFICHER TOUS LES ORDIS EN CLI
		context = new AnnotationConfigApplicationContext(RootConfiguration.class);
		List<Computer> listComputer = context.getBean(ComputerController.class).getAllComputer();
		listComputer.stream().forEach(c -> System.out.println(c));
		//AFFICHER TOUS LES COMPANY EN CLI
		List<Company> listCompany = context.getBean(CompanyController.class).getAllCompany();
		listCompany.stream().forEach(c -> System.out.println(c));
		//AFFICHER UN ORDI
		Computer computer =  context.getBean(ComputerController.class).getComputerById(18);
		CDBLogger.logInfo(CLImain.class.toString(), computer.toString()) ;
		//AFFICHER UNE COMPANY
		Company company =  context.getBean(CompanyController.class).getCompanyById(18);
		CDBLogger.logInfo(CLImain.class.toString(), company.toString()) ;
		*/
		

		
		/*
		String name = "blorpf";
		LocalDate introduced = LocalDate.parse("1971-08-23");
		LocalDate discontinued = LocalDate.parse("1979-12-21");
		int company_id = 18;
		
		//ADD COMPUTER EN CLI
		Computer computerAdd = new ComputerBuilder().setName(name)
				.withIntroduced(introduced)
				.withDiscontinued(discontinued)
				.withCompany(new Company(company_id, "rmn"))
				.build();
		Optional<Computer> computer = Optional.ofNullable(computerAdd);
		System.out.println(computer.toString());
		context = new AnnotationConfigApplicationContext(RootConfiguration.class);
		context.getBean(ComputerController.class).addComputer(computer);
		List<Computer> listComputer = context.getBean(ComputerController.class).getAllComputer();
		listComputer.stream().forEach(c -> System.out.println(c));
		*/
		
		/*
		int id = 643;
		name = "plouf";
		introduced = LocalDate.parse("1991-07-10");
		discontinued = LocalDate.parse("1999-08-23");
		company_id = 6;
		
		//EDIT COMPUTER EN CLI
		Computer computerEdit = new ComputerBuilder().setName(name)
				.withIntroduced(introduced)
				.withDiscontinued(discontinued)
				.withCompany(new Company(company_id, "rmn"))
				.build();
		Optional<Computer> computer = Optional.ofNullable(computerEdit);
		context = new AnnotationConfigApplicationContext(RootConfiguration.class);
		
		Computer computerId =  context.getBean(ComputerController.class).getComputerById(id);
		CDBLogger.logInfo(CLImain.class.toString(), computerId.toString()) ;
		
		context.getBean(ComputerController.class).editComputerById(id, computer);
		
		computerId =  context.getBean(ComputerController.class).getComputerById(id);
		CDBLogger.logInfo(CLImain.class.toString(), computerId.toString()) ;
		*/
		
		
		/*int id = 643;
		//DELETE COMPUTER EN CLI
		context = new AnnotationConfigApplicationContext(RootConfiguration.class);
		context.getBean(ComputerController.class).deleteComputerById(id);
		List<Computer> listComputer = context.getBean(ComputerController.class).getAllComputer();
		listComputer.stream().forEach(c -> System.out.println(c));
		*/
		
		//COUNT ALL
		//context = new AnnotationConfigApplicationContext(RootConfiguration.class);
		//System.out.println(context.getBean(ComputerService.class).countAllComputer());
		
		
		
		
	}

}
