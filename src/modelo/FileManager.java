package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.HashMap;
import java.util.Iterator;

public class FileManager implements DataInterface {
	File f = new File("Files/file.txt");
	HashMap<Integer, Elemento> elementos = new HashMap<Integer, Elemento>();

	public HashMap<Integer, Elemento> showAll() {
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				int id = Integer.parseInt(line.substring(line.indexOf("Id") + 4, line.indexOf("Nombre") - 2));
				String nombre = line.substring(line.indexOf("Nombre") + 8, line.indexOf("Descripcion") - 2);
				String descripcion = line.substring(line.indexOf("Descripcion") + 13,
						line.indexOf("Caracteristicas") - 2);
				String caracteristicas = line.substring(line.indexOf("Caracteristicas") + 17);
				Elemento e = new Elemento(id, nombre, descripcion, caracteristicas);
				elementos.put(id, e);
				line = br.readLine();
			}
			br.close();

			return elementos;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void moveData(HashMap<Integer, Elemento> e) {
		FileWriter writer;
		try {
			writer = new FileWriter(f);
			BufferedWriter bwriter = new BufferedWriter(writer);
			PrintWriter pwriter = new PrintWriter(bwriter);
			Iterator<Elemento> itr = e.values().iterator();
			while (itr.hasNext()) {
				pwriter.write(itr.next().toString());
				pwriter.write("\n");
			}
			pwriter.close();
			bwriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void addElement(Elemento e) {
		try {
			Files.write(Paths.get("Files/file.txt"), (e.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void removeElement(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAll() {
		PrintWriter pw;
		try {
			pw = new PrintWriter(f);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modifyElement(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void searchOne(int id) {
		// TODO Auto-generated method stub

	}

}
