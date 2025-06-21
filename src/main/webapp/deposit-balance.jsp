<%--
  Created by IntelliJ IDEA.
  User: nitis
  Date: 19-05-2025
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Deposit Balance</title>
    <link rel="stylesheet" href="css/deposit_bal.css">
    <%
        String accNumber = request.getParameter("accNumber");
    %>

</head>
<body>

<div class="container">

    <div class="logo">
        <h2>Nitiya Bank</h2>
        <p>Deposit Balance</p>
    </div>

    <div class="form-section">
        <form action="deposit-balance" method="post">
            <input type="number" name="dep-bal" placeholder="Enter Balance" class="inp"  required><br>
            <input type="hidden" name="accNumber" placeholder="Password" value="<%=accNumber%>"><br>
            <button type="submit" class="sign-btn">Deposit Balance</button>
        </form>
    </div>

</div>

</body>
</html>
