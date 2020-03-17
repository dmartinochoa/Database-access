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
	private HashMap<Integer, Elemento> elementos = new HashMap<Integer, Elemento>();

	private DbManager dBManager;
	private FileManager fileManager;
	private Connection conexion;

	public Control() {
		dBManager = new DbManager();
		fileManager = new FileManager();
		conexion = dBManager.conect();
	}

	public String showAllDb() throws SQLException {
		String data = "";
		elementos = dBManager.showAll();
		Iterator<Elemento> itr = elementos.values().iterator();
		while (itr.hasNext()) {
			data += (itr.next().toString()) + "\n";
		}
		System.out.println(data);
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
		System.out.println(data);
		return data;
	}

	public void addToDb(String nombre, String descripcion, String caracteristicas) {
		Elemento e = new Elemento(nombre, descripcion, caracteristicas);
		dBManager.addElement(e);
	}

	public void addToFile(int id, String nombre, String descripcion, String caracteristicas) {
		Elemento e = new Elemento(id, nombre, descripcion, caracteristicas);
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
}
