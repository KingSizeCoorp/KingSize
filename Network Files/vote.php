<?php
$key = $_POST["key"];
$method = $_POST["method"];

$conn = mysqli_connect("localhost","testuser","1234","Karten");
echo "moooin ";
if($method == "up"){
	echo "loool";
	$q = "UPDATE Daten set positive_votes = positive_votes + '1' WHERE id = '$key'";
	$inject = mysqli_query($conn, $q);
	echo "loool";
}
if($method == "down"){
	$q = "UPDATE Daten set negative_votes = negative_votes + '1' WHERE id = '$key'";
	$inject = mysqli_query($conn, $q);
}

if($inject == true){
        echo "success";
}else{
        echo "not successfull";
}


mysqli_close($conn);

?>
