package persistence.binding.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import model.Company;
import model.Computer;
import model.Computer.ComputerBuilder;

@Component
public class ComputerMapper {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DATE_INTRODUCED = "introduced";
	private static final String DATE_DISCONTINUED = "discontinued";
	private static final String COMPANY_ID = "company_id";
	private static final String COMPANY_NAME = "company.name";


	public Computer mapRow(ResultSet rs) throws SQLException {
		ComputerBuilder computer = new ComputerBuilder();	
		computer.setId(rs.getInt(ID));
		computer.setName(rs.getString(NAME));

		if ( rs.getDate(DATE_INTRODUCED) != null ) {
			computer.withIntroduced(LocalDate.parse(""+rs.getDate(DATE_INTRODUCED)));
		}
		if ( rs.getDate(DATE_DISCONTINUED) != null ) {
			computer.withDiscontinued(LocalDate.parse(""+rs.getDate(DATE_DISCONTINUED)));
		}
		if ( rs.getString(COMPANY_ID) != null ) {
			computer.withCompany(new Company(Integer.valueOf(""+rs.getString(COMPANY_ID)),
					rs.getString(COMPANY_NAME)));
		}

		return computer.build();
	}

	public Computer mapToComputer(ResultSet rs) {
		ComputerBuilder computer = new ComputerBuilder();

		try {
			computer.setId(Integer.parseInt(rs.getString("id")));
			computer.setName(rs.getString("name"));



			if(rs.getDate("introduced") != null) {
				LocalDate tmpDate = rs.getDate("introduced").toLocalDate();
				computer.withIntroduced(tmpDate);
			}

			if(rs.getDate("discontinued") != null) {
				LocalDate tmpDate = rs.getDate("discontinued").toLocalDate();
				computer.withDiscontinued(tmpDate);
			}


			String tmpString = rs.getString("company_id");
			if(tmpString != null) {
				Company company = new Company(Integer.valueOf(tmpString),rs.getString("company_name"));
				computer.withCompany(company);
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