<?php
require "init.php";

$selectResource=$_POST["selectResource"];
$selectDate=$_POST["selectDate"];
$selectSession=$_POST["selectSession"];

$sql_query = "Select * from $selectResource where Date like '$selectDate' and Session like '$selectSession';";
$result = mysqli_query($con, $sql_query);
$rowcount=mysqli_num_rows($result);
if($rowcount==0)
{echo "No Booking detected!";
	}
else if($rowcount>0)
{
	
	$sql_query="delete from $selectResource where Date like '$selectDate' and Session like '$selectSession';";
if(mysqli_query($con, $sql_query))
{
echo "Cancelled Booking!";
}
else
{
echo "Record delete error".mysqli_error($con);
}
}
mysqli_close($con);
?>