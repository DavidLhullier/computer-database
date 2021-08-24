package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import persistence.JDBC;



public class Main {

	public static void main(String[] args) {
		JDBC jdbc = new JDBC();
		jdbc.connect();
		Connection cn = jdbc.getConnection();

		String request = "SELECT * FROM computer";
		
		jdbc.sendRequest(request);
	}

}
