package service;

import java.util.List;
import java.util.Optional;


import model.Computer;
import model.Page;
import persistence.dao.ComputerDAO;

public class ComputerService {

	private static ComputerService instance;
	private ComputerDAO computerDAO;

	//Singleton
	public static  ComputerService getInstance() {
		if(instance == null) {
			instance = new ComputerService();
		}
		return instance;
	}

	public ComputerService() {
		this.computerDAO = ComputerDAO.getInstance();
	}

	public List<Computer> getAllComputer() {
		return computerDAO.getAllComputer();
	}

	public void addComputer(Optional<Computer> computer) {
		computerDAO.addComputer(computer);
	}

	public Computer getComputer(int id) {
		return computerDAO.getComputerById(id);
	}

	public void deleteComputerById(int id) {
		computerDAO.deleteComputerById(id);

	}

	public void editComputerById(int id, Optional<Computer> computer) {
		computerDAO.editComputerById(id, computer);

	}

	public int countAllComputer() {
		return computerDAO.countAllComputer();
	}

	public List<Computer> getComputerPage(Page page, String orderBy, String dir) {
		return computerDAO.getComputerPage(page, orderBy, dir);
	}
	
	public List<Computer> getComputerResearch(String research, String order, String dir, Page page){
		return computerDAO.getComputerResearch(research, order, dir, page);
	}

	public int countAllComputerWithSearch(String search) {
		return computerDAO.countAllComputerWithSearch(search);
	}
}
