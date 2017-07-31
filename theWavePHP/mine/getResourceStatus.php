<?php
require "init.php";
$device = intval($_GET['deviceID']);    
$sql_query = "SELECT inUse FROM `deviceLocUsageJournal` WHERE device = $device AND recordedTime < CURRENT_TIMESTAMP ORDER BY seqNo DESC LIMIT 1;";
$result = mysqli_query($con, $sql_query);
if(mysqli_num_rows($result)>0)
{
	$row=mysqli_fetch_assoc($result);
	echo $row["inUse"];
}
else
{
	echo 99;
}
?>
