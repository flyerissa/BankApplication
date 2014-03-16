<%--
  Created by IntelliJ IDEA.
  User: aili
  Date: 15.03.14
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <form action="/search" method="post">
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
            <thead>
            <tr>
                <th>City</th>
                <th>Client</th>
                <th>Balance</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="client" items="${clients}">
                <tr>
                    <td><c:out value="${client.city}"/></td>
                    <td><a href="/client?clientId=${client.id}"><c:out value="${client.fullName}"/></a></td>
                    <td><c:out value="${client.balance}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </form>

    <a href="/bankinfo">
        <button>Back</button>
    </a>
    <a href="/jsp/secure/office/addClient.jsp">
        <button>Add client</button>
    </a>
</div>
</body>
</html>