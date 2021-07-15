package ui;

import java.util.Scanner;

public class CLIReader {
	
	private boolean scannerStatus = true;
	private static final Scanner sc = new Scanner(System.in);
	
	private void canRead() {
		this.scannerStatus = true;
	}
	
	public String readRequest() {
		if(this.scannerStatus) {
			return this.sc.nextLine();
		}
		else {
			System.out.println("Scanner not available");
			return null;
		}
	}

	
}
