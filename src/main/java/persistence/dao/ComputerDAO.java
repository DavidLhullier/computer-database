package persistence.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Computer;
import model.Computer.ComputerBuilder;
import persistence.JDBC;
import persistence.binding.mapper.ComputerMapper;

public class ComputerDAO {

	private static ComputerDAO instance;
	private ComputerMapper computerMapper;
	private final String REQUEST_GET_ALL_COMPUTER = "SELECT cp.id, cp.name, cp.introduced, cp.discontinued, cp.company_id, cny.name as company_name FROM computer as cp LEFT JOIN company as cny on cny.id= cp.company_id ;";
	private final String REQUEST_GET_ONE_COMPUTER_BY_ID = "SELECT cp.id, cp.name, cp.introduced, cp.discontinued, cp.company_id, cny.name as company_name FROM computer as cp LEFT JOIN company as cny on cny.id= cp.company_id WHERE cp.id = ? ;";
	private final String REQUEST_ADD_COMPUTER = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
	private final String REQUEST_DELETE_ONE_COMPUTER_BY_ID = "DELETE FROM computer WHERE id = ? ;";
	private final String REQUEST_EDIT_ONE_COMPUTER_BY_ID = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? , company_id = ? WHERE id = ?;";
	//SELECT cp.id, cp.name, cp.introduced, cp.discontinued, cp.company_id, cny.name as company_name FROM computer as cp LEFT JOIN company as cny on cny.id= cp.company_id WHERE cp.id = 4;

	public ComputerDAO() {
		this.computerMapper = new ComputerMapper();
	}

	//Singleton
	public static  ComputerDAO getInstance() {
		if(instance == null) {
			instance = new ComputerDAO();
		}
		return instance;
	}


	public List<Computer> getAllComputer() {
		List<Computer> listComputer = new ArrayList<>();

		try(Connection cn = JDBC.getInstance().getConnection();){
			Statement stmt = cn.createStatement();
			ResultSet rs = stmt.executeQuery(REQUEST_GET_ALL_COMPUTER);
			while(rs.next()) {
				listComputer.add(this.computerMapper.mapToComputer(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listComputer;
	}

	public void addComputer(Computer computer) {
		try( Connection cn = JDBC.getInstance().getConnection(); ){
			PreparedStatement request = cn.prepareStatement(REQUEST_ADD_COMPUTER);

			if(computer.getName() != null) {
				request.setString(1, computer.getName());
			}
			if(computer.getIntroduced() != null) {
				request.setDate(2, Date.valueOf(computer.getIntroduced()) );
			}
			if(computer.getDiscontinued() != null) {
				request.setDate(3, Date.valueOf(computer.getDiscontinued()) );
			}
			if(computer.getCompany() != null) {
				request.setInt(4, computer.getCompany().getId());
			}		
			request.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Computer getComputerById(int id) {
		Computer computer = new Computer();
		try( Connection cn = JDBC.getInstance().getConnection(); ){
			PreparedStatement request = cn.prepareStatement(REQUEST_GET_ONE_COMPUTER_BY_ID);
			request.setInt(1, id);
			ResultSet rs = request.executeQuery();

			rs.next();
			 computer = this.computerMapper.mapToComputer(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computer;
	}

	public void deleteComputerById(int id) {


		try( Connection cn = JDBC.getInstance().getConnection(); ){
			PreparedStatement request = cn.prepareStatement(REQUEST_DELETE_ONE_COMPUTER_BY_ID);
			request.setInt(1, id);
			request.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void editComputerById(int id, Computer computer) {

		Computer computerDB = new Computer();
		try( Connection cn = JDBC.getInstance().getConnection(); ){
			PreparedStatement requestDB = cn.prepareStatement(REQUEST_GET_ONE_COMPUTER_BY_ID);
			requestDB.setInt(1, id);
			ResultSet rs = requestDB.executeQuery();

			rs.next();
			computerDB = this.computerMapper.mapToComputer(rs);

			PreparedStatement request = cn.prepareStatement(REQUEST_EDIT_ONE_COMPUTER_BY_ID);
			if(computer.getName() == null) {
				request.setString(1, computerDB.getName());
			}
			else {
				request.setString(1, computer.getName());
			}
			if(computer.getIntroduced() == null) {
				request.setDate(2, Date.valueOf(computerDB.getIntroduced()) );	
			}
			else {
				request.setDate(2, Date.valueOf(computer.getIntroduced()) );
			}
			if(computer.getDiscontinued() == null) {
				request.setDate(3, Date.valueOf(computerDB.getDiscontinued()) );	
			}
			else {
				request.setDate(3, Date.valueOf(computer.getDiscontinued()) );
			}
			if(computer.getCompany().getId() == 0) {
				request.setInt(4, computerDB.getCompany().getId());
			}
			else {
				request.setInt(4, computer.getCompany().getId());		
			}
			request.setInt(5, id);
			request.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
