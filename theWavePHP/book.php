<?php
require"init.php";

$response = array ();

// check for missing fields
if (!isset($_POST['selectResource']) || !isset($_POST['selectDate']) 
         || !isset($_POST['userName'])  || !isset($_POST['selectSession'])) {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Resouce, Date or Session (or User) is mssing !";

    // echo JSON response && RETURN back
    echo json_encode($response);
    return;
}

$selectResource=$_POST["selectResource"];
$selectDate=$_POST["selectDate"];
$selectSession=$_POST["selectSession"];
$userName=$_POST["userName"];
$sql_query = "Select * from $selectResource where Date like '$selectDate' and Session like '$selectSession';";
$result = mysqli_query($con, $sql_query);
$rowcount=mysqli_num_rows($result);
if($rowcount>0) {
    // successfully inserted into database
    $response["success"] = 0;
    $response["message"] = "Already booked";

    // echoing JSON response
    echo json_encode($response);
    return;
}

if($rowcount==0) {
    $sql_query="insert into $selectResource values('$selectDate', '$selectSession', '$userName');";
    $result = mysqli_query($con, $sql_query);
    if($result) {
        // successfully booked
        $response["success"] = 1;
        $response["message"] = "Successfully booked.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // some Database insertion error
        $response["success"] = 0;
        $response["message"] = "Data insertion error".mysqli_error($con);

        // echoing JSON response
        echo json_encode($response);
    }
}
mysqli_close($con);
?>
