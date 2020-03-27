package controlador;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import modelo.*;
import vista.*;

public class Control {
	private Vista vista;
	private DbManager dbManager;
	private FileManager fileManager;
	private HashMap<Integer, Elemento> elementos = new HashMap<Integer, Elemento>();

	public Control() {
		dbManager = new DbManager();
		fileManager = new FileManager();
		Vista vista = new Vista();
		vista.setControl(this);
		this.setVista(vista);
		vista.setVisible(true);
	}

	public String showAllDb() throws SQLException {
		String data = "";
		elementos = dbManager.showAll();
		Iterator<Elemento> itr = elementos.values().iterator();
		while (itr.hasNext()) {
			data += (itr.next().toString()) + "\n";
		}
		return data;
	}

	public String ShowAllFm() {
		String data = "";
		System.out.println();
		elementos = fileManager.showAll();
		Iterator<Elemento> itr = elementos.values().iterator();
		while (itr.hasNext()) {
			data += (itr.next().toString()) + "\n";
		}
		return data;
	}

	public void addDbToFile() throws SQLException, IOException {
		elementos = dbManager.showAll();
		fileManager.moveData(elementos);
	}

	public void addFileToDb() {
		elementos = fileManager.showAll();
		dbManager.moveData(elementos);
	}

	public void addElementToDb(Elemento e) {
		dbManager.addElement(e);
	}

	public void addElementToFile(Elemento e) {
		fileManager.addElement(e);
	}

	public void deleteDB() {
		dbManager.removeAll();
	}

	public void deleteFM() {
		fileManager.removeAll();
	}

	public void deleteElementDB(int id) {
		dbManager.removeElement(id);
	}

	public void deleteElementFM(int id) {
		fileManager.removeElement(id);
	}
	public void showEntryDB(int id) {
		dbManager.searchOne(id);
	}

	public void setVista(Vista vista) {
		this.vista = vista;
	}
}
