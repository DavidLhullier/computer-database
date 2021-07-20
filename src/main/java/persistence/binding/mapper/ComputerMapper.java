package persistence.binding.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import model.Company;
import model.Computer;
import model.Computer.ComputerBuilder;

public class ComputerMapper {

	public Computer mapToComputer(ResultSet rs) {
		ComputerBuilder computer = new ComputerBuilder();

		try {
			computer.setId(Integer.parseInt(rs.getString("id")));
			computer.setName(rs.getString("name"));

			
			
			if(rs.getDate("introduced") != null) {
				LocalDate tmpDate = rs.getDate("introduced").toLocalDate();
				computer.setIntroduced(tmpDate);
			}

			if(rs.getDate("discontinued") != null) {
				LocalDate tmpDate = rs.getDate("discontinued").toLocalDate();
				computer.setDiscontinued(tmpDate);
			}
			

			String tmpString = rs.getString("company_id");
			if(tmpString != null) {
				Company company = new Company(Integer.valueOf(tmpString),rs.getString("company_name"));
				computer.setCompany(company);
			}

		} catch (NumberFormatException e) {
			// id dans base de donnée n'est pas un id 
			e.printStackTrace();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		Computer computerTmp = computer.build();
		
		return computerTmp;
	}

}
