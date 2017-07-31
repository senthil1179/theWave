<?php
require"init.php";
$name=$_POST["name"];
$nric=$_POST["nric"];
$userName=$_POST["userName"];
$userPass=$_POST["userPass"];
$phoneNumber=$_POST["phoneNumber"];
$email=$_POST["email"];
//$reg="insert into user_info values('$name', '$nric', '$userName', '$userPass', '$phoneNumber', '$email');";
$sql_query="insert into user_info values('$name', '$nric', '$userName', '$userPass', '$phoneNumber', '$email')";
if(mysqli_query($con, $sql_query))
{
echo "<h3> Registration success</h3>";
}
else
{
echo "Data insertion error".mysqli_error($con);
}


?>