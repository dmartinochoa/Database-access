package modelo;

import java.util.HashMap;
import java.util.Iterator;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class HibernateManager implements DataInterface {
	private HashMap<Integer, Elemento> elementos = new HashMap<Integer, Elemento>();
	Session session;

	public HibernateManager() {
		HibernateUtil util = new HibernateUtil();
		session = util.getSessionFactory().openSession();
	}

	@Override
	public void removeAll() {
		session.beginTransaction();
		Query q = session.createQuery("delete from Elemento");
		q.executeUpdate();
		session.getTransaction().commit();
	}

	@Override
	public HashMap<Integer, Elemento> showAll() {
		Query q = session.createQuery("select e from Elemento e");
		List results = q.list();
		Iterator itr = results.iterator();
		while (itr.hasNext()) {
			Elemento e = (Elemento) itr.next();
			elementos.put(e.getId(), e);
		}
		return elementos;
	}

	@Override
	public void addElement(Elemento e) {
		session.beginTransaction();
		session.save(e);
		session.getTransaction().commit();
	}

	@Override
	public void moveData(HashMap<Integer, Elemento> e) {
		Iterator<Elemento> itr = e.values().iterator();
		while (itr.hasNext()) {
			addElement(itr.next());
		}
	}

	@Override
	public void removeElement(int id) {
		session.beginTransaction();
		Query q = session.createQuery("delete from Elemento where id=" + id);
		q.executeUpdate();
		session.getTransaction().commit();
	}

	@Override
	public boolean modifyElement(Elemento e) {
		boolean todoOK = false;
		int id = e.getId();
		String nombre = e.getNombre();
		String descripcion = e.getDescripcion();
		String caracteristicas = e.getCaracteristicas();
		HashMap<Integer, Elemento> elementos = showAll();
		Iterator<Elemento> itr = elementos.values().iterator();
		while (itr.hasNext()) {
			Elemento a = itr.next();
			if (e.getId() == a.getId()) {
				todoOK = true;
				session.beginTransaction();
				Query q = session.createQuery("update Elemento e set e.nombre ='" + nombre + "', e.descripcion='"
						+ descripcion + "', e.caracteristicas='" + caracteristicas + "' where e.id =" + id);
				q.executeUpdate();
				session.getTransaction().commit();
			}
		}
		return todoOK;
	}

	@Override
	public Elemento searchOne(int id) {
		Query q = session.createQuery("select e from Elemento e where id=" + id);
		List results = q.list();
		Iterator itr = results.iterator();
		Elemento e = (Elemento) itr.next();
		return e;
	}

	public void cerrarSesion() {
		session.close();
	}

}
