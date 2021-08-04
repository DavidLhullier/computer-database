package persistence.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import model.Computer;
import model.Page;
import persistence.DataSource;
import persistence.binding.mapper.ComputerMapper;

@Repository
public class ComputerDAO {

	private final String REQUEST_COUNT_ALL = "SELECT COUNT(*) FROM computer ; ";
	private final String REQUEST_COUNT_COMPUTER_WITH_SEARCH = "SELECT COUNT(*) FROM computer as cp LEFT JOIN company as cny on cny.id= cp.company_id WHERE cp.name LIKE ? OR  cny.name LIKE ? ; ";
	private final String REQUEST_GET_COMPUTER_PAGE = "SELECT cp.id, cp.name, cp.introduced, cp.discontinued, cp.company_id, cny.name as company_name FROM computer as cp LEFT JOIN company as cny on cny.id= cp.company_id ";
	private final String REQUEST_GET_ALL_COMPUTER = "SELECT cp.id, cp.name, cp.introduced, cp.discontinued, cp.company_id, cny.name as company_name FROM computer as cp LEFT JOIN company as cny on cny.id= cp.company_id ;";
	private final String REQUEST_GET_ONE_COMPUTER_BY_ID = "SELECT cp.id, cp.name, cp.introduced, cp.discontinued, cp.company_id, cny.name as company_name FROM computer as cp LEFT JOIN company as cny on cny.id= cp.company_id WHERE cp.id = ? ;";
	private final String REQUEST_ADD_COMPUTER = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
	private final String REQUEST_DELETE_ONE_COMPUTER_BY_ID = "DELETE FROM computer WHERE id = ? ;";
	private final String REQUEST_DELETE_COMPUTER_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id = ? ;";
	
	
	private final String REQUEST_EDIT_ONE_COMPUTER_BY_ID = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? , company_id = ? WHERE id = ?;";
	private final String REQUEST_GET_ALL_COMPUTER_WITH_RESEARCH_AND_ORDER_BY ="SELECT cp.id, cp.name, cp.introduced, cp.discontinued, cp.company_id, cny.name as company_name FROM computer as cp LEFT JOIN company as cny on cny.id= cp.company_id WHERE cp.name LIKE ? OR  cny.name LIKE ? ORDER BY ";
	private final String END_REQUEST_SEARCH_COMPUTER = " LIMIT ? OFFSET ? ;";

	//SELECT cp.id, cp.name, cp.introduced, cp.discontinued, cp.company_id, cny.name as company_name FROM computer as cp LEFT JOIN company as cny on cny.id= cp.company_id WHERE cp.id = 4;

	@Autowired
	@Qualifier("computerMapperDAO")
	private ComputerMapper computerMapper;

