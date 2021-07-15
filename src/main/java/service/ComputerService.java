package service;

import java.time.LocalDate;
import java.util.List;

import model.Computer;
import persistence.dao.ComputerDAO;

public class ComputerService {
	
	private ComputerDAO computerDAO;

	public ComputerService() {
		this.computerDAO = new ComputerDAO();
	}
	
	public List<Computer> getAllComputer() {
		return computerDAO.getAllComputer();
	}

	public void addComputerById(Computer computer) {
		computerDAO.addComputerById(computer);
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
