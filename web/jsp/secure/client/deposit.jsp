<%--
  Created by IntelliJ IDEA.
  User: aili
  Date: 15.03.14
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="css" href="/css/auth.css">
    <script type="text/javascript" src="/js/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="/js/authorization.js"></script>
    <title>Deposit</title>
</head>
<body>
<div class="form">
    <form action="/deposit" method="post">
        <table>
            <tr>
                <td colspan=2 style="font-weight:bold;">Enter sum to deposit</td>
            </tr>
            <tr>
                <td>Sum:</td>
                <td><input type="text" name="deposit_sum" id="deposit_sum" class="numbers"/></td>
                <td id="sumError" class="error"></td>

            </tr>
            <tr>
                <td><input class="button" type=submit value="Send"></td>
                <td><a href="/jsp/secure/client/menu.jsp">Back to Menu</a></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>