<?php

/* Conexión con la BBDD*/

$servername = "localhost";
$user = "root";
$password = "";
$dbname = "adat_intercambio_json";
$conn  =  new  mysqli($servername,  $user,$password, $dbname);
// Check connection
if ($conn->connect_error) {
	die("Error: " . $conn->connect_error);
}