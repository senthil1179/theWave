<?php
require "init.php";
$userName =$_POST["userName"];
$userPass =$_POST["userPass"];
$sql_query = "Select * from user_info where userName like '$userName' and userPass like '$userPass';";
$result = mysqli_query($con, $sql_query);
if(mysqli_num_rows($result)>0)
{
	$row=mysqli_fetch_assoc($result);
	//$name=$row["name"];
	echo "Login Successful....Welcome ".$row["name"];
	
	
}
else
{
	echo "Login Failed....Try again! ";
}






?>