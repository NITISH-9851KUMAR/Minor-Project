<%--
  Created by IntelliJ IDEA.
  User: nitis
  Date: 19-05-2025
  Time: 21:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transfer Balance</title>
    <link rel="stylesheet" href="transfer-bal.css">
    <%
        String accNumber = request.getParameter("accNumber");
    %>
</head>
<body>


<div class="container">

    <div class="logo">
        <h2>Nitiya Bank</h2>
        <p style="color: green;">Receiver Details</p>
    </div>

    <div class="form-section">
        <form action="TransferBalance1Servlets" method="post">
            <input type="text" name="recAcNumber" placeholder="Ac Number" class="inp"  required>
            <input type="text" name="ifsc" placeholder="IFSC CODE / Upper Case" class="inp"  required>
<%--            <input type="number" name="bal" placeholder="Balance" class="inp"  required><br>--%>
            <input type="hidden" name="senAcNumber" value="<%=accNumber%>"><br>
            <button type="submit" class="sign-btn">Fetch Details</button>
        </form>
    </div>

</div>

</body>
</html>