	public List<Computer> getAllComputer() {
		List<Computer> listComputer = new ArrayList<>();

		try(Connection con = DataSource.getConnection();
	            PreparedStatement request = con.prepareStatement( REQUEST_GET_ALL_COMPUTER );
	            ResultSet rs = request.executeQuery();){

			while(rs.next()) {
				listComputer.add(this.computerMapper.mapToComputer(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listComputer;
	}

	public void addComputer(Optional<Computer> computer) {
		try( Connection con = DataSource.getConnection();
	            PreparedStatement request = con.prepareStatement( REQUEST_ADD_COMPUTER );){
			
			if(computer.get().getName() != null) {
				request.setString(1, computer.get().getName());
			}
			if(computer.get().getIntroduced() != null) {
				request.setDate(2, Date.valueOf(computer.get().getIntroduced()) );
			}else {
			request.setNull(2, Types.TIMESTAMP);
			}
			if(computer.get().getDiscontinued() != null) {
				request.setDate(3, Date.valueOf(computer.get().getDiscontinued()) );
			}else {

				request.setNull(3, Types.TIMESTAMP);
			}
			if(computer.get().getCompany() != null && computer.get().getCompany().getId() != 0) {
				request.setInt(4, computer.get().getCompany().getId());
			}else {

				request.setNull(4, Types.BIGINT);
			}
			request.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Computer getComputerById(int id) {
		Computer computer = new Computer();
		try( Connection con = DataSource.getConnection();
	            PreparedStatement request = con.prepareStatement( REQUEST_GET_ONE_COMPUTER_BY_ID ); ){
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


		try( Connection con = DataSource.getConnection();
	            PreparedStatement request = con.prepareStatement( REQUEST_DELETE_ONE_COMPUTER_BY_ID ); ){

			request.setInt(1, id);
			request.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void deleteComputerByCompanyId(int id) {


		try( Connection con = DataSource.getConnection();
	            PreparedStatement request = con.prepareStatement( REQUEST_DELETE_COMPUTER_BY_COMPANY_ID ); ){

			request.setInt(1, id);
			request.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void editComputerById(int id, Optional<Computer> computer) {

		Computer computerDB = new Computer();
		try( Connection con = DataSource.getConnection();
	            PreparedStatement requestDB = con.prepareStatement( REQUEST_GET_ONE_COMPUTER_BY_ID ); 
				PreparedStatement request = con.prepareStatement(REQUEST_EDIT_ONE_COMPUTER_BY_ID);){
			
			requestDB.setInt(1, id);
			ResultSet rs = requestDB.executeQuery();

			rs.next();
			computerDB = this.computerMapper.mapToComputer(rs);

			
			if(computer.get().getName() == null) {
				if(computerDB.getName() != null) {
					request.setString(1, computerDB.getName());
				}
			}
			else {
				request.setString(1, computer.get().getName());
			}
			
			if(computer.get().getIntroduced() == null) {
				request.setNull(2, java.sql.Types.NULL);
				
			}
			else {
				request.setDate(2, Date.valueOf(computer.get().getIntroduced()) );
			}

			if(computer.get().getDiscontinued() == null) {
				request.setNull(3, java.sql.Types.NULL);
				
			}
			else {
				request.setDate(3, Date.valueOf(computer.get().getDiscontinued()) );
			}
			
			if(computer.get().getCompany().getId() == 0) {
				request.setNull(4, java.sql.Types.NULL);
				
			}
			else {
				request.setInt(4, computer.get().getCompany().getId());		
			}
			request.setInt(5, id);
			
			request.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int countAllComputer() {
		int nbComputer = 0;
		try( Connection con = DataSource.getConnection();
	            PreparedStatement request = con.prepareStatement( REQUEST_COUNT_ALL );
				ResultSet rs = request.executeQuery();){
			
			rs.next();
			nbComputer = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nbComputer;
	}

	public List<Computer> getComputerPage(Page page ,String orderBy, String dir) {
		List<Computer> listComputer = new ArrayList<>();

		try(Connection con = DataSource.getConnection();
	            PreparedStatement request = con.prepareStatement( REQUEST_GET_COMPUTER_PAGE + "ORDER BY " + orderBy + " "+ dir + END_REQUEST_SEARCH_COMPUTER );){
			request.setInt(1, page.getNbElementByPage());
			request.setInt(2, page.getNbElementByPage()*(page.getNumeroPage()-1) );

			ResultSet rs = request.executeQuery();
			while(rs.next()) {
				listComputer.add(this.computerMapper.mapToComputer(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listComputer;
	}
	
	
	public List<Computer> getComputerResearch(String research, String order, String dir, Page page) {
		List<Computer> listComputer = new ArrayList<>();
		
		try( Connection con = DataSource.getConnection();
	            PreparedStatement request = con.prepareStatement( REQUEST_GET_ALL_COMPUTER_WITH_RESEARCH_AND_ORDER_BY +
						order + " " + dir + END_REQUEST_SEARCH_COMPUTER); ){
			if(research == null || research.isEmpty()) {
				request.setString(1, "");
				request.setString(2, "");
			} else {
				request.setString(1, "%"+research+"%");
				request.setString(2, "%"+research+"%");
			}
			
			request.setInt(3, page.getNbElementByPage());
			request.setInt(4, page.getNbElementByPage()*(page.getNumeroPage()-1) );
			
			ResultSet rs = request.executeQuery();
			
			while(rs.next()) {
				listComputer.add(this.computerMapper.mapToComputer(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//finalrequest + order + endfinal requets
		return listComputer;
	}

	

	public int countAllComputerWithSearch(String research) {
		
		int nbComputer = 0;
		try( Connection con = DataSource.getConnection();
	            PreparedStatement request = con.prepareStatement( REQUEST_COUNT_COMPUTER_WITH_SEARCH ); ){
			if(research == null || research.isEmpty()) {
				request.setString(1, "");
				request.setString(2, "");
			} else {
				request.setString(1, "%"+research+"%");
				request.setString(2, "%"+research+"%");
			}
			ResultSet rs = request.executeQuery();

			rs.next();
			nbComputer = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nbComputer;
	}


}
