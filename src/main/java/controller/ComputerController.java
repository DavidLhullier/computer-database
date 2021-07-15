package controller;

import java.time.LocalDate;
import java.util.List;

import model.Computer;
import service.ComputerService;

public class ComputerController {

	private ComputerService computerService;
	
	public ComputerController() {
		this.computerService = new ComputerService();
	}
	
	public List<Computer> getAllComputer(){
		return computerService.getAllComputer();
	}

	public void addComputerById(Computer computer) {
		computerService.addComputerById(computer);
	}

	public Computer getComputerById(int id) {
		return computerService.getComputer(id);
	}

	public void deleteComputerById(int id) {
		computerService.deleteComputerById(id);
		
	}

	public void editComputerById(int id, Computer computer) {
		computerService.editComputerById(id, computer);
		
	}

	
}
