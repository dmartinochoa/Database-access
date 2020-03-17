package main;

import controlador.*;
import vista.*;

import java.util.Scanner;

import java.io.IOException;
import java.sql.*;

public class Main {

	public static void main(String[] args) throws SQLException, IOException {
		Vista v = new Vista();
		Control c = new Control();
		v.setC(c);
		c.setV(v);
		v.setVisible(true);
		Scanner sc = new Scanner(System.in);
		System.out.println("Type a number to select an option:");
		System.out.println("1) Show all data from db");
		System.out.println("2) Write all data from db to file");
		System.out.println("3) Show all data from file");
		System.out.println("4) Add an entry to db");
		System.out.println("5) Add entry to file");
		System.out.println("6) Add file data to db");
		String option = sc.nextLine();
		switch (option) {
		case "1":
			c.showAllDb();
			break;
		case "2":
			c.writeAllFm();
			break;
		case "3":
			c.ShowAllFm();
			break;
		case "4":
			System.out.println("Introduce Nombre:");
			String nombre = sc.nextLine();
			System.out.println("Introduce descripcion:");
			String descripcion = sc.nextLine();
			System.out.println("Introduce caracteristicas:");
			String caracteristica = sc.nextLine();
			c.addToDb(nombre, descripcion, caracteristica);
			break;
		case "5":
			System.out.println("Introduce Id:");
			int id = Integer.parseInt(sc.nextLine());
			System.out.println("Introduce Nombre:");
			nombre = sc.nextLine();
			System.out.println("Introduce descripcion:");
			descripcion = sc.nextLine();
			System.out.println("Introduce caracteristicas:");
			caracteristica = sc.nextLine();
			c.addToFile(id, nombre, descripcion, caracteristica);
			break;
		case "6":
			c.addFileToDb();
			break;
		default:
			System.out.println("Opcion invalida");
		}
	}
}