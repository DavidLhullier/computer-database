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
	
	@Autowired
    private CompanyService companyService ;
	@Autowired
	private ComputerDTOMapper computerDTOMapper;
	@Autowired
	private ComputerService computerService;
	
	private ComputerAddDTO computerDTO;
	
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
	}
	
	/*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		int id = Integer.parseInt(request.getParameter("id"));
		Computer computer = computerService.getComputer(id);
		CDBLogger.logInfo(EditComputerServlet.class.toString(), "Before modifications " + computer.toString());

		request.setAttribute("computerId", computer.getId());
		request.setAttribute("computerName", computer.getName());
		request.setAttribute("computerIntroduced", computer.getIntroduced());
		request.setAttribute("computerDiscontinued", computer.getDiscontinued());
		request.setAttribute("company", computer.getCompany());
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/view/editComputer.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}*/
	
	/*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
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
			CDBLogger.logInfo(EditComputerServlet.class.toString(), "Before modifications " + computer.toString());
			this.computerService.editComputerById(id, computer);

		} catch(Exception e) {e.printStackTrace();
			
		}

		response.sendRedirect(VUE_DASHBOARD); //ESSENTIEL
	}*/

	@GetMapping("/editComputer/cancel")
	public String cancel () {
		//insert error to log
		return VUE_DASHBOARD;
	}
}
