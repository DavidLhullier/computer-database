package controller.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logger.CDBLogger;
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
	private static final String VUE_DASHBOARD = "/computer-database/DashboardServlet";
	private final String ASCENDING = "ASC";
	private final String DESCENDING = "DESC";
	private final String ORDER_BY_COMPUTER_NAME ="cp.name";
	private final String ORDER_BY_COMPANY_NAME ="cny.name";
	private final String ORDER_BY_COMPUTER_ID ="cp.id";
	private final String ORDER_BY_COMPUTER_INTRODUCED ="cp.introduced";
	private final String ORDER_BY_COMPUTER_DISCONTINUED ="cp.discontinued";
	private final String RESEARCH_EMPTY ="";
	private boolean isSearching = false;
	private String lemotquejechere ="";
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
		this.setInitialisation(request);
		this.updatePage(request);

		
		
		String search = request.getParameter("search");
		
		
		if(search!=null ) {
			isSearching = true;
			lemotquejechere=search;
			this.page.setNumeroPage(1);
		}
		
		if(!lemotquejechere.isEmpty()) {
			search = lemotquejechere +","+ ORDER_BY_COMPUTER_NAME +","+ ASCENDING;
			CDBLogger.logInfo(search);
			List<String> research = Arrays.asList(search.split(","));
			this.updateSearch(request, research);

			request.setAttribute("search",search);
		} else {
			int nbComputer = computerService.countAllComputer();
			page.setNbElementDB(nbComputer);
			List<Computer> listComputer = computerService.getComputerPage(page);
			request.setAttribute("listComputer", listComputer);
			request.setAttribute("nbComputer", nbComputer);
		}
			
		//String paramOrderBy = request.getParameter("orderBy");
		//this.updateOrderBy( paramOrderBy);
		
		

		
		
		


		this.getServletContext().getRequestDispatcher("/WEB-INF/view/dashboard.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<String> selection = Arrays.asList(request.getParameter("selection").split(","));
				try {
			selection.stream()
				.map(s -> Integer.valueOf(s) )
				.forEach(id -> this.computerService.deleteComputerById(id) );
		}catch (Exception e) {
			e.printStackTrace();
			CDBLogger.logWarn(DashboardServlet.class.toString(), e);
		}
		
		
						
				
		
		
		
		
		
		response.sendRedirect(VUE_DASHBOARD);

	}

	
	private void setInitialisation(HttpServletRequest request) {
		request.setAttribute("page",page);

		request.setAttribute("search", RESEARCH_EMPTY);
		request.setAttribute("orderBy", ORDER_BY_COMPUTER_ID );
		
	}
	
	private void updatePage(HttpServletRequest request) {
		String nbElement = request.getParameter("nbElementByPage");
		String numPage = request.getParameter("page");
		try {
			
			if(numPage != null) {
				this.page.setNumeroPage(Integer.valueOf(numPage));
				if  (Integer.valueOf(numPage) <= page.getTotalPage() ) {
					page.setNumeroPage(Integer.valueOf(numPage));
				}
			}
			
			if(nbElement != null) {
				
				this.page.setNbElementByPage(Integer.valueOf(nbElement));
				this.page.setNumeroPage(1);
			}
			
			
			int nbComputer = computerService.countAllComputer();
			this.page.setNbElementDB(nbComputer);
			
			
		} catch (NullPointerException | NumberFormatException e) {
			e.printStackTrace();
			//CDBLogger.logInfo(DashboardServlet.class.toString(), e);
		}
		CDBLogger.logInfo(this.page.toString());
	}
	
	private void updateSearch(HttpServletRequest request, List<String> searchRequest) {
		if(searchRequest.get(0) != null) {
			String orderBy = "";
			switch(searchRequest.get(1)) {
				case "computerName":
					orderBy = ORDER_BY_COMPUTER_NAME;
					break;
				case "introduced":
					orderBy = ORDER_BY_COMPUTER_INTRODUCED;
					break;
				case "discontinued":
					orderBy = ORDER_BY_COMPUTER_DISCONTINUED;
					break;
				case "companyName":
					orderBy = ORDER_BY_COMPANY_NAME;
					break;
				default :
					orderBy = ORDER_BY_COMPUTER_ID;
			}
			String dir ="";
			if(searchRequest.get(2) == ASCENDING) {
				dir = ASCENDING;
			} else {
				dir = DESCENDING;
			}
			
			
			try {
				
				List<Computer> listComputer = this.computerService.getComputerResearch(searchRequest.get(0), ORDER_BY_COMPUTER_NAME, ASCENDING, page);
				int nbComputer = computerService.countAllComputerWithSearch(searchRequest.get(0));
				this.page.setNbElementDB(nbComputer);
				
				
				request.setAttribute("listComputer", listComputer);
				request.setAttribute("nbComputer", this.page.getNbElementDB());
				
			} catch (Exception e) {
				CDBLogger.logWarn(DashboardServlet.class.toString(), e);
			}
			
			
			
		}
		
		
	}
	
}
