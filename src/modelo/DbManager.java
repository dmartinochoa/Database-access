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

	@Override
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

	@Override
	public void moveData(HashMap<Integer, Elemento> e) {
		Iterator<Elemento> itr = e.values().iterator();
		while (itr.hasNext()) {
			addElement(itr.next());
		}
	}

	@Override
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

	@Override
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

	@Override
	public void removeAll() {
		String query = "truncate table elementos";
		try {
			PreparedStatement insertPstms = connection.prepareStatement(query);
			insertPstms.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public boolean modifyElement(Elemento e) {
		boolean todoOK = false;
		HashMap<Integer, Elemento> elementos = showAll();
		Iterator<Elemento> itr = elementos.values().iterator();
		while (itr.hasNext()) {
			Elemento a = itr.next();
			if (e.getId() == a.getId()) {
				todoOK = true;
			}
		}
		String query = "update elementos set nombre = ?, descripcion = ?, caracteristica = ?  where id = ?";
		try {
			PreparedStatement insertPstms = connection.prepareStatement(query);
			insertPstms.setString(1, e.getNombre());
			insertPstms.setString(2, e.getDescripcion());
			insertPstms.setString(3, e.getCaracteristicas());
			insertPstms.setInt(4, e.getId());
			insertPstms.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return todoOK;
	}

	@Override
	public Elemento searchOne(int id) {
		String query = "select * from elementos where id = ?";
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
