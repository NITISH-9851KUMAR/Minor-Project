<%--
  Created by IntelliJ IDEA.
  User: nitis
  Date: 17-06-2025
  Time: 21:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot Password</title>
    <link rel="stylesheet" href="css/forgot-pass.css"></head>
<body>

<div class="container">

    <div class="logo">
        <h2>Nitiya Bank</h2>
        <p>Password Recovery</p>
    </div>

    <div class="form-section">
        <form action="password-recover" method="post">
            <input type="text" name="acc-num" placeholder="Account Number" required> <br>
            <input type="text" name="prn-num" placeholder="Enter PRN Number" required><br>
            <input type="text" name="mob-num" placeholder="Registered Mobile Number" required>

            <button type="submit" class="sub-btn">Know Password</button>
        </form>
    </div>
</div>

</body>
</html>
