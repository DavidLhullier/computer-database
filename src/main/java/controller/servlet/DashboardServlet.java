package controller.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Computer;
import model.Page;
import service.ComputerService;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService;
	private Page page = new Page();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
        this.computerService = ComputerService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setNumeroPage(request.getParameter("page"));
		String nbElement = request.getParameter("nbElementByPage");
		if(nbElement != null) {
			page.setNbElementByPage(Integer.valueOf(nbElement));
		}
		
		
		int nbComputer = computerService.countAllComputer();
		page.setNbElementDB(nbComputer);

		
		List<Computer> listComputer = computerService.getComputerPage(page);
		
			
		request.setAttribute("listComputer", listComputer);
		request.setAttribute("nbComputer", nbComputer);
		request.setAttribute("page", page);
		
		

		this.getServletContext().getRequestDispatcher("/WEB-INF/view/dashboard.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("button50"));
	}

	private void setNumeroPage(String request) {
		if(request != null) {
			this.page.setNumeroPage(Integer.valueOf(request));
		}
	}
	
	
}
