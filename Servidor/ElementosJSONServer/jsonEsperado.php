<?php

/*  Formato JSON esperado */

$arrEsperado = array();
$arrElementoEsperado = array();

$arrEsperado["peticion"] = "add";
$arrElementoEsperado["id"] = "int";
$arrElementoEsperado["nombre"] = "Lorenzo";
$arrElementoEsperado["descripcion"] = "test";
$arrElementoEsperado["caracteristicas"] = "test";

$arrEsperado["elementoAAnadir"] = $arrElementoEsperado;


/* Funcion para comprobar si el recibido es igual al esperado */

function JSONCorrectoAnnadir($recibido){
	
	$auxCorrecto = false;
	
	if(isset($recibido["peticion"]) && $recibido["peticion"] ="add" && isset($recibido["elementoAAnadir"])){
		
		$auxElemento = $recibido["elementoAAnadir"];
		if(isset($auxElemento["id"]) && isset($auxElemento["nombre"]) && isset($auxElemento["descripcion"]) && isset($auxElemento["caracteristicas"])){
			$auxCorrecto = true;
		}
		
	}
	
	
	return $auxCorrecto;
	
}
