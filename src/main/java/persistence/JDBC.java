package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {

	private final static String URL = "jdbc:mysql://localhost/computer-database-db";
	private final static String LOGIN= "admincdb";
	private final static String PASSWORD = "qwerty1234";
	private static  Connection cn = null;
	private static JDBC jdbc;

	private JDBC() {
		try {
			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.jdbc.Driver");

			// Etape 2 : récupération de la connexion
			cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static JDBC getInstance() {
		if(jdbc == null) {
			jdbc = new JDBC();
		}
		return jdbc;
	}

	public Connection getConnection() throws SQLException {
		if(cn == null || cn.isClosed()) {
			cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
		}
		return cn;
	}


}