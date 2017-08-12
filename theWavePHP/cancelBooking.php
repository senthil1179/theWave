<?php
require "init.php";

// check for missing fields
if (!isset($_POST['selectResource']) || !isset($_POST['selectDate']) 
        || !isset($_POST['selectSession']) || !isset($_POST['userName'])) {
    // required field is missing
    $response["success"] = 1;
    $response["message"] = "Resouce, Date or Session is mssing !";

    // echo JSON response && RETURN back
    echo json_encode($response);
    return;
}

$selectResource=$_POST["selectResource"];
$selectDate=$_POST["selectDate"];
$selectSession=$_POST["selectSession"];
$userName = $_POST["userName"];

// check if any user has booked the resource
$sql_query = "Select * from $selectResource where Date like '$selectDate' and Session like '$selectSession';";
$result = mysqli_query($con, $sql_query);
$rowcount=mysqli_num_rows($result);
if($rowcount == 0) {            // error checking on $result is not done properly anywhere in scripts !
    // successfully inserted into database
    $response["success"] = 3;
    $response["message"] = "No Booking detected!";

    // echoing JSON response
    echo json_encode($response);
    return;
}

// check if this user has booked the resource
$sql_query = "Select * from $selectResource where Date like '$selectDate' and Session like '$selectSession'
                    and userName like '$userName';";
$result = mysqli_query($con, $sql_query);
$rowcount=mysqli_num_rows($result);
if($rowcount == 0) {            // error checking on $result is not done properly anywhere in scripts !
    // successfully inserted into database
    $response["success"] = 2;
    $response["message"] = "Not Authorized to Cancel!";

    // echoing JSON response
    echo json_encode($response);
    return;
}

// now try removing the record
if($rowcount>0)
{
    $sql_query="delete from $selectResource where Date like '$selectDate' and Session like '$selectSession'
                    and userName like '$userName';";
    $result = mysqli_query($con, $sql_query);
    if($result) {
        // successfully cancelled
        $response["success"] = 0;
        $response["message"] = "Cancelled Booking!";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // some Database updation error
        $response["success"] = 4;
        $response["message"] = "Database connectivity error".mysqli_error($con);

        // echoing JSON response
        echo json_encode($response);
    }
}
mysqli_close($con);
?>