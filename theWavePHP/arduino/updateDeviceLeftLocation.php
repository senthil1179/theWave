
<!DOCTYPE html>
<html>
<body>

<?php
echo "My first PHP script!   ";
?> 

<?php
$servername = "localhost"; 
$dbname = "hackathondb"; 
$username = "root"; 
$password = "root";
 
$device = intval($_GET['deviceID']);    
$location = intval($_GET['locationID']);
$inUse = intval($_GET['inUse']);
echo "[DEBUG] Location : " . $location . "\n";
  
//Open a new connection to MySQL server
$mysqli = new mysqli($servername, $username, $password, $dbname);
//Output connection errors
if ($mysqli->connect_error)
{
    die("[ERROR] Connection Failed: " . $mysqli->connect_error);
}

$resource = 'Label_printer_1';
if ($location == 1) {
    $locationString = "WS01";
    $inUse = 1;
} elseif ($location == 2) {
    $locationString = "WS02";  
    $inUse = 1;
} elseif ($location == 3) {
    $locationString = "DECK01";
    $inUse = 1;
} else {
    $locationString = "DECK01";
    $inUse = 1;
}
    
$sql = "INSERT INTO `deviceLocUsageJournal` (`seqNo`, `resource`, `location`, `inUse`, `recordedTime`, `InOutStatus`) VALUES (NULL, '$resource', '$locationString', '$inUse', CURRENT_TIMESTAMP, 'OUT')";
if (!$result = $mysqli->query($sql))
{
    echo "[Error] " . mysqli_error() . "\n";
    exit(); 
}
    
?>
    
</body>
</html>

//    $sql = "UPDATE one SET count = count + $location WHERE name = 'senthil'";

