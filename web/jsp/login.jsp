<%--
  Created by IntelliJ IDEA.
  User: aili
  Date: 15.03.14
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="css" href="../css/auth.css">
    <script type="text/javascript" src="../js/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="../js/authorization.js"></script>

    <title>Bank Application Main</title>
</head>
<body>
<div class="form">
    <form action="/login2" method="post">
        <table>
            <tr>
                <td colspan=2 style="font-weight:bold;">Enter Bank and Client name</td>
            </tr>

            <tr>
                <td>Bank Name:</td>
                <td><input type="text" name="bank_name" id="bank_name"/></td>
                <td id="bankError" class="error"></td>
            </tr>

            <tr>
                <td>Client Name:</td>
                <td><input type="text" name="client_name" id="client_name"/></td>
                <td id="clientError" class="error"></td>
            </tr>

            <tr>
                <td>Account ID:</td>
                <td><input type="text" name="accountID" id="accountID"/></td>
                <td id="accountError" class="error"></td>

            </tr>
            <tr>
                <td>
                    <a href="/secure/client/menu.jsp">
                        <button>Next</button>
                    </a>
                </td>
            </tr>
        </table>


    </form>


</div>
</body>
</html>