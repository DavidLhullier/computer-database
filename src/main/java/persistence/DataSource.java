package persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import logger.CDBLogger;

@Configuration
//@ComponentScan("persistence")
public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private final static String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	private final static String LOGIN= "admincdb";
	private final static String PASSWORD = "qwerty1234";

    static {
    	config.setDriverClassName("com.mysql.cj.jdbc.Driver");
    	config.setJdbcUrl( URL );
        config.setUsername( LOGIN );
        config.setPassword( PASSWORD );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    public static Connection getConnection() throws SQLException {
    	CDBLogger.logInfo(DataSource.class.toString(), "HikariCP connection") ;
		return ds.getConnection();
    }
    
    
    
}