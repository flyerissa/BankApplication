<%--
  Created by IntelliJ IDEA.
  User: aili
  Date: 15.03.14
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="css" href="/css/auth.css">
    <script type="text/javascript" src="/js/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="/js/authorization.js"></script>
    <title>Menu</title>
</head>
<body>

<div class="form">

    <p><b>Please choose the operation</b></p><br>
    <a href="/balance2">
        <button>Balance</button>
    </a>
    <a href="/jsp/secure/client/withdraw.jsp">
        <button>Withdraw</button>
    </a>
    <a href="/jsp/secure/client/deposit.jsp">
        <button>Deposit</button>
    </a>
    <a href="/jsp/login.jsp">
        <button>Back</button>
    </a>

</div>
</body>
</html>