<?php
require"init.php";

$response = array ();

// check for missing fields
if (!isset($_POST['selectResource'])) {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "select the Resource";

    // echo JSON response && RETURN back
    echo json_encode($response);
    return;
}

$selectResource=$_POST["selectResource"];  
$sql_query = "SELECT * FROM `deviceLocUsageJournal` WHERE resource like '$selectResource' AND recordedTime < CURRENT_TIMESTAMP ORDER BY seqNo DESC LIMIT 1;";
$result = mysqli_query($con, $sql_query);
$rowcount=mysqli_num_rows($result);
$row=mysqli_fetch_assoc($result);
if($rowcount>0) {
        // successfully return from mySql query
        $response["success"] = 1;
        $response["message"] = "Records found";
        $response["location"] = $row["location"];
        $response["inUse"] = $row["inUse"];
        $response["inOutStatus"] = $row["InOutStatus"];
    
        // echoing JSON response
        echo json_encode($response);
    } else {
        // record not found
        $response["success"] = 0;
        $response["message"] = "No Records found.";
        
        // echoing JSON response
        echo json_encode($response);
    }
mysqli_close($con);
?>

