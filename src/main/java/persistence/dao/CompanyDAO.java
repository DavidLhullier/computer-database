package persistence.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.Company;
import persistence.binding.mapper.CompanyMapper;

@Repository
public class CompanyDAO {

	private final String REQUEST_GET_ALL_COMPANY = "SELECT id, name FROM company;";
	private final String REQUEST_GET_ONE_COMPANY_BY_ID = "SELECT id, name FROM company WHERE id = :id ;";

	private final String REQUEST_DELETE_ONE_COMPANY_BY_ID = "DELETE FROM company WHERE id = ? ;";
	private final String REQUEST_DELETE_COMPUTER_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id = ? ;";

	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	private CompanyMapper companyMapper;
	
	@Autowired
	public CompanyDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate,
			CompanyMapper companyMapper) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = jdbcTemplate;
		this.companyMapper = companyMapper;
	}

	public List<Company> getAllCompany() {
		return jdbcTemplate.query(REQUEST_GET_ALL_COMPANY, companyMapper);
	}

	public Company getCompanyById(int id) {		
		MapSqlParameterSource requestParameter = new MapSqlParameterSource().addValue("id", id);
		return namedParameterJdbcTemplate.query(REQUEST_GET_ONE_COMPANY_BY_ID, requestParameter, companyMapper).get(0);
	}

	public void deleteCompanyById(int id) {	
		SqlParameterSource request = new MapSqlParameterSource().addValue("id", id);		
		namedParameterJdbcTemplate.update(REQUEST_DELETE_ONE_COMPANY_BY_ID, request);
	}

}