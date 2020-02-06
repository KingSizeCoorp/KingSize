<?php
$conn = mysqli_connect("localhost", "testuser", "1234", "Karten");
$result = mysqli_query($conn, "SELECT * from Daten");
$json_array = array();
$json_array[] = array ("success" => "1");
while ($row = mysqli_fetch_assoc($result)){
	$json_array[] = $row;
}
print(json_encode($json_array));
mysqli_close($conn);
?>
