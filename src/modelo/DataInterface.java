package modelo;

import java.util.HashMap;

public interface DataInterface {
	public HashMap<Integer, Elemento> showAll();

	public void addElement(Elemento e);
}
