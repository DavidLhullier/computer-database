package persistence.binding.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;

public class CompanyMapper {

	public Company mapToCompany(ResultSet rs) {
		Company company = new Company();
		try {
			company.setId(Integer.parseInt(rs.getString("id")));
			company.setName(rs.getString("name"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return company;
	}
	
}
