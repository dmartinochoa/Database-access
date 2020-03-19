package controlador;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import modelo.*;
import vista.*;

public class Control {
	private Vista v;
	private DbManager dBManager;
	private FileManager fileManager;
	private HashMap<Integer, Elemento> elementos = new HashMap<Integer, Elemento>();

	public Control() {
		dBManager = new DbManager();
		fileManager = new FileManager();
	}

	public String showAllDb() throws SQLException {
		String data = "";
		elementos = dBManager.showAll();
		Iterator<Elemento> itr = elementos.values().iterator();
		while (itr.hasNext()) {
			data += (itr.next().toString()) + "\n";
		}
		return data;
	}

	public void writeAllFm() throws SQLException, IOException {
		elementos = dBManager.showAll();
		fileManager.writeAll(elementos);
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

	public void addToDb(Elemento e) {
		dBManager.addElement(e);
	}

	public void addToFile(Elemento e) {
		fileManager.addElement(e);
	}

	public void addFileToDb() {
		elementos = fileManager.showAll();
		Iterator<Elemento> itr = elementos.values().iterator();
		while (itr.hasNext()) {
			dBManager.addElement(itr.next());
		}
	}

	public Vista getV() {
		return v;
	}

	public void setV(Vista v) {
		this.v = v;
	}

	public Elemento createDbElement(String nombre, String descripcion, String caracteristicas) {
		Elemento e = new Elemento(nombre, descripcion, caracteristicas);
		return e;
	}

	public Elemento createFileElement(int id, String nombre, String descripcion, String caracteristicas) {
		Elemento e = new Elemento(id, nombre, descripcion, caracteristicas);
		return e;
	}
}
