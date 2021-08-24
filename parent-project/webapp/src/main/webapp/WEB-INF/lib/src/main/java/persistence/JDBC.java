package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {

	private final static String URL = "jdbc:mysql://localhost8080/";
	private final static String LOGIN= "root";
	private final static String PASSWORD = "";
	private Connection cn = null;

	public void connect() {

		try {

			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Etape 2 : récupération de la connexion
			this.cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);


		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}



	public Connection getConnection() {
		return this.cn;
	}


	public void sendRequest(String request) {

		try {
			Statement stmt = cn.createStatement();
			ResultSet rs = stmt.executeQuery(request);
		} catch (SQLException e) {

		}
	}
}