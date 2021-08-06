package persistence.binding.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import model.Company;

@Component
public class CompanyMapper {

	private static final String COLONNE_ID = "id";
	private static final String COLONNE_NAME = "name";
	
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Company(rs.getInt(COLONNE_ID) ,rs.getString(COLONNE_NAME));
	}
	

}