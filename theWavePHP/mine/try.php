<?php
$servername = "localhost"; 
$dbname = "hackathondb"; 
$username = "root"; 
$password = "root";
 
$device = intval($_GET['deviceID']);    
echo "[DEBUG] Location : " . $device . "\n";
  
//Open a new connection to MySQL server
$mysqli = new mysqli($servername, $username, $password, $dbname);
//Output connection errors
if ($mysqli->connect_error)
{
    die("[ERROR] Connection Failed: " . $mysqli->connect_error);
}
$sql = "SELECT inUse FROM `deviceLocUsageJournal` WHERE device = $device AND recordedTime < CURRENT_TIMESTAMP ORDER BY seqNo DESC LIMIT 1;";
$result = $mysqli->query($sql);
if (!$result = $mysqli->query($sql))
{
    echo "[Error] " . mysqli_error() . "\n";
    exit(); 
}    
?>
