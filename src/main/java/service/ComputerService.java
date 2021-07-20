package service;

import java.util.List;

import controller.CompanyController;
import model.Computer;
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
		this.computerDAO = new ComputerDAO().getInstance();
	}

	public List<Computer> getAllComputer() {
		return computerDAO.getAllComputer();
	}

	public void addComputer(Computer computer) {
		computerDAO.addComputer(computer);
	}

	public Computer getComputer(int id) {
		return computerDAO.getComputerById(id);
	}

	public void deleteComputerById(int id) {
		computerDAO.deleteComputerById(id);

	}

	public void editComputerById(int id, Computer computer) {
		computerDAO.editComputerById(id, computer);

	}

}
