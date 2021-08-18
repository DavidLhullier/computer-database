package persistence.binding.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import model.Company;
import model.Computer;
import model.Computer.ComputerBuilder;
import persistence.dto.CompanyDB;
import persistence.dto.ComputerDB;

@Component
public class ComputerMapper implements RowMapper<Computer> {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DATE_INTRODUCED = "introduced";
	private static final String DATE_DISCONTINUED = "discontinued";
	private static final String COMPANY_ID = "company_id";
	private static final String COMPANY_NAME = "company_name";
	
	private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
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
	
	public Computer fromComputerDBToComputer(ComputerDB computerDB) {
		
		Date start = computerDB.getIntroduced();
		Date end = computerDB.getDiscontinued();
		
		LocalDate introduced = null;
		LocalDate discontinued = null;
		if( start != null ){
			try {
				introduced = LocalDate.parse(start.toString().substring(0, 10), formatter);
				
			} catch (Exception e) {
				logger.error(e.toString());
				e.printStackTrace();
			} 
		}
		
		if( end != null ){
			try {
				discontinued = LocalDate.parse(end.toString().substring(0, 10), formatter);
			} catch (Exception e) {
				logger.error(e.toString());
				e.printStackTrace();
			} 
		}
		
		Computer computer;
		if( computerDB.getCompany() == null) {
			computer = new ComputerBuilder()
					.setName(computerDB.getName())
					.setId(computerDB.getId())
					.withIntroduced(introduced)
					.withDiscontinued(discontinued)
					.build();
		} else {
			computer = new ComputerBuilder()
					.setName(computerDB.getName())
					.setId(computerDB.getId())
					.withIntroduced(introduced)
					.withDiscontinued(discontinued)
					.withCompany(new Company(computerDB.getCompany().getId(),computerDB.getCompany().getName()))
					.build();
		}
		
		return computer;
		
		/*
		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		try {			
			if (computerDB.getId() !=  0) {
				computerBuilder.setId(Integer.valueOf(computerDB.getId()));
			}
			computerBuilder.setName(computerDB.getName());
			
			LocalDate introductionDate = null;
			if (computerDB.getIntroduced() != null) {
				introductionDate = computerDB.getIntroduced();
			}
			computerBuilder.withIntroduced(introductionDate);
			
			LocalDate discontinueDate = null;
			if (computerDB.getDiscontinued() != null) {
				discontinueDate = computerDB.getDiscontinued();

			}
			computerBuilder.withDiscontinued(discontinueDate);

			
			if (computerDB.getCompany() != null) {
				if (computerDB.getCompany().getId() != 0) {
					int idCompany = Integer.valueOf(computerDB.getCompany().getId());
					String nameCompany = computerDB.getCompany().getName();
					computerBuilder.withCompany(new Company(idCompany, nameCompany));
				}
				
			}
			
			return computerBuilder.build();

		} catch (NumberFormatException e) {
			// id dans base de donnée n'est pas un id 
			e.printStackTrace();
		}
		Computer computerTmp = new Computer();;

		return computerTmp;*/
	}

	public ComputerDB fromComputerToComputerDB(Optional<Computer> computer) {
		ComputerDB computerDB = new ComputerDB();		
		
		computerDB.setId(computer.get().getId());
		computerDB.setName(computer.get().getName());
		
		Optional<LocalDate> introduced = Optional.ofNullable(computer.get().getIntroduced());
		Optional<LocalDate> discontinued = Optional.ofNullable(computer.get().getDiscontinued());
		
		if(introduced.isPresent()) {
			computerDB.setIntroduced(Date.valueOf(introduced.get()));
		
		} else {
			computerDB.setIntroduced(null);
		}
		
		if(discontinued.isPresent()) {
			computerDB.setDiscontinued(Date.valueOf(discontinued.get()));
		
		} else {
			computerDB.setDiscontinued(null);
		}
		CompanyDB companyDB = new CompanyDB();
		companyDB.setId(computer.get().getCompany().getId());
		companyDB.setName(computer.get().getCompany().getName());
		computerDB.setCompany(companyDB);
		
		return(computerDB);
	}
	
}