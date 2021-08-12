package controller.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import logger.CDBLogger;
import model.Computer;
import model.Page;
import service.ComputerService;


@Controller
public class DashboardServlet {

	private ComputerService computerService;
	
	@Autowired
	public DashboardServlet(ComputerService computerService) {
		this.computerService = computerService;
	}

	private Page page = new Page();
	private static final String VUE_DASHBOARD = "/dashboard";
	private static final String RESEARCH_EMPTY = "";
	private static final String COMPUTER_NAME = "cp.name";
	private static final String ASCENDANT = "ASC";
	private String searchRequest = "";
	private String orderBy = "cp.id";
	private String dir = "ASC";


	private List<Computer> getAllComputer() {
		int nbComputer = this.computerService.countAllComputer();
		this.page.setNbElementDB(nbComputer);
		return computerService.getComputerPage(this.page, this.orderBy, this.dir);
	}

	@GetMapping(value = "/Dashboard", params = "page")
	public ModelAndView updateNumPage(@RequestParam("page") int numPage) throws ServletException, IOException {
		CDBLogger.logInfo("updateNumPage");
		this.page.setNumeroPage(numPage);
		return this.updateSearch(this.searchRequest);
	}

	@GetMapping(value = "/Dashboard", params = "nbElementByPage")
	public ModelAndView updateNbElementByPage(@RequestParam("nbElementByPage") int nbElementByPage) throws ServletException, IOException {
		CDBLogger.logInfo("updateNbElementByPage");
		this.page.setNbElementByPage(nbElementByPage);
		this.page.setNumeroPage(1);
		return this.updateSearch(this.searchRequest);	}

	@GetMapping(value = "/Dashboard", params = "search")
	public ModelAndView updateSearch(@RequestParam("search") String search) throws ServletException, IOException {
		ModelAndView mv = new ModelAndView(VUE_DASHBOARD);

		try {
			this.searchRequest = search;
			if(this.searchRequest.equals(RESEARCH_EMPTY)) {
				CDBLogger.logInfo("no research");
				mv.addObject("listComputer", this.getAllComputer());

				this.page.setNbElementDB(this.getAllComputer().size());
				mv.addObject("page", this.page );

				this.getAllComputer().size();

			}else {
				CDBLogger.logInfo("updateSearch "+search);

				this.page.setNbElementDB(this.computerService.
						countAllComputerWithSearch(this.searchRequest));
				
				if(this.page.getNumeroPage() > this.page.getTotalPage()) {
					this.page.setNumeroPage(1);
					
				} 
				mv.addObject("listComputer", 
						this.computerService.
						getComputerResearch(this.searchRequest, 
								this.orderBy, this.dir, this.page));
				mv.addObject("page", this.page );

			}
		} catch ( Exception e) {
			CDBLogger.logWarn(DashboardServlet.class.toString(), e);
		}

		return mv;

	}

	@GetMapping(value = "/Dashboard", params = "orderBy")
	public ModelAndView updateOrderBy(@RequestParam("orderBy") String orderBy) throws ServletException, IOException {
		CDBLogger.logInfo("updateOrderBy " + orderBy);
		List<String> selection = Arrays.asList(orderBy.split(","));
		this.orderBy = selection.get(0);
		this.dir = selection.get(1);
		this.page.setNumeroPage(1);
		return this.updateSearch(this.searchRequest);	}

	@GetMapping(value = "/reset")
	public String reset() {
		System.out.println("tfvygbhnjcdrytvfybguhextsrcdytvfbyg");
		this.searchRequest = RESEARCH_EMPTY;
		this.orderBy = COMPUTER_NAME;
		this.dir = ASCENDANT ;
		this.page.setNumeroPage(1);
		return VUE_DASHBOARD;
	}

	@PostMapping(value = "/Dashboard")
	public ModelAndView delete(String selection) throws ServletException, IOException {
		CDBLogger.logInfo("delete init");
		List<String> selectedId = Arrays.asList(selection.split(","));
		try {
			selectedId.stream()
			.map(s -> Integer.valueOf(s) )
			.forEach(id -> this.computerService.deleteComputerById(id) );
		}catch (Exception e) {
			e.printStackTrace();
			CDBLogger.logWarn(DashboardServlet.class.toString(), e);
		}

		this.page.setNumeroPage(1);
		return this.updateSearch(RESEARCH_EMPTY);	}


}
