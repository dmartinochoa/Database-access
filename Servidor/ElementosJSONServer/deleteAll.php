<?php

require 'bbdd.php'; // Incluimos fichero en la que está la coenxión con la BBDD

		$query  = "truncate table elementos";
		$result = $conn->query ( $query );
		
		if (isset ( $result ) && $result) { // Si pasa por este if, la query está está bien y se ha insertado correctamente
			
			$arrMensaje["estado"] = "ok";
			$arrMensaje["mensaje"] = "elemento insertado correctamente";
			
		}else{ // Se ha producido algún error al ejecutar la query
			
			$arrMensaje["estado"] = "error";
			$arrMensaje["mensaje"] = "SE HA PRODUCIDO UN ERROR AL ACCEDER A LA BASE DE DATOS";
			$arrMensaje["error"] = $conn->error;
			$arrMensaje["query"] = $query;		
		}


$mensajeJSON = json_encode($arrMensaje,JSON_PRETTY_PRINT);

//echo "<pre>";  // Descomentar si se quiere ver resultado "bonito" en navegador. Solo para pruebas
echo $mensajeJSON;
//echo "</pre>"; // Descomentar si se quiere ver resultado "bonito" en navegador

$conn->close ();

die();

?>