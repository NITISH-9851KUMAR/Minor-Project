<%--
  Created by IntelliJ IDEA.
  User: nitish
  Date: 18-05-2025
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Account Menubar</title>
    <link rel="stylesheet" href="css/account-menu.css">
</head>
<body>

<div class="container">

    <div class="logo">
        <h2>Nitiya Bank</h2>

        <%-- Know accNumber and name --%>
        <%
            String accNumber = request.getAttribute("accNumber") != null
                    ? request.getAttribute("accNumber").toString()
                    : request.getParameter("accNumber");

            String name = request.getAttribute("name") != null
                    ? request.getAttribute("name").toString()
                    : request.getParameter("name");

            String logoView= request.getAttribute("name") != null
                    ? "Account login Successfully"
                    : "";
        %>
        <p ><%= logoView %></p>
        <p style="color:#555555; ">Welcome   <%= name %></p>
        <p style="color:#555555 ; ">Account Number: <%= accNumber %></p>
    </div>

    <div class="form-section">

        <form action="check-balance" method="post" style="display:inline;">
            <input type="hidden" name="accNumber" value="<%= accNumber %>">
            <button type="submit" class="btn">Check Balance</button>
        </form>


        <form action="deposit-balance.jsp" method="post" style="display:inline;">
            <input type="hidden" name="accNumber" value="<%= accNumber %>">
            <button type="submit" class="btn">Deposit Balance</button>
        </form>

        <form action="transfer-balance-f.jsp" method="post" style="display:inline;">
            <input type="hidden" name="accNumber" value="<%= accNumber %>">
            <button type="submit" class="btn">Transfer Balance</button>
        </form>

        <form action="transaction-details" method="post" style="display:inline;">
            <input type="hidden" name="accNumber" value="<%= accNumber %>">
            <button type="submit" class="btn">Transaction Details</button>
        </form><br>

        <form action="account-details" method="post" style="display:inline;">
            <input type="hidden" name="accNumber" value="<%= accNumber %>">
            <button type="submit" class="btn">Account Details</button>
        </form>

        <form action="change-mobile.jsp" method="post" style="display:inline;">
            <input type="hidden" name="accNumber" value="<%= accNumber %>">
            <button type="submit" class="btn">Change Mobile Number</button>
        </form><br>

        <form action="change-password.jsp" method="post" style="display:inline;">
            <input type="hidden" name="accNumber" value="<%= accNumber %>">
            <button type="submit" class="btn">Change Password</button>
        </form>

    </div>
</div>

</body>
</html>
