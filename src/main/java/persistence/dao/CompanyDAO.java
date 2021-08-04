package persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import logger.CDBLogger;
import model.Company;
import persistence.DataSource;
import persistence.binding.mapper.CompanyMapper;

@Repository
public class CompanyDAO {

	private final String REQUEST_GET_ALL_COMPANY = "SELECT id, name FROM company;";
	private final String REQUEST_GET_ONE_COMPANY_BY_ID = "SELECT id, name FROM company WHERE id = ? ;";
	
	private final String REQUEST_START_TRANSACTION = "START TRANSACTION ;";
	private final String REQUEST_ROLLBACK = "ROLLBACK;";
	private final String REQUEST_COMMIT = "COMMIT;";
	
	private final String REQUEST_DELETE_ONE_COMPANY_BY_ID = "DELETE FROM company WHERE id = ? ;";
	private final String REQUEST_DELETE_COMPUTER_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id = ? ;";
	
	
	@Autowired
	@Qualifier("companyMapperDAO") //dans le cas où j'ai un autre mapper dao
	private CompanyMapper companyMapper;
	


	public List<Company> getAllCompany() {
		List<Company> listCompany = new ArrayList<>();

		try( Connection con = DataSource.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(REQUEST_GET_ALL_COMPANY); ) {

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

		try( Connection con = DataSource.getConnection(); 
				PreparedStatement request = con.prepareStatement(REQUEST_GET_ONE_COMPANY_BY_ID);){
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


	public void deleteCompanyById(int id) {
		try(Connection con = DataSource.getConnection();) {
			
			
			try( PreparedStatement request = con.prepareStatement(
		            		REQUEST_START_TRANSACTION ); ){
				request.execute();

				
				PreparedStatement deleteComputerRequest = con.prepareStatement(
	            		REQUEST_DELETE_COMPUTER_BY_COMPANY_ID ); 
				deleteComputerRequest.setInt(1, id);
				deleteComputerRequest.executeUpdate();
				
				
				PreparedStatement deleteComapnyRequest = con.prepareStatement(
	            		REQUEST_DELETE_ONE_COMPANY_BY_ID ); 
				deleteComapnyRequest.setInt(1, id);
				deleteComapnyRequest.executeUpdate();
				
				
				PreparedStatement commitRequest = con.prepareStatement(REQUEST_COMMIT);
				commitRequest.execute();
				
				CDBLogger.logInfo(ComputerDAO.class.toString(), "Company and Computer delete");

			} catch (SQLException e) {
				e.printStackTrace();
				PreparedStatement rollbackRequest = con.prepareStatement(REQUEST_ROLLBACK);
				rollbackRequest.execute();
				CDBLogger.logInfo(ComputerDAO.class.toString(), "Company and Computer not delete");
				
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		

		
	}


}
