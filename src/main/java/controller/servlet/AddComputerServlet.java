package controller.servlet;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Company;
import model.Computer;
import model.Computer.ComputerBuilder;
import service.CompanyService;
import service.ComputerService;
import logger.CDBLogger;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CompanyService companyService;
	private ComputerService computerService;

	private static final String VUE_DASHBOARD = "/computer-database/DashboardServlet";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputerServlet() {
		super();
		this.companyService = CompanyService.getInstance();
		this.computerService = ComputerService.getInstance();
	    
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
		
		
		ComputerBuilder computerAdd = new ComputerBuilder();
		if(!request.getParameter("name").isEmpty()) {
			String computerName = request.getParameter("name");
			computerAdd.setName(computerName);
		}
		if(!request.getParameter("introduced").isEmpty()) {
			LocalDate computerIntroduced = Date.valueOf(request.getParameter("introduced")).toLocalDate();
			computerAdd.setIntroduced(computerIntroduced);
		}
		if(!request.getParameter("discontinued").isEmpty()) {
			if(!request.getParameter("introduced").isEmpty() & 
					compareTo(Date.valueOf(request.getParameter("introduced")).toLocalDate(),
							Date.valueOf(request.getParameter("discontinued")).toLocalDate())) {
				LocalDate computerDiscontinued = Date.valueOf(request.getParameter("discontinued")).toLocalDate();
				computerAdd.setDiscontinued(computerDiscontinued);
			}
		}

		if(!request.getParameter("companyId").isEmpty() & !request.getParameter("companyId").equals("0")) {
			int computerCompany = Integer.valueOf(request.getParameter("companyId"));
			computerAdd.setCompany(new Company(computerCompany, "rominou"));
		}


		Computer computer = computerAdd.build();
		
		//DTO 
		//appel de DTO to COmputer avec un mapper (String)
		//avec 1ere ligne mapper Classe ValidationDTO en pattern singleton pour apell validateDTO(DTO)
		
		// dans validationDTO AVEC PLEIN DE VALIDATION ATTRIBUTS ssinon throw excpetions
		//logger throws ces excpetions mais sortent dans le servlet
		// mapper rend un model computer que je TRANSMET !!
		
		this.computerService.addComputer(computer);
		
		response.sendRedirect(VUE_DASHBOARD); //ESSENTIEL
	}

	public boolean compareTo(LocalDate date, LocalDate otherDate) {
		if(date.isBefore(otherDate)) {
			return true;
		}
		else {return false;}
	}


}
