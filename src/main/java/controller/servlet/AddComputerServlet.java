package controller.servlet;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import controller.binding.dto.ComputerAddDTO;
import controller.binding.mapper.ComputerDTOMapper;
import logger.CDBLogger;
import model.Company;
import model.Computer;
import model.Page;
import service.CompanyService;
import service.ComputerService;

@Controller
public class AddComputerServlet {
	

    private CompanyService companyService ;
	private ComputerDTOMapper computerDTOMapper;
	private ComputerService computerService;
	
	@Autowired
	public AddComputerServlet(CompanyService companyService, ComputerDTOMapper computerDTOMapper,
			ComputerService computerService) {
		this.companyService = companyService;
		this.computerDTOMapper = computerDTOMapper;
		this.computerService = computerService;
	}
	
	private ComputerAddDTO computerDTO;
	
	private static final String VUE_DASHBOARD = "redirect:/Dashboard?page=1";
	private static final String VUE_ADD_COMPUTER = "/addComputer";

	private Page page = new Page();
	private String orderBy = "cp.id";
	private String dir = "ASC";
	
	@GetMapping(value = "/AddComputerServlet")
	protected ModelAndView displayComputer() {
		ModelAndView mv = new ModelAndView(VUE_ADD_COMPUTER);
		mv.addObject("listCompany", this.getAllCompany());
		return mv;
	}
	
	private List<Company> getAllCompany() {
		return this.companyService.getAllCompany();
	}
	
	private List<Computer> getAllComputer() {
		int nbComputer = this.computerService.countAllComputer();
		this.page.setNbElementDB(nbComputer);
		return computerService.getComputerPage(this.page, this.orderBy, this.dir);
	}
	
	@PostMapping(value = "/AddComputerServlet")
	protected ModelAndView addComputer(ComputerAddDTO computerAddDTO) {
		CDBLogger.logInfo("adding");
		ModelAndView mv = new ModelAndView(VUE_DASHBOARD);
	
		try {
			CDBLogger.logInfo(AddComputerServlet.class.toString(), computerAddDTO.toString());
			Optional<Computer> computerAdd = this.computerDTOMapper.mapToComputer(computerAddDTO);
			
			CDBLogger.logInfo(AddComputerServlet.class.toString(), computerAdd.toString());
			this.computerService.addComputer(computerAdd);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		mv.addObject("computer", this.computerDTO );
		mv.addObject("listComputer", this.getAllComputer());

		this.page.setNbElementDB(this.getAllComputer().size());
		mv.addObject("page", this.page );

		this.getAllComputer().size();
		return mv;
	}
	
}
