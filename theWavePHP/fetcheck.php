<?php
require "init.php";

   
$selectResource=$_POST["selectResource"];
$selectDate=$_POST["selectDate"];
$selectSession=$_POST["selectSession"];
//$Date=$_POST["Date"];
//$Session=$_POST["Session"];
////$phoneNumber=$_POST["phoneNumber"];
//$email=$_POST["email"];
//$reg="insert into user_info values('$name', '$nric', '$userName', '$userPass', '$phoneNumber', '$email');";


$sql_query = "Select * from $selectResource where Date like '$selectDate' and Session like '$selectSession';";
$result = mysqli_query($con, $sql_query);
$rowcount=mysqli_num_rows($result);
//echo $rowcount;
//if(($result) && ($rowcount>0))
if($rowcount>0)
{echo "Already booked. Please choose another session / resource ";
	//while($row=mysqli_fetch_assoc($result))
	//{
		
	//$name=$row["name"];
	//echo "Fetched data....Welcome ".$row["Date"];
	//echo "Date: {$row['Date']}";
	// } 
}

else if($rowcount==0)
{
	echo "Login Failed....Try again! ";
	$sql_query="insert into $selectResource values('$selectDate', '$selectSession');";
if(mysqli_query($con, $sql_query))
{
echo "<h3> Insertion Success</h3>";
}
else
{
echo "Data insertion error".mysqli_error($con);
}
}
mysqli_close($con);
?>

