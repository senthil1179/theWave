<?php
require "init.php";

// check for missing fields
if (!isset($_POST['selectResource']) || !isset($_POST['selectDate']) 
        || !isset($_POST['selectSession'])) {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Resouce, Date or Session is mssing !";

    // echo JSON response && RETURN back
    echo json_encode($response);
    return;
}

$selectResource=$_POST["selectResource"];
$selectDate=$_POST["selectDate"];
$selectSession=$_POST["selectSession"];
$sql_query = "Select * from $selectResource where Date like '$selectDate' and Session like '$selectSession';";
$result = mysqli_query($con, $sql_query);
$rowcount=mysqli_num_rows($result);
if($rowcount == 0) {
    // successfully inserted into database
    $response["success"] = 0;
    $response["message"] = "No Booking detected!";

    // echoing JSON response
    echo json_encode($response);
    return;
}

if($rowcount>0)
{
    $sql_query="delete from $selectResource where Date like '$selectDate' and Session like '$selectSession';";
    $result = mysqli_query($con, $sql_query);
    if($result) {
        // successfully cancelled
        $response["success"] = 1;
        $response["message"] = "Cancelled Booking!";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // some Database updation error
        $response["success"] = 0;
        $response["message"] = "Record delete error".mysqli_error($con);

        // echoing JSON response
        echo json_encode($response);
    }
}
mysqli_close($con);
?>