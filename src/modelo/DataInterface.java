package modelo;

import java.io.IOException;
import java.util.HashMap;

public interface DataInterface {
	public HashMap<Integer, Elemento> showAll();

	public void addElement(Elemento e);

	public void moveData(HashMap<Integer, Elemento> e);

	public void removeElement(int id);

	public void removeAll();

	public void modifyElement(int id);

	public void searchOne(int id);

}
