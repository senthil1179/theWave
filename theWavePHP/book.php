<?php
require "init.php";

$selectResource=$_POST["selectResource"];
$selectDate=$_POST["selectDate"];
$selectSession=$_POST["selectSession"];
$userName=$_POST["userName"];
$sql_query = "Select * from $selectResource where Date like '$selectDate' and Session like '$selectSession';";
$result = mysqli_query($con, $sql_query);
$rowcount=mysqli_num_rows($result);
if($rowcount>0)
{echo "Already booked";
	}
else if($rowcount==0)
{
	
	$sql_query="insert into $selectResource values('$selectDate', '$selectSession', '$userName');";
if(mysqli_query($con, $sql_query))
{
echo "Insertion Successful";
}
else
{
echo "Data insertion error".mysqli_error($con);
}
}
mysqli_close($con);
?>