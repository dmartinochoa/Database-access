package main;

import controlador.*;
import vista.*;

import java.util.Scanner;

import java.io.IOException;
import java.sql.*;

public class Main {

	public static void main(String[] args) throws SQLException, IOException {
		Vista vista = new Vista();
		Control control = new Control();
		vista.setC(control);
		control.setV(vista);
		vista.setVisible(true);
	}
}