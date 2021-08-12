package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Computer;
import model.Page;
import persistence.dao.ComputerDAO;

@Service
public class ComputerService {

	private ComputerDAO computerDAO;

	@Autowired
	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
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

	public void deleteComputerByCompanyId(int id) {
		this.computerDAO.deleteComputerByCompanyId(id);		
	}
}
