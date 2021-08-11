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
	
	@Autowired
    private CompanyService companyService ;
	@Autowired
	private ComputerDTOMapper computerDTOMapper;
	@Autowired
	private ComputerService computerService;
	
	private ComputerAddDTO computerDTO;
	
	private static final String VUE_DASHBOARD = "/dashboard";
	private static final String VUE_ADD_COMPUTER = "/addComputer";

	private Page page = new Page();
	private String orderBy = "cp.id";
	private String dir = "ASC";
	
	@GetMapping(value = "/AddComputerServlet")
	protected ModelAndView displayComputer() {
		System.out.println("displayAdd");
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
		System.out.println("adding");
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
	/*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerAddDTO computerDTO = new ComputerAddDTO();
		ComputerDTOBuilder computerAdd = new ComputerDTOBuilder();
		
		String computerName = request.getParameter("name");
		computerAdd.setName(computerName);
		
		String computerIntroduced = request.getParameter("introduced");
		computerAdd.setIntroduced(computerIntroduced);
		
		String computerDiscontinued = request.getParameter("discontinued");
		computerAdd.setDiscontinued(computerDiscontinued);
		
		String computerCompany = request.getParameter("companyId");
		computerAdd.setCompanyId(computerCompany);

		computerDTO = computerAdd.build();
		
		try {
			Optional<Computer> computer = this.computerDTOMapper.mapToComputer(computerDTO);
			CDBLogger.logInfo(AddComputerServlet.class.toString(), computer.toString());
			this.computerService.addComputer(computer);
			
		} catch(Exception e) {
			
		}
	

		response.sendRedirect(VUE_DASHBOARD); //ESSENTIEL
	}*/
	
	@GetMapping("/addComputer/cancel")
	public String cancel() {
		//insert error to log
		return VUE_DASHBOARD;
	}


}
