package controller.servlet;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controller.binding.dto.ComputerAddDTO;
import controller.binding.mapper.ComputerDTOMapper;
import logger.CDBLogger;
import model.Company;
import model.Computer;
import service.CompanyService;
import service.ComputerService;


@Controller
public class EditComputerServlet {

	private CompanyService companyService ;
	private ComputerDTOMapper computerDTOMapper;
	private ComputerService computerService;
	
	@Autowired
	public EditComputerServlet(CompanyService companyService, ComputerDTOMapper computerDTOMapper,
			ComputerService computerService) {
		this.companyService = companyService;
		this.computerDTOMapper = computerDTOMapper;
		this.computerService = computerService;
	}

	private static final String VUE_DASHBOARD = "/dashboard";
	private static final String VUE_EDIT_COMPUTER = "/editComputer";


	//@RequestMapping(value = "/EditComputerServlet", method = RequestMethod.GET)
	@GetMapping(value = "/EditComputerServlet")
	protected ModelAndView displayComputer(int id) {

		ModelAndView mv = new ModelAndView(VUE_EDIT_COMPUTER);
		mv.addObject("listCompany", this.getAllCompany());
		mv.addObject("computer", this.computerService.getComputer(id) );
		CDBLogger.logInfo("Update : "+this.computerService.getComputer(id));

		return mv;
	}

	private List<Company> getAllCompany() {
		return this.companyService.getAllCompany();
	}

	//@RequestMapping(value = "/EditComputerServlet", method = RequestMethod.POST )
	@PostMapping(value = "/EditComputerServlet" )
	protected String editComputer(@RequestParam("id") int id, ComputerAddDTO computerAddDTO) {
		CDBLogger.logInfo("udpating");
		try {
			CDBLogger.logInfo(EditComputerServlet.class.toString(), computerAddDTO.toString());
			Optional<Computer> computer = this.computerDTOMapper.mapToComputer(computerAddDTO);
			CDBLogger.logInfo(EditComputerServlet.class.toString(),  "Before modifications " + computer.toString());
			this.computerService.editComputerById(id, computer);

		} catch(Exception e) {
			e.printStackTrace();
		}

		return  "redirect:/Dashboard?page=1";
	}}
