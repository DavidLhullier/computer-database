package model;

public class Page {

	private int nbElementByPage = 10;
	private int nbElementDB;
	private int numeroPage = 1;
	
	public int getTotalPage() {
		int modulo = 0;
		if(nbElementDB % nbElementByPage != 0 ) {
			modulo = 1;
		}
		return nbElementDB / nbElementByPage + modulo;
	}
	
	public int getNbElementByPage() {
		return nbElementByPage;
	}
	public void setNbElementByPage(int nbElementByPage) {
		this.nbElementByPage = nbElementByPage;
	}
	public int getNbElementDB() {
		return nbElementDB;
	}
	public void setNbElementDB(int nbElementDB) {
		this.nbElementDB = nbElementDB;
	}
	public int getNumeroPage() {
		return numeroPage;
	}
	public void setNumeroPage(int numeroPage) {
		this.numeroPage = numeroPage;
	}
	
	
	
	
}
