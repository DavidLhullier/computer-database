package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import persistence.JDBC;
import ui.CLIReader;
import model.Computer;

public class Main {

	public static void main(String[] args) {
		//testCLIetJDBC();
		
		//testGetAllComputers();
		//testGetAllCompanies();
		//testGetOneComputer();
		//testAddUpdateDeleteComputer();
		
		
	}
	
	/*private static void	testAddUpdateDeleteComputer() {
		String pcname = "Pcs name";
		testAddComputer(pcname);
		System.out.println();
		testUpdateComputer(pcname);
		System.out.println();
		testDeleteComputer(pcname);
		testGetAllComputers();
	}
	
	private static void testUpdateComputer(String pcname) {
		JDBC jdbc = new JDBC();
		jdbc.connect();
		Connection cn = jdbc.getConnection();

		CLIReader cli = new CLIReader();
		System.out.println("Enter MySQL request with UPDATE computer SET company_id = '4' WHERE name = 'Pcs name'; :");
		//String request = cli.readRequest();
		String request = "UPDATE computer SET company_id = '4' WHERE name = '"+pcname+"';";
		jdbc.insertRequest(request);
		
		testGetOneComputer();
	}

	private static void testDeleteComputer(String pcname) {
		JDBC jdbc = new JDBC();
		jdbc.connect();
		Connection cn = jdbc.getConnection();

		CLIReader cli = new CLIReader();
		System.out.println("Enter MySQL request with DELETE FROM computer WHERE name = 'Pcs name'; :");
		//String request = cli.readRequest();
		String request = "DELETE FROM computer WHERE name = '" + pcname+ "';";
		jdbc.insertRequest(request);
		
		testGetOneComputer();
	}

	private static void testAddComputer(String pcname) {
		JDBC jdbc = new JDBC();
		jdbc.connect();
		Connection cn = jdbc.getConnection();

		CLIReader cli = new CLIReader();
		System.out.println("Enter MySQL request with INSERT INTO computer (name) VALUES ('Pcs name'); :");
		//String request = cli.readRequest();
		String request = "INSERT INTO computer (name) VALUES ('"+ pcname +"');";
		jdbc.insertRequest(request);
		
		testGetOneComputer();
	}

	private static void testGetOneComputer() {
		JDBC jdbc = new JDBC();
		jdbc.connect();
		Connection cn = jdbc.getConnection();

		CLIReader cli = new CLIReader();
		System.out.println("Enter MySQL request with SELECT * FROM computer WHERE name = 'Pcs name';");
		//String request = cli.readRequest();
		String request = "SELECT * FROM computer WHERE name = 'Pcs name';";
		try {
			ResultSet rs = jdbc.sendRequest(request);
			rs.next();
			System.out.println("id : " + rs.getString("id"));
			System.out.println("name : " + rs.getString("name"));
			System.out.println("introduced : " + rs.getString("introduced"));
			System.out.println("dicontinued : " + rs.getString("discontinued"));
			System.out.println("company_id : " + rs.getString("company_id"));
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	private static void testGetAllCompanies() {
		JDBC jdbc = new JDBC();
		jdbc.connect();
		Connection cn = jdbc.getConnection();
		
		Computer computer = new Computer();
		//computer.getAllCompanies();
		
	}

	private static void testGetAllComputers() {
		JDBC jdbc = new JDBC();
		jdbc.connect();
		Connection cn = jdbc.getConnection();
		
		Computer computer = new Computer();
		//computer.getAllComputers();
		
	}

	public static void testCLIetJDBC() {
		JDBC jdbc = new JDBC();
		jdbc.connect();
		Connection cn = jdbc.getConnection();

		CLIReader cli = new CLIReader();
		System.out.println("Enter MySQL request :");
		String request = cli.readRequest();
				//"SELECT * FROM computer;";
		try {
			ResultSet rs = jdbc.sendRequest(request);
			rs.next();
			System.out.println("id : " + rs.getString("id"));
			System.out.println("name : " + rs.getString("name"));
			System.out.println("introduced : " + rs.getString("introduced"));
			System.out.println("dicontinued : " + rs.getString("discontinued"));
			System.out.println("company_id : " + rs.getString("company_id"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/

}
