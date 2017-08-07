<?php
require"init.php";

$response = array ();

// check for missing fields
if (!isset($_POST['userName']) || !isset($_POST['userPass'])) {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Mandatory field(s) - UserName or Password is missing";

    // echo JSON response && RETURN back
    echo json_encode($response);
    return;
}

$name=$_POST["name"];
$nric=$_POST["nric"];
$userName=$_POST["userName"];
$userPass=$_POST["userPass"];
$phoneNumber=$_POST["phoneNumber"];
$email=$_POST["email"];

// inserting the new user into the database
$sql_query="insert into user_info values('$name', '$nric', '$userName', '$userPass', '$phoneNumber', '$email')";
$result = mysqli_query($con, $sql_query);
if(mysqli_num_rows($result)>0) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Registration Successful.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Registration Failed.";
        
        // echoing JSON response
        echo json_encode($response);
    }
mysqli_close($con);
?>