package controller.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.binding.dto.ComputerAddDTO;
import controller.binding.dto.ComputerAddDTO.ComputerDTOBuilder;
import controller.binding.mapper.ComputerDTOMapper;
import logger.CDBLogger;
import model.Company;
import model.Computer;
import service.CompanyService;
import service.ComputerService;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CompanyService companyService;
	private ComputerService computerService;
	private ComputerDTOMapper computerBindingMapper;

	private static final String VUE_DASHBOARD = "/computer-database/DashboardServlet";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditComputerServlet() {
		this.companyService = CompanyService.getInstance();
		this.computerService = ComputerService.getInstance();
		this.computerBindingMapper = new ComputerDTOMapper();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Company> listCompany = companyService.getAllCompany();

		request.setAttribute("listCompany", listCompany);

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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
			Optional<Computer> computer = this.computerBindingMapper.mapToComputer(computerDTO);
			CDBLogger.logInfo(EditComputerServlet.class.toString(), "Before modifications " + computer.toString());
			this.computerService.editComputerById(id, computer);

		} catch(Exception e) {e.printStackTrace();
			
		}

		response.sendRedirect(VUE_DASHBOARD); //ESSENTIEL
	}

}
