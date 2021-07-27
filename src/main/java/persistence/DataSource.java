package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import logger.CDBLogger;
import model.Company;
import model.Computer;
import model.Computer.ComputerBuilder;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private final static String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	private final static String LOGIN= "admincdb";
	private final static String PASSWORD = "qwerty1234";

    static {
        config.setJdbcUrl( URL );
        config.setUsername( LOGIN );
        config.setPassword( PASSWORD );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    private DataSource() {}
    
    
    

    public static Connection getConnection() throws SQLException {
    	CDBLogger.logInfo(DataSource.class.toString(), "HikariCP connection") ;
		return ds.getConnection();
    }
    
    
    public static List<Computer> fetchData() throws SQLException {
        String SQL_QUERY = "select * from computer;";
        List<Computer> listComputer = null;
        try (Connection con = DataSource.getConnection();
            PreparedStatement pst = con.prepareStatement( SQL_QUERY );
            ResultSet rs = pst.executeQuery();) {
                listComputer = new ArrayList<>();
                ComputerBuilder computerBuilder =  new ComputerBuilder();
                while ( rs.next() ) {
                	
        			computerBuilder.setId(rs.getInt( "id")).
                				setName(rs.getString("name"));
        			
        			if(rs.getDate("introduced") != null) {
        				LocalDate tmpDate = rs.getDate("introduced").toLocalDate();
        				computerBuilder.setIntroduced(tmpDate);
        			}

        			if(rs.getDate("discontinued") != null) {
        				LocalDate tmpDate = rs.getDate("discontinued").toLocalDate();
        				computerBuilder.setDiscontinued(tmpDate);
        			}
        			

        			String tmpString = rs.getString("company_id");
        			if(tmpString != null) {
        				Company company = new Company(Integer.valueOf(tmpString),"ctvfygbuh");
        				computerBuilder.setCompany(company);
        			}
                    Computer computer = computerBuilder.build();
                    listComputer.add(computer);
                }
    	} 
        return listComputer;
    }
}