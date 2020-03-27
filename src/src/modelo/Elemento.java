package modelo;

public class Elemento {
	private int id;
	private String nombre;
	private String descripcion;
	private String caracteristicas;

	public Elemento(int id, String nombre, String descripcion, String caracteristicas) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.caracteristicas = caracteristicas;
	}

	public Elemento(String nombre, String descripcion, String caracteristicas) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.caracteristicas = caracteristicas;
	}

	@Override
	public String toString() {
		return "Id: " + id + ", Nombre: " + nombre + ", Descripcion: " + descripcion + ", Caracteristicas: "
				+ caracteristicas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
}
