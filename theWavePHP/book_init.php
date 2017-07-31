<?php
$db_name="hackathondb";
$user_name="root";
$user_pass="root";
$server_name="localhost";

$con=mysqli_connect($server_name, $user_name, $user_pass, $db_name);
if(!$con)
{
	echo "Connection error...".mysqli_connect_error();
}
else
{
//echo "<h3>Connection successful</h3>";
}
?>