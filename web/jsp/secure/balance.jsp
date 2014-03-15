<%--
  Created by IntelliJ IDEA.
  User: aili
  Date: 15.03.14
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="css" href="../../css/auth.css">
    <script type="text/javascript" src="../../js/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="../../js/authorization.js"></script>
    <title>Balance</title>


</head>
<body>
<div class="form">

    <p><b>Balance: </b></p> <%=request.getSession().getAttribute("balance")%>

    <a href="/jsp/secure/menu.jsp">
        <button>Back</button>
    </a>

</div>

</body>
</html>
