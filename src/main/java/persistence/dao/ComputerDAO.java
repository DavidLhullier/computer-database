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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import logger.CDBLogger;
import model.Company;
import model.Computer;
import model.Page;
import persistence.DataSource;
import persistence.binding.mapper.ComputerMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;

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

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("computerMapperDAO")
	private ComputerMapper computerMapper;

	public List<Computer> getAllComputer() {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplateDataSource.getConnection();

		List<Company> listComuter = jdbcTemplate.query(REQUEST_GET_ALL_COMPUTER,computerMapper);

		return listComputer;
	}

	public void addComputer(Optional<Computer> computer) {
		jdbcTemplate.update(REQUEST_ADD_COMPUTER, 
				computer.get().getName(),
				Date.valueOf(computer.get().getIntroduced()),
				Date.valueOf(computer.get().getDiscontinued()),
				computer.get().getCompany().getId());

		ComputerDTOAdd computerAdd = this.computerMapper.mapToComputer(computer);
		SqlParameterSource computerparams = new BeanPropertySqlParameterSource(computerAdd);
		namedParameterJdbcTemplate.update(REQUEST_ADD_COMPUTER, computerparams);

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
		JdbcTemplate jdbcTemplate = new JdbcTemplateDataSource.getConnection();
		
		MapSqlParameterSource request = new MapSqlParameterSource().addValue("id", id);
		
		Computer computer = jdbcTemplate.query(REQUEST_GET_ONE_COMPUTER_BY_ID,computerMapper);

		return computer;
		/*
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

		return computer;*/
	}

	public void deleteComputerById(int id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplateDataSource.getConnection();
		
		//jdbcTemplate.

				
		SqlParameterSource request = new MapSqlParameterSource().addValue("id", id);
		
		namedParameterJdbcTemplate.update(REQUEST_DELETE_ONE_COMPUTER_BY_ID, request);			
		
/*
		try( Connection con = DataSource.getConnection();
				PreparedStatement request = con.prepareStatement( REQUEST_DELETE_ONE_COMPUTER_BY_ID ); ){

			request.setInt(1, id);
			request.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
*/
	}

	public void deleteComputerByCompanyId(int id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplateDataSource.getConnection();
		
		//jdbcTemplate.

				
		SqlParameterSource request = new MapSqlParameterSource().addValue("id", id);
		
		namedParameterJdbcTemplate.update(REQUEST_DELETE_COMPUTER_BY_COMPANY_ID, request);			
		
/*
		try( Connection con = DataSource.getConnection();
				PreparedStatement request = con.prepareStatement( REQUEST_DELETE_COMPUTER_BY_COMPANY_ID ); ){

			request.setInt(1, id);
			request.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
*/
	}

	public void editComputerById(int id, Optional<Computer> computer) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate.getDataSource();

		List<Object >parameters = new ArrayList<Object>();

		Computer computerDB = jdbcTemplate.query(REQUEST_GET_ONE_COMPUTER_BY_ID, new SqlParameterValue(Types.INTEGER, computer.get().getId()) ,computerMapper);

		

		if(computer.get().getName() == null) {
			if(computerDB.getName() != null) {
				parameters.add(new SqlParameterValue(Types.VARCHAR, computerDB.get().getName()));
			}
		}
		else {
			parameters.add(new SqlParameterValue(Types.VARCHAR, computer.get().getName()));
		}

		if(computer.get().getIntroduced() == null) {
			parameters.add(new SqlParameterValue(Types.DATE, java.sql.Types.NULL));
		}
		else {
			parameters.add(new SqlParameterValue(Types.DATE, Date.valueOf(computer.get().getIntroduced())));
		}

		if(computer.get().getDiscontinued() == null) {
			parameters.add(new SqlParameterValue(Types.DATE, java.sql.Types.NULL));

		}
		else {
			parameters.add(new SqlParameterValue(Types.DATE, Date.valueOf(computer.get().getDiscontinued())));
		}

		if(computer.get().getCompany().getId() == 0) {
			parameters.add(new SqlParameterValue(Types.INTEGER, java.sql.Types.NULL));

		}
		else {
			parameters.add(new SqlParameterValue(Types.INTEGER, computer.get().getCompany().getId()));
		}
		parameters.add(new SqlParameterValue(Types.INTEGER, computer.get().getId()));

		jdbcTemplate.update(REQUEST_EDIT_ONE_COMPUTER_BY_ID, parameters);

	}

	public int countAllComputer() {
		NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(DataSource.getConnection());

		MapSqlParameterSource vParams = new MapSqlParameterSource();

		int nbComputer = 0;
		try {
			nbComputer = vJdbcTemplate.queryForObject(REQUEST_COUNT_ALL, vParams, Integer.class);
		} catch (SQLException e) {
			CDBLogger.logInfo(e.toString());
		}
		return nbComputer;
	}

	public List<Computer> getComputerPage(Page page ,String orderBy, String dir) {
		JdbcTemplate jdbcTemplate = new JdbcTemplateDataSource.getConnection();
		
		//jdbcTemplate.
		Object[] parameters = {
				 new MapSqlParameterSource().addValue("nbElementBypage",  page.getNbElementByPage()),
				 new MapSqlParameterSource().addValue("nbElementBypage",  page.getNbElementByPage())
				
		};
		
		List<Computer> listComputer = jdbcTemplate.query(REQUEST_GET_COMPUTER_PAGE + "ORDER BY " + orderBy + " "+ dir + END_REQUEST_SEARCH_COMPUTER , parameters);			
		
		return listComputer;
		
		/*
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
		return listComputer;*/
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