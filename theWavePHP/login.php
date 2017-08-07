<?php
require "init.php";

$response = array ();

// check for missing fields
if (!isset($_POST['userName']) || !isset($_POST['userPass'])) {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echo JSON response && RETURN back
    echo json_encode($response);
    return;
}

$userName =$_POST['userName'];
$userPass =$_POST['userPass'];

// mysql checking if the record is found
$sql_query = "Select * from user_info where userName like '$userName' and userPass like '$userPass';";
$result = mysqli_query($con, $sql_query);
if(mysqli_num_rows($result)>0) {
        // found a record in database
        $response["success"] = 1;
        $response["message"] = "Login Successful.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Login Failed.";
        
        // echoing JSON response
        echo json_encode($response);
    }
mysqli_close($con);
?>