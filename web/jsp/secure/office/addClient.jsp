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

    <title>Add Client</title>
</head>
<body>
<div class="form">
    <form action="/client" method="get" name="form">
        <table id="statistic">
            <tr>
                <td colspan=2 style="font-weight:bold;">Please fill form to add/update client</td>
                <td><input type="hidden" name="id" value="${client.id}"></td>
            </tr>
            <tr>
                <td>Full Name</td>
                <td><input type="text" name="add_client_name" class="client_name" value="${client.fullName}"/></td>
                <td id="clientError" class="error"></td>

            </tr>
            <tr>
                <td>City</td>
                <td><input type="text" name="city" id="city" value="${client.city}"/></td>

            </tr>
            <tr>
                <td>Sex</td>
                <td><input type="radio" name="sex"${client.gender=="FEMALE"?"checked":""} id="sexF"/>Female</td>
                <td><input type="radio" name="sex"${client.gender=="MALE"?"checked":""} id="sexM"/>Male</td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="text" name="mail" id="mail" value="${client.email}"/></td>
                <td id="emailError" class="error"></td>

            </tr>
            <tr>
                <td>Checking account</td>
                <td><input type="checkbox" name="checking" id="checking"/>Open</td>
                <td>Saving account</td>
                <td><input type="checkbox" name="saving" id="saving"/>Open</td>

            </tr>
            <tr>
                <td>Balance</td>
                <td><input type="text" name="balance" id="balance" value="${client.balance}"/></td>

            </tr>
            <tr>
                <td>Overdraft</td>
                <td><input type="text" name="overdraft" id="overdraft"/></td>
            </tr>
        </table>

        <a href="/addClient">
            <button onclick="checkFieldsPresent(); checkEmail(); checkClientName()">Add client</button>
        </a>
        <a href="/jsp/secure/office/bankInfo.jsp">
            <button>Back</button>
        </a>


    </form>
</div>
</body>
</html>