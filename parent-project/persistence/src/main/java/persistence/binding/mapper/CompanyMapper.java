package persistence.binding.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import core.model.Company;
import persistence.dto.CompanyDB;

@Component
public class CompanyMapper implements RowMapper<Company> {

	private static final String ID = "id";
	private static final String NAME = "name";
	
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Company(rs.getInt(ID) ,rs.getString(NAME));
	}

	public Company fromCompanyDBToCompany(CompanyDB companyDB) {
		Company company = new Company(companyDB.getId(),companyDB.getName());
		
		return(company);
	}
	

}