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
	private VistaJson vistaJson;
	private DataInterface i;
	private DbManager dbManager;
	private FileManager fileManager;
	private HibernateManager hibernateManager;
	private HashMap<Integer, Elemento> elementos = new HashMap<Integer, Elemento>();
	private String viewName;

	public Control() {
		Vista vista = new Vista();
		vista.setControl(this);
		this.setVista(vista);
		vista.setVisible(true);
	}

	public void goToView(String view) {
		switch (view) {
		case "db": {
			viewName = "SQL";
			i = new DbManager();
			this.vista.dispose();
			this.vistaSql = new VistaSql();
			this.vistaSql.setControl(this);
			this.vistaSql.setVisible(true);
			break;

		}
		case "file": {
			viewName = "File";
			i = new FileManager();
			this.vista.dispose();
			this.vistaFichero = new VistaFichero();
			this.vistaFichero.setControl(this);
			this.vistaFichero.setVisible(true);
			break;

		}
		case "hibernate": {
			viewName = "Hibernate";
			i = new HibernateManager();
			this.vista.dispose();
			this.vistaHibernate = new VistaHibernate();
			this.vistaHibernate.setControl(this);
			this.vistaHibernate.setVisible(true);
			break;

		}
		case "json": {
			viewName = "Json";
			i = new JsonManager();
			this.vista.dispose();
			this.vistaJson = new VistaJson();
			this.vistaJson.setControl(this);
			this.vistaJson.setVisible(true);
			break;
		}
		}
	}

	public String showAll() throws SQLException {
		String data = "";
		elementos = i.showAll();
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
		i.moveData(elementos);
	}

	public void addFileToDbHibernate() {
		if (fileManager == null) {
			fileManager = new FileManager();
		}
		elementos = fileManager.showAll();
		i.moveData(elementos);
	}

	public void addHibernateToDb() {
		if (dbManager == null) {
			dbManager = new DbManager();
		}
		if (hibernateManager == null) {
			hibernateManager = new HibernateManager();
		}
		elementos = hibernateManager.showAll();
		i.moveData(elementos);
	}

	public void addDbToHibernate() {
		if (dbManager == null) {
			dbManager = new DbManager();
		}
		if (hibernateManager == null) {
			hibernateManager = new HibernateManager();
		}
		elementos = dbManager.showAll();
		i.moveData(elementos);
	}
	public void addDbToJson() {
		if (dbManager == null) {
			dbManager = new DbManager();
		}
		elementos = dbManager.showAll();
		i.moveData(elementos);
	}
	public void addJsonToDb() {
		if (dbManager == null) {
			dbManager = new DbManager();
		}
		elementos = i.showAll();
		dbManager.moveData(elementos);
	}
	public void addJsonToFile() {
		if (fileManager == null) {
			fileManager = new FileManager();
		}
		elementos = i.showAll();
		fileManager.moveData(elementos);
	}
	public void addFileToJson() {
		if (fileManager == null) {
			fileManager = new FileManager();
		}
		elementos = fileManager.showAll();
		i.moveData(elementos);
	}

	public void addElement(Elemento e) {
		i.addElement(e);
	}

	public void deleteAll() {
		i.removeAll();
	}

	public String deleteElement(int id) {
		Elemento e = i.searchOne(id);
		if (e != null) {
			dbManager.removeElement(id);
			return "Element with id: " + id + " has been removed";
		} else {
			return "Index does not exist";
		}
	}

	public String showEntry(int id) {
		Elemento e = i.searchOne(id);
		if (e != null) {
			return e.toString();
		} else {
			return "Index does not exist";
		}
	}

	public String updateEntry(Elemento e) {
		if (i.modifyElement(e)) {
			return "Entry modified";
		} else {
			return "Could not modify element";
		}
	}

	public void setVista(Vista vista) {
		this.vista = vista;
	}

	public String showViewName() {
		return viewName;
	}
}
