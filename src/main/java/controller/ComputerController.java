package controller;

import java.util.List;

import model.Computer;
import service.ComputerService;

public class ComputerController {

	private static ComputerController instance;
	private ComputerService computerService;


	//Singleton
	public static  ComputerController getInstance() {
		if(instance == null) {
			instance = new ComputerController();
		}
		return instance;
	}
	public ComputerController() {
		this.computerService = new ComputerService().getInstance();
	}

	public List<Computer> getAllComputer(){
		return computerService.getAllComputer();
	}
/* ilo faut revoir cette méthode car elle utilise optional pour les servlet # plus tard
	public void addComputer(Computer computer) {
		computerService.addComputer(computer);
	}
*/
	public Computer getComputerById(int id) {
		return computerService.getComputer(id);
	}

	public void deleteComputerById(int id) {
		computerService.deleteComputerById(id);

	}

/*	public void editComputerById(int id, Computer computer) {
		computerService.editComputerById(id, computer);
	}*/


}
