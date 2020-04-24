package controlador;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import modelo.*;
import vista.*;

public class Control {
	private Vista vista;
	private VistaFichero vistaFichero;
	private VistaHibernate vistaHibernate;
	private VistaSql vistaSql;
	private DbManager dbManager;
	private FileManager fileManager;
	private HibernateManager hibernateManager;
	private HashMap<Integer, Elemento> elementos = new HashMap<Integer, Elemento>();

	public Control() {
		Vista vista = new Vista();
		vista.setControl(this);
		this.setVista(vista);
		vista.setVisible(true);
	}

	public void goToDbView() {
		dbManager = new DbManager();
		this.vista.dispose();
		this.vistaSql = new VistaSql();
		this.vistaSql.setControl(this);
		this.vistaSql.setVisible(true);
	}

	public void goToFileView() {
		fileManager = new FileManager();
		this.vista.dispose();
		this.vistaFichero = new VistaFichero();
		this.vistaFichero.setControl(this);
		this.vistaFichero.setVisible(true);
	}

	public void goToHibernateView() {
		hibernateManager = new HibernateManager();
		this.vista.dispose();
		this.vistaHibernate = new VistaHibernate();
		this.vistaHibernate.setControl(this);
		this.vistaHibernate.setVisible(true);
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

	public String ShowAllHibernate() {
		String data = "";
		System.out.println();
		elementos = hibernateManager.showAll();
		Iterator<Elemento> itr = elementos.values().iterator();
		while (itr.hasNext()) {
			data += (itr.next().toString()) + "\n";
		}
		return data;
	}

	public void addDbToFile() throws SQLException, IOException {
		if (fileManager == null) {
			fileManager = new FileManager();
		}
		if (dbManager == null) {
			dbManager = new DbManager();
		}
		elementos = dbManager.showAll();
		fileManager.moveData(elementos);
	}

	public void addFileToDb() {
		if (fileManager == null) {
			fileManager = new FileManager();
		}
		if (dbManager == null) {
			dbManager = new DbManager();
		}
		elementos = fileManager.showAll();
		dbManager.moveData(elementos);
	}

	public void addDbToFileHibernate() throws SQLException, IOException {
		if (fileManager == null) {
			fileManager = new FileManager();
		}
		elementos = hibernateManager.showAll();
		fileManager.moveData(elementos);
	}

	public void addFileToDbHibernate() {
		if (fileManager == null) {
			fileManager = new FileManager();
		}
		elementos = fileManager.showAll();
		hibernateManager.moveData(elementos);
	}

	public void addHibernateToDb() {
		if (dbManager == null) {
			dbManager = new DbManager();
		}
		if (hibernateManager == null) {
			hibernateManager = new HibernateManager();
		}
		elementos = hibernateManager.showAll();
		dbManager.moveData(elementos);
	}

	public void addDbToHibernate() {
		if (dbManager == null) {
			dbManager = new DbManager();
		}
		if (hibernateManager == null) {
			hibernateManager = new HibernateManager();
		}
		elementos = dbManager.showAll();
		hibernateManager.moveData(elementos);
	}

	public void addElementToDb(Elemento e) {
		dbManager.addElement(e);
	}

	public void addElementToFile(Elemento e) {
		fileManager.addElement(e);
	}

	public void addElementoHibernate(Elemento e) {
		hibernateManager.addElement(e);
	}

	public void deleteDB() {
		dbManager.removeAll();
	}

	public void deleteFM() {
		fileManager.removeAll();
	}

	public void deleteHibernate() {
		hibernateManager.removeAll();
	}

	public String deleteElementDB(int id) {
		Elemento e = dbManager.searchOne(id);
		if (e != null) {
			dbManager.removeElement(id);
			return "Element with id: " + id + " has been removed";
		} else {
			return "Index does not exist";
		}
	}

	public String deleteElementFM(int id) {
		Elemento e = fileManager.searchOne(id);
		if (e != null) {
			fileManager.removeElement(id);
			return "Element with id: " + id + " has been removed";
		} else {
			return "Index does not exist";
		}
	}

	public String deleteElementHibernate(int id) {
		Elemento e = hibernateManager.searchOne(id);
		if (e != null) {
			hibernateManager.removeElement(id);
			return "Element with id: " + id + " has been removed";
		} else {
			return "Index does not exist";
		}
	}

	public String showEntryDB(int id) {
		Elemento e = dbManager.searchOne(id);
		if (e != null) {
			return e.toString();
		} else {
			return "Index does not exist";
		}
	}

	public String showEntryFM(int id) {
		Elemento e = fileManager.searchOne(id);
		if (e != null) {
			return e.toString();
		} else {
			return "Index does not exist";
		}
	}

	public String showEntryHibernate(int id) {
		Elemento e = hibernateManager.searchOne(id);
		if (e != null) {
			return e.toString();
		} else {
			return "Index does not exist";
		}
	}

	public String updateEntryDB(Elemento e) {
		if (dbManager.modifyElement(e)) {
			return "Entry modified";
		} else {
			return "Could not modify element";
		}
	}

	public String updateEntryFM(Elemento e) {
		if (fileManager.modifyElement(e)) {
			return "Entry modified";
		} else {
			return "Could not modify element";
		}
	}

	public String updateEntryHibernate(Elemento e) {
		if (hibernateManager.modifyElement(e)) {
			return "Entry modified";
		} else {
			return "Could not modify element";
		}
	}

	public void setVista(Vista vista) {
		this.vista = vista;
	}
}
