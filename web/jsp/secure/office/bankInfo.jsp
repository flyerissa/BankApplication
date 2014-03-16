<%--
  Created by IntelliJ IDEA.
  User: aili
  Date: 15.03.14
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="css" href="/css/auth.css">
    <script type="text/javascript" src="/js/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="/js/authorization.js"></script>
    <title>BankInfo</title>
</head>
<body>
<div class="form">
    <form>
        <table id="statistic">
            <tr>
                <td colspan=2 style="font-weight:bold;">Bank Statistic</td>
                <td><%=session.getAttribute("bankName")%>
                </td>
            </tr>
            <tr>
                <td>Total amount of clients</td>
                <td><%=session.getAttribute("numberOfClients")%>
                </td>

            </tr>
            <tr>
                <td>Total amount of sum</td>
                <td><%=session.getAttribute("totalSum")%>
                </td>

            </tr>
        </table>
    </form>

    <a href="/jsp/secure/office/searchClients.jsp">
    <button>Search clients</button>
    </a>
    <a href="/addClient">
        <button>Add client</button>
    </a>
    <a href="/jsp/office_login.jsp">
        <button>Back</button>
    </a>

</div>
</body>
</html>