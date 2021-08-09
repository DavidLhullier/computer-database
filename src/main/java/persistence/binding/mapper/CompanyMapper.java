package persistence.binding.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import model.Company;

@Component
public class CompanyMapper implements RowMapper<Company> {

	private static final String ID = "id";
	private static final String NAME = "name";
	
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Company(rs.getInt(ID) ,rs.getString(NAME));
	}
	

}