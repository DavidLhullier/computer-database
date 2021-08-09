package controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

	public void addComputer(Optional<Computer> computer) {
		computerService.addComputer(computer);
	}

	public Computer getComputerById(int id) {
		return computerService.getComputer(id);
	}

	public void deleteComputerById(int id) {
		computerService.deleteComputerById(id);

	}

	public void editComputerById(int id, Optional<Computer> computer) {
		computerService.editComputerById(id, computer);
	}


}
