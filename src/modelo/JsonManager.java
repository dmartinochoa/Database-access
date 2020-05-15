package modelo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JsonManager implements DataInterface {
	ApiRequests encargadoPeticiones;
	private String SERVER_PATH; // Datos de la conexion

	public JsonManager() {
		encargadoPeticiones = new ApiRequests();
		SERVER_PATH = "http://localhost/ElementosJSONServer/";
	}

	@Override
	public HashMap<Integer, Elemento> showAll() {
		HashMap<Integer, Elemento> auxhm = new HashMap<Integer, Elemento>();
		try {

			System.out.println("---------- Leemos datos de JSON --------------------");

			System.out.println("Lanzamos peticion JSON para jugadores");

			String url = SERVER_PATH + "readElements.php"; // Sacadas de configuracion

			// System.out.println("La url a la que lanzamos la petición es " +
			// url); // Traza para pruebas

			String response = encargadoPeticiones.getRequest(url);

			// System.out.println(response); // Traza para pruebas

			// Parseamos la respuesta y la convertimos en un JSONObject
			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay algún error de parseo (json
										// incorrecto porque hay algún caracter
										// raro, etc.) la respuesta será null
				System.out.println("El json recibido no es correcto. Finaliza la ejecución");
			} else { // El JSON recibido es correcto
				// Sera "ok" si todo ha ido bien o "error" si hay algún problema
				String estado = (String) respuesta.get("estado");
				// Si ok, obtenemos array de jugadores para recorrer y generar hashmap
				if (estado.equals("ok")) {
					JSONArray array = (JSONArray) respuesta.get("elementos");

					if (array.size() > 0) {

						// Declaramos variables
						Elemento nuevoJug;
						int id;
						String nombre;
						String descripcion;
						String caracteristicas;

						for (int i = 0; i < array.size(); i++) {
							JSONObject row = (JSONObject) array.get(i);
							id = Integer.parseInt(row.get("id").toString());
							nombre = row.get("nombre").toString();
							descripcion = row.get("descripcion").toString();
							caracteristicas = row.get("caracteristicas").toString();
							nuevoJug = new Elemento(id, nombre, descripcion, caracteristicas);

							auxhm.put(id, nuevoJug);
						}

						System.out.println("Acceso JSON Remoto - Leidos datos correctamente y generado hashmap");
						System.out.println();

					} else { // El array de jugadores está vacío
						System.out.println("Acceso JSON Remoto - No hay datos que tratar");
						System.out.println();
					}

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido algún error

					System.out.println("Ha ocurrido un error en la busqueda de datos");
					System.out.println("Error: " + (String) respuesta.get("error"));
					System.out.println("Consulta: " + (String) respuesta.get("query"));

				}
			}

		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en la busqueda de datos");

			e.printStackTrace();

		}

		return auxhm;
	}

	@Override
	public void addElement(Elemento e) {

		try {
			JSONObject objElemento = new JSONObject();
			JSONObject objPeticion = new JSONObject();

			objElemento.put("id", e.getId());
			objElemento.put("nombre", e.getNombre());
			objElemento.put("descripcion", e.getDescripcion());
			objElemento.put("caracteristicas", e.getCaracteristicas());

			// Tenemos el jugador como objeto JSON. Lo añadimos a una peticion
			// Lo transformamos a string y llamamos al
			// encargado de peticiones para que lo envie al PHP

			objPeticion.put("peticion", "add");
			objPeticion.put("elementoAAnadir", objElemento);

			String json = objPeticion.toJSONString();

			System.out.println("Lanzamos peticion JSON para almacenar un jugador");

			String url = SERVER_PATH + "writeElement.php";

			System.out.println("La url a la que lanzamos la petición es " + url);
			System.out.println("El json que enviamos es: ");
			System.out.println(json);

			String response = encargadoPeticiones.postRequest(url, json);

			System.out.println("El json que recibimos es: ");

			System.out.println(response); // Traza para pruebas

			// Parseamos la respuesta y la convertimos en un JSONObject

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay algún error de parseo (json
										// incorrecto porque hay algún caracter
										// raro, etc.) la respuesta será null
				System.out.println("El json recibido no es correcto. Finaliza la ejecución");
			} else { // El JSON recibido es correcto

				// Sera "ok" si todo ha ido bien o "error" si hay algún problema
				String estado = (String) respuesta.get("estado");
				if (estado.equals("ok")) {

					System.out.println("Almacenado jugador enviado por JSON Remoto");

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido algún error

					System.out.println("Acceso JSON REMOTO - Error al almacenar los datos");
					System.out.println("Error: " + (String) respuesta.get("error"));
					System.out.println("Consulta: " + (String) respuesta.get("query"));
				}
			}
		} catch (Exception e1) {
			System.out.println(
					"Excepcion desconocida. Traza de error comentada en el método 'annadirJugador' de la clase JSON REMOTO");
			// e.printStackTrace();
			System.out.println("Fin ejecución");
		}

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
		try {
			JSONObject objElemento = new JSONObject();
			JSONObject objPeticion = new JSONObject();

			objElemento.put("id", id);
			objElemento.put("nombre", "this");
			objElemento.put("descripcion", "doesnt");
			objElemento.put("caracteristicas", "matter");
			objPeticion.put("peticion", "add");

			objPeticion.put("elementoAAnadir", objElemento);

			String json = objPeticion.toJSONString();
			String url = SERVER_PATH + "deleteElement.php";
			String response = encargadoPeticiones.postRequest(url, json);
			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeAll() {
		String url = SERVER_PATH + "deleteAll.php";
		try {
			String response = encargadoPeticiones.getRequest(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean modifyElement(Elemento e) {
		boolean todoOk = true;
		try {
			JSONObject objElemento = new JSONObject();
			JSONObject objPeticion = new JSONObject();

			objElemento.put("id", e.getId());
			objElemento.put("nombre", e.getNombre());
			objElemento.put("descripcion", e.getDescripcion());
			objElemento.put("caracteristicas", e.getCaracteristicas());

			// Tenemos el jugador como objeto JSON. Lo añadimos a una peticion
			// Lo transformamos a string y llamamos al
			// encargado de peticiones para que lo envie al PHP

			objPeticion.put("peticion", "add");
			objPeticion.put("elementoAAnadir", objElemento);

			String json = objPeticion.toJSONString();

			System.out.println("Lanzamos peticion JSON para almacenar un jugador");

			String url = SERVER_PATH + "updateElement.php";

			System.out.println("La url a la que lanzamos la petición es " + url);
			System.out.println("El json que enviamos es: ");
			System.out.println(json);

			String response = encargadoPeticiones.postRequest(url, json);

			System.out.println("El json que recibimos es: ");

			System.out.println(response); // Traza para pruebas

			// Parseamos la respuesta y la convertimos en un JSONObject

			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay algún error de parseo (json
										// incorrecto porque hay algún caracter
										// raro, etc.) la respuesta será null
				System.out.println("El json recibido no es correcto. Finaliza la ejecución");
			} else { // El JSON recibido es correcto

				// Sera "ok" si todo ha ido bien o "error" si hay algún problema
				String estado = (String) respuesta.get("estado");
				if (estado.equals("ok")) {

					System.out.println("Almacenado jugador enviado por JSON Remoto");

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido algún error

					System.out.println("Acceso JSON REMOTO - Error al almacenar los datos");
					System.out.println("Error: " + (String) respuesta.get("error"));
					System.out.println("Consulta: " + (String) respuesta.get("query"));

				}
			}
		} catch (Exception e1) {
			System.out.println(
					"Excepcion desconocida. Traza de error comentada en el método 'annadirJugador' de la clase JSON REMOTO");
			// e.printStackTrace();
			System.out.println("Fin ejecución");
		}
		return todoOk;
	}

	@Override
	public Elemento searchOne(int searchId) {
		JSONObject objElemento = new JSONObject();
		JSONObject objPeticion = new JSONObject();

		objElemento.put("id", searchId);
		objElemento.put("nombre", "this");
		objElemento.put("descripcion", "doesnt");
		objElemento.put("caracteristicas", "matter");
		objPeticion.put("peticion", "add");

		objPeticion.put("elementoAAnadir", objElemento);

		String json = objPeticion.toJSONString();
		
		try {
			String url = SERVER_PATH + "searchElement.php"; // Sacadas de configuracion

			// System.out.println("La url a la que lanzamos la petición es " +
			// url); // Traza para pruebas

			String response = encargadoPeticiones.postRequest(url, json);

			// System.out.println(response); // Traza para pruebas

			// Parseamos la respuesta y la convertimos en un JSONObject
			JSONObject respuesta = (JSONObject) JSONValue.parse(response.toString());

			if (respuesta == null) { // Si hay algún error de parseo (json
										// incorrecto porque hay algún caracter
										// raro, etc.) la respuesta será null
				System.out.println("El json recibido no es correcto. Finaliza la ejecución");
			} else { // El JSON recibido es correcto
				// Sera "ok" si todo ha ido bien o "error" si hay algún problema
				String estado = (String) respuesta.get("estado");
				// Si ok, obtenemos array de jugadores para recorrer y generar hashmap
				if (estado.equals("ok")) {
					JSONArray array = (JSONArray) respuesta.get("elementos");

					Elemento e;
					int id;
					String nombre;
					String descripcion;
					String caracteristicas;

					if (array.size() > 0) {
						JSONObject row = (JSONObject) array.get(0);
						id = Integer.parseInt(row.get("id").toString());
						nombre = row.get("nombre").toString();
						descripcion = row.get("descripcion").toString();
						caracteristicas = row.get("caracteristicas").toString();
						e = new Elemento(id, nombre, descripcion, caracteristicas);
						
						return e;
					}

					System.out.println("Acceso JSON Remoto - Leidos datos correctamente y generado hashmap");
					System.out.println();

				} else { // Hemos recibido el json pero en el estado se nos
							// indica que ha habido algún error

					System.out.println("Ha ocurrido un error en la busqueda de datos");
					System.out.println("Error: " + (String) respuesta.get("error"));
					System.out.println("Consulta: " + (String) respuesta.get("query"));

				}
			}

		} catch (Exception e) {
			System.out.println("Ha ocurrido un error en la busqueda de datos");

			e.printStackTrace();

		}

		return null;
	}

}
