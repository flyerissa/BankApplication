<%--
  Created by IntelliJ IDEA.
  User: aili
  Date: 15.03.14
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="css" href="/css/auth.css">
    <script type="text/javascript" src="/js/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="/js/authorization.js"></script>

    <title>bank Application Main</title>
</head>
<body>
<div class="form">
    <form action="submit" method="post" target="_blank">
        <table id="search">
            <tr>
                <td style="font-weight:bold;">Find Client</td>
            </tr>
            <tr>
                <td>City:</td>
                <td><input type="text" name="city" id="city"></td>
                <td>Client:</td>
                <td><input type="text" name="client" id="client"></td>
                <!--create objects in JS and fill table with data on pressing button search-->

                <td><input class="button" id="find" type=submit value="Search"></td>
            </tr>
        </table>
        <table id="results">
            <tr>
                <td style="font-weight:bold;">City</td>
                <td style="font-weight:bold;">Client</td>
                <td style="font-weight:bold;">Balance</td>
            </tr>
            <tr>
                <td style="font-weight:bold;">London</td>
                <td><p>Ivan Ivanov</p></td>
                <td>5000</td>
            </tr>
            <tr>
                <td style="font-weight:bold;">Paris</td>
                <td><p>Ivan Petrov</p></td>
                <td>8000</td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>