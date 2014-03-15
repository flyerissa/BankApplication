<%--
  Created by IntelliJ IDEA.
  User: aili
  Date: 15.03.14
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="css" href="/css/auth.css">
    <script type="text/javascript" src="/js/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="/js/authorization.js"></script>

    <title>Remote Office Main</title>
</head>
<body>
<div class="form">
    <form action="/bankLogin" method="post">
        <table>
            <tr>
                <td colspan=2 style="font-weight:bold;">Bank Statistic</td>
            </tr>
            <tr>
                <td>Bank Name:</td>
                <td><input type="text" name="bank_name" id="bank_name"/></td>
                <td id="bankError"></td>
            </tr>
            <tr>
                <td>
                    <a href="/bankinfo">
                        <button>Next</button>
                    </a>
                </td>
            </tr>
        </table>
    </form>
    <
</div>
</body>
</html>