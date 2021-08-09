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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logger.CDBLogger;
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
	private final String REQUEST_GET_ONE_COMPUTER_BY_ID = "SELECT cp.id, cp.name, cp.introduced, cp.discontinued, cp.company_id, cny.name as company_name FROM computer as cp LEFT JOIN company as cny on cny.id= cp.company_id WHERE cp.id = :id ;";
	private final String REQUEST_ADD_COMPUTER = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
	private final String REQUEST_DELETE_ONE_COMPUTER_BY_ID = "DELETE FROM computer WHERE id = :id ;";
	private final String REQUEST_DELETE_COMPUTER_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id = :companyId ;";


	private final String REQUEST_EDIT_ONE_COMPUTER_BY_ID = "UPDATE computer SET name = ? , introduced = ? , discontinued = ? , company_id = ? WHERE id = ? ;";
	private final String REQUEST_GET_ALL_COMPUTER_WITH_RESEARCH_AND_ORDER_BY ="SELECT cp.id, cp.name, cp.introduced, cp.discontinued, cp.company_id, cny.name as company_name FROM computer as cp LEFT JOIN company as cny on cny.id= cp.company_id WHERE cp.name LIKE ? OR  cny.name LIKE ? ORDER BY ";
	private final String END_REQUEST_SEARCH_COMPUTER = " LIMIT ? OFFSET ? ;";

	//SELECT cp.id, cp.name, cp.introduced, cp.discontinued, cp.company_id, cny.name as company_name FROM computer as cp LEFT JOIN company as cny on cny.id= cp.company_id WHERE cp.id = 4;


	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	private ComputerMapper computerMapper;

	public ComputerDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate,
			ComputerMapper computerMapper) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
		this.computerMapper = computerMapper;
	}

	public List<Computer> getAllComputer() {
		return jdbcTemplate.query(REQUEST_GET_ALL_COMPUTER,computerMapper);
	}

	public void addComputer(Optional<Computer> computer) {
		jdbcTemplate.update(REQUEST_ADD_COMPUTER, computer.get().getName(),
				Date.valueOf(computer.get().getIntroduced()),
				Date.valueOf(computer.get().getDiscontinued()),
				computer.get().getCompany().getId());
	}

	public Computer getComputerById(int id) {
		MapSqlParameterSource requestParameter = new MapSqlParameterSource().addValue("id", id);
		return namedParameterJdbcTemplate.query(REQUEST_GET_ONE_COMPUTER_BY_ID, requestParameter, computerMapper).get(0);
	}

	public void deleteComputerById(int id) {
		SqlParameterSource request = new MapSqlParameterSource().addValue("id", id);
		namedParameterJdbcTemplate.update(REQUEST_DELETE_ONE_COMPUTER_BY_ID, request);	
	}

	public void deleteComputerByCompanyId(int id) {
		SqlParameterSource request = new MapSqlParameterSource().addValue("companyId", id);
		namedParameterJdbcTemplate.update(REQUEST_DELETE_COMPUTER_BY_COMPANY_ID, request);			
	}

	public void editComputerById(int id, Optional<Computer> computer) {

		MapSqlParameterSource parameterId = new MapSqlParameterSource().addValue("id", id);
		Computer computerDB = namedParameterJdbcTemplate.query(REQUEST_GET_ONE_COMPUTER_BY_ID, parameterId, computerMapper).get(0);

		Object[] parameters = new Object[5];
		int[] type = {Types.VARCHAR, Types.DATE, Types.DATE, Types.INTEGER, Types.INTEGER };

		if(computer.get().getName() == null) {
			if(computerDB.getName() != null) {
				parameters[0] = computerDB.getName();
			}
		}
		else {
			parameters[0]= computer.get().getName();
		}

		if(computer.get().getIntroduced() == null) {
			parameters[1] = java.sql.Types.NULL;
		}
		else {
			parameters[1] = Date.valueOf(computer.get().getIntroduced());
		}

		if(computer.get().getDiscontinued() == null) {
			parameters[2] = java.sql.Types.NULL;
		}
		else {
			parameters[2] = Date.valueOf(computer.get().getDiscontinued());
		}

		if(computer.get().getCompany().getId() == 0) {
			parameters[3] = java.sql.Types.NULL;

		}
		else {
			parameters[3] =computer.get().getCompany().getId();
		}
		parameters[4] = id;

		jdbcTemplate.update(REQUEST_EDIT_ONE_COMPUTER_BY_ID, parameters, type);

	}

	public int countAllComputer() {
		return jdbcTemplate.queryForObject(REQUEST_COUNT_ALL, Integer.class);
	}

	public List<Computer> getComputerPage(Page page ,String orderBy, String dir) {
		int[] type = {Types.INTEGER, Types.INTEGER };
		Object[] parameters = {
				page.getNbElementByPage(),
				page.getNbElementByPage()*(page.getNumeroPage()-1)
		};

		return jdbcTemplate.query(REQUEST_GET_COMPUTER_PAGE 
				+ "ORDER BY " + orderBy + " "+ dir + END_REQUEST_SEARCH_COMPUTER,
				parameters, type, computerMapper);
	}

	public List<Computer> getComputerResearch(String research, String order, String dir, Page page) {
		Object[] parameters = new Object[4];
		int[] type = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER };

		if(research == null || research.isEmpty()) {
			parameters[0] = " ";
			parameters[1] = " ";
		} else {
			parameters[0] = "%"+research+"%" ;
			parameters[1] = "%"+research+"%" ;
		}
		parameters[2] = page.getNbElementByPage();
		parameters[3] = page.getNbElementByPage()*(page.getNumeroPage()-1);
		
		
		return jdbcTemplate.query(REQUEST_GET_ALL_COMPUTER_WITH_RESEARCH_AND_ORDER_BY +
				 order + " " + dir + END_REQUEST_SEARCH_COMPUTER,
				 parameters, type, computerMapper);
	}

	public int countAllComputerWithSearch(String research) {
		Object[] parameters = new Object[2];
		int[] type = {Types.VARCHAR, Types.VARCHAR };

		if(research == null || research.isEmpty()) {
			parameters[0] = " ";
			parameters[1] = " ";
		} else {
			parameters[0] = "%"+research+"%" ;
			parameters[1] = "%"+research+"%" ;
		}
		
		return jdbcTemplate.queryForObject(REQUEST_COUNT_COMPUTER_WITH_SEARCH,
				 parameters, type, Integer.class);
	}


}