package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private String user;
	private String pwd;
	private String url;
	private String driver;

	public ConfigManager() {
		Properties properties = new Properties();
		InputStream input = null;
		try {
			File miFichero = new File("Files/config/dbInfo.ini");
			if (miFichero.exists()) {
				input = new FileInputStream(miFichero);
				properties.load(input);
				this.user = properties.getProperty("USUARIO");
				this.pwd = properties.getProperty("PWD");
				this.url = properties.getProperty("URL");
				this.driver = properties.getProperty("DRIVER");
			} else
				System.err.println("File not found");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

}
