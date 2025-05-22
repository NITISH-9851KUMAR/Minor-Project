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
        String recName = request.getAttribute("recName").toString();
        String recAcNumber = request.getAttribute("recAccNumber").toString();
        String sendAcNumber = request.getAttribute("senAcNumber").toString();
    %>
</head>
<body>


<div class="container">

    <div class="logo">
        <h2>Nitiya Bank</h2>
        <p style="color: green;">Receiver Details</p>
        <p style="color: black;">Ac Holder Name: <big><%=recName%></big></p>
        <p style="color: black;">Ac/No: <big><%=recAcNumber%></big> </p>
    </div>

    <div class="form-section">
     <form action="TransferBalance2Servlets" method="post">
           <input type="number" name="bal" placeholder="Balance" class="inp"  required>
           <input type="hidden" name="senAcNumber" value="<%=sendAcNumber%>"><br>
           <input type="hidden" name="recAcNumber" value="<%=recAcNumber%>"><br>
           <button type="submit" class="sign-btn">Transfer Balance</button>
       </form>
  </div>

</div>

</body>
</html>
