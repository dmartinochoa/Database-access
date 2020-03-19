package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private String USUARIO;
	private String PWD;
	private String URL;
	private String DRIVER;

	public ConfigManager() {
		Properties propiedades = new Properties();
		InputStream entrada = null;
		try {
			File miFichero = new File("config/dbInfo.ini");
			if (miFichero.exists()) {
				entrada = new FileInputStream(miFichero);
				propiedades.load(entrada);
				this.USUARIO = propiedades.getProperty("USUARIO");
				this.PWD = propiedades.getProperty("PWD");
				this.URL = propiedades.getProperty("URL");
				this.DRIVER = propiedades.getProperty("DRIVER");
			} else
				System.err.println("File not found");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (entrada != null) {
				try {
					entrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getUSUARIO() {
		return USUARIO;
	}

	public void setUSUARIO(String uSUARIO) {
		USUARIO = uSUARIO;
	}

	public String getPWD() {
		return PWD;
	}

	public void setPWD(String pWD) {
		PWD = pWD;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getDRIVER() {
		return DRIVER;
	}

	public void setDRIVER(String dRIVER) {
		DRIVER = dRIVER;
	}
}
