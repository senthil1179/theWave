<?php
require "init.php";
$device = intval($_POST['selectResource']);    
$sql_query = "SELECT inUse FROM `deviceLocUsageJournal` WHERE resource = $device AND recordedTime < CURRENT_TIMESTAMP ORDER BY seqNo DESC LIMIT 1;";
$result = mysqli_query($con, $sql_query);
//$rowcount=mysqli_num_rows($result);
$rowcount=mysqli_num_rows($result);
$row=mysqli_fetch_assoc($result);
if($rowcount>0)
{echo "status:".$row["inUse"];
	}

else 
{
	echo "Data insertion error".mysqli_error($con);
	echo 99;
}
mysqli_close($con);
?>
