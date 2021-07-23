package controller.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.binding.dto.ComputerAddDTO;
import controller.binding.dto.ComputerAddDTO.ComputerDTOBuilder;
import controller.binding.mapper.ComputerBindingMapper;
import logger.CDBLogger;
import model.Company;
import model.Computer;
import service.CompanyService;
import service.ComputerService;
import ui.CLImain;
/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CompanyService companyService;
	private ComputerService computerService;
	private ComputerBindingMapper computerBindingMapper;

	private static final String VUE_DASHBOARD = "/computer-database/DashboardServlet";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputerServlet() {
		this.companyService = CompanyService.getInstance();
		this.computerService = ComputerService.getInstance();
		this.computerBindingMapper = new ComputerBindingMapper();
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		List<Company> listCompany = companyService.getAllCompany();

		request.setAttribute("listCompany", listCompany);


		this.getServletContext().getRequestDispatcher("/WEB-INF/view/addComputer.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
			
			Computer computer = this.computerBindingMapper.mapToComputer(computerDTO);
			System.out.println(computer);
			this.computerService.addComputer(computer);
			
		} catch(Exception e) {
			CDBLogger.logInfo(AddComputerServlet.class.toString(), new Exception("computer not add")) ;
		}
	

		response.sendRedirect(VUE_DASHBOARD); //ESSENTIEL
	}

	public boolean compareTo(LocalDate date, LocalDate otherDate) {
		if(date.isBefore(otherDate)) {
			return true;
		}
		else {return false;}
	}


}
