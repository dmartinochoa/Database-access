package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class DbManager implements DataInterface {
	private ConfigManager configManager = new ConfigManager();
	private String USUARIO = configManager.getUSUARIO();
	private String PWD = configManager.getPWD();
	private String URL = configManager.getURL();
	private String DRIVER = configManager.getDRIVER();
	private Connection conexion;

	public Connection conect() {
		try {
			Class.forName(DRIVER);
			conexion = DriverManager.getConnection(URL, USUARIO, PWD);
		} catch (Exception e) {
			System.out.println("SQL conexion failed");
			e.printStackTrace();
		}
		return conexion;
	}

	public HashMap<Integer, Elemento> showAll() {
		String query = "SELECT * FROM elementos";
		HashMap<Integer, Elemento> elementos = new HashMap<Integer, Elemento>();

		try {
			PreparedStatement pstms = conexion.prepareStatement(query);
			ResultSet rs = pstms.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String descripcion = rs.getString("descripcion");
				String caracteristicas = rs.getString("caracteristica");
				Elemento e = new Elemento(id, nombre, descripcion, caracteristicas);
				elementos.put(id, e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return elementos;
	}

	public void addElement(Elemento e) {
		String query = "insert into elementos(nombre, descripcion, caracteristica) values(?,?,?);";
		try {
			PreparedStatement insertPstms = conexion.prepareStatement(query);
			insertPstms.setString(1, e.getNombre());
			insertPstms.setString(2, e.getDescripcion());
			insertPstms.setString(3, e.getCaracteristicas());
			insertPstms.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
