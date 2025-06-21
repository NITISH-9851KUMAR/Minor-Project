<%--
  Created by IntelliJ IDEA.
  User: nitis
  Date: 19-05-2025
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change Password</title>
    <%
        String accNumber = request.getParameter("accNumber").trim();
    %>
</head>
<body>


<div class="container">

    <div class="logo">
        <h2>Nitiya Bank</h2>
        <p>Change Password</p>
        <link rel="stylesheet" href="css/pass_change.css">
    </div>

    <div class="form-section">
        <form action="change-password" method="post">
            <input type="text" name="newPassword" placeholder="New Password / At least 6 digit" class="inp" required><br>
            <div class="password">
                <p>Note? <small style="color: black;">Password at least contain one digit, special Symbol and Upper Case</small> </p>
            </div>
            <input type="hidden" name="accNumber" value="<%=accNumber%>"><br>
            <button type="submit" class="sign-btn">Change Password</button>
        </form>
    </div>

</div>

</body>
</html>
