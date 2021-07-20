package persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import persistence.JDBC;
import persistence.binding.mapper.CompanyMapper;

public class CompanyDAO {

	private static CompanyDAO instance;
	private CompanyMapper companyMapper;
	private final String REQUEST_GET_ALL_COMPANY = "SELECT id, name FROM company;";
	private final String REQUEST_GET_ONE_COMPANY_BY_ID = "SELECT id, name FROM company WHERE id = ? ;";


	public CompanyDAO() {
		this.companyMapper = new CompanyMapper();
	}


	//Singleton
	/*public static  CompanyDAO getInstance() {
		if(instance == null) {
			instance = new CompanyDAO();
		}
		return instance;
	}*/


	public List<Company> getAllCompany() {
		List<Company> listCompany = new ArrayList<>();

		try( Connection cn = JDBC.getInstance().getConnection(); ) {
			Statement stmt = cn.createStatement();
			ResultSet rs = stmt.executeQuery(REQUEST_GET_ALL_COMPANY);

			while(rs.next()) {
				listCompany.add(this.companyMapper.mapToCompany(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listCompany;
	}

	public Company getCompanyById(int id) {
		Company company = new Company();

		try( Connection cn = JDBC.getInstance().getConnection(); ){
			PreparedStatement request = cn.prepareStatement(REQUEST_GET_ONE_COMPANY_BY_ID);
			request.setInt(1, id);
			ResultSet rs = request.executeQuery();

			rs.next();
			company = this.companyMapper.mapToCompany(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return company;
	}


}
