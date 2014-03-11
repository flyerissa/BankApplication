/**
 * Created by User on 11.03.14.
 */
function checkName() {
    var name = $("#name").val();
    var checkResult = true;
    if (name.length < 2) {
        $("#nameError").html("Please recheck name! It should contain 2 and more letters");
        checkResult = false;
    }
    var firstPasswd = $("#pass").val();
    if (firstPasswd.length < 5) {
        $("#passError").html("Too small passwd!");
        checkResult = false;
    }

    if (checkResult) {
        alert("sending..");
        return true;
    } else {
        return false;
    }
}
function checkSum() {
    var sum = $("#sum").val();
    var result = true;
    if (sum < 0) {
        $("#sumError").html("Sum should be greater then zero!");
        result = false;
    }
    if (result) {
        alert("Withdraw..");
        return true;
    }
    else {
        return false;
    }
}

function isNumeric() {
    var input = $("#account").val();
    var result = true;
    if (!input.match(/^-{0,1}\d*\.{0,1}\d+$/)) {
        $("#accountError").html("Only numbers are allowed!");
        result = false;
    }
    if (result) {
        alert("Withdraw..");
        return true;
    }
    else {
        return false;
    }
}



