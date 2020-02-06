<?php

$type = $_POST["type"];
$title = $_POST["title"];
$description = $_POST["description"];

$conn = mysqli_connect("localhost","testuser","1234","Karten");

$injection = mysqli_query($conn, "INSERT INTO Daten (title, type, description, positive_votes, negative_votes) VALUES ('$title', '$type', '$description', '0', '0')");

if($injection == true){
	echo "success";
}else{
	echo "not successfull";
}


mysqli_close($conn);

?>

