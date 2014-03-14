/**
 * Created by User on 11.03.14.
 */
function checkLogin() {
    var bank = $("#bank_name").val();
    var client_name = $("#client_name").val();
    var checkResult = true;

    if (client_name.length < 2) {
        $("#clientError").html("Clients name should be > 2");
        checkResult = false;
    }

    if (bank.length < 2) {
        $("#bankError").html("Bank name should be > 2");
        checkResult = false;
    }

    if (checkResult) {
        alert("sending..");
        return true;
    } else {
        return false;
    }
}


function isNumeric() {
    var input = $(".numbers").val();
    var result = true;
    if (!input.match(/^ {0,1}\d*\.{0,1}\d+$/)) {
        $(".error").html("Only numbers are allowed and should be positive!");
        result = false;
    }
    if (result) {
        alert("Processing..");
        return true;
    }
    else {
        return false;
    }
}
function checkEmail() {
    var checkResult = true;
    var email = $("#mail").val();
    if (!email.match(".+@.+\.[a-zA-Z]{2,3}")) {
        $("#emailError").html("неверный формат e-mail");
        checkResult = false;
    }
    return checkResult;
}

function checkFieldsPresent() {
    var client_name = $("#add_client_name").val();
    var city = $("#city").val();
    var email = $("#mail").val();
    var balance = $("#balance").val();
    var result = true;
    if (client_name === null || city == "" || email == "" || balance == "") {
        result = false;
    }
    if (result) {
        alert("Sending..")
        return true;
    } else {
        alert("All fields should be filled");
        return false;
    }

}

function checkClientName() {
    var client_name = $(".client_name").val();
    var nameArr = name.split(" ");
    var result = true;
    if (client_name.length < 2 || nameArr.length < 2) {
        $("#clientError").html("Please recheck name! It should contain name and surname");
        result = false;
    }
    else {
        for (var i = 0; i < nameArr.length; i++) {
            var n = nameArr[i];
            // проверьте n.length
            if (n.length < 2) {
                $("#clientError").html("Please recheck name or surname! It should contain 2 or more letters");
                result = false;
            }

        }
    }
    if (result) {
        alert("Sending..")
        return true;
    } else {
        alert("Recheck client name");
        return false;
    }
}

function addClient() {

    var client = {


    }
    checkFieldsPresent();
    checkClientName();
    checkEmail();
    var name = $("#add_client_name").val();
    var city = $("#city").val();
    var sex = $("input[name='sex']:checked").val();
    var mail = $("#mail").val();
}






