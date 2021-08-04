package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import model.Computer;
import service.ComputerService;

@Controller
public class ComputerController {

	@Autowired
	private ComputerService computerService;


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
