package modelo;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

public class DbManager implements DataInterface {
	private ConfigManager configManager = new ConfigManager();
	private String user;
	private String pwd;
	private String url;
	private String driver;
	private Connection connection;
	private HashMap<Integer, Elemento> elementos = new HashMap<Integer, Elemento>();

	public DbManager() {
		this.user = configManager.getUser();
		this.pwd = configManager.getPwd();
		this.url = configManager.getUrl();
		this.driver = configManager.getDriver();
		this.connection = conect();
	}

	public Connection conect() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			System.out.println("SQL conexion failed");
			e.printStackTrace();
		}
		return connection;
	}

	public HashMap<Integer, Elemento> showAll() {
		String query = "SELECT * FROM elementos";
		try {
			PreparedStatement pstms = connection.prepareStatement(query);
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

	public void moveData(HashMap<Integer, Elemento> e) {
		Iterator<Elemento> itr = e.values().iterator();
		while (itr.hasNext()) {
			addElement(itr.next());
		}
	}

	public void addElement(Elemento e) {
		String query = "insert into elementos(nombre, descripcion, caracteristica) values(?,?,?);";
		try {
			PreparedStatement insertPstms = connection.prepareStatement(query);
			insertPstms.setString(1, e.getNombre());
			insertPstms.setString(2, e.getDescripcion());
			insertPstms.setString(3, e.getCaracteristicas());
			insertPstms.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void removeElement(int id) {
		String query = "delete from elementos where id = ?";
		try {
			PreparedStatement insertPstms = connection.prepareStatement(query);
			insertPstms.setInt(1, id);
			insertPstms.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void removeAll() {
		String query = "truncate table elementos";
		try {
			PreparedStatement insertPstms = connection.prepareStatement(query);
			insertPstms.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void modifyElement(int id) {
		// TODO Auto-generated method stub

	}

	public Elemento searchOne(int id) {
		String query = "select * from elementos where id = ?)";
		try {
			PreparedStatement insertPstms = connection.prepareStatement(query);
			insertPstms.setInt(1, id);
			ResultSet rs = insertPstms.executeQuery();
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				String descripcion = rs.getString("descripcion");
				String caracteristicas = rs.getString("caracteristica");
				Elemento e = new Elemento(id, nombre, descripcion, caracteristicas);
				return e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
