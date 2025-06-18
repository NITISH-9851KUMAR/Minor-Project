<%--
  Created by IntelliJ IDEA.
  User: nitis
  Date: 17-06-2025
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Create Account</title>
  <link rel="stylesheet" href="css/open-account.css">
</head>
<body>

<div class="container">

  <div class="logo">
    <h2>Nitiya Bank</h2>
    <p>Open Account</p>
  </div>

  <div class="form-section">
    <form action="open-account" method="post">
      <input type="text" name="full_name" placeholder="Full Name" required><br>
      <input type="number" name="balance" placeholder="Enter Balance / Minimum ₹500" required> <br>
      <input type="text" name="mob-num" placeholder="Mobile Number" required> <br>
      <input type="text" name="prn" placeholder="Enter PRN Number" required>
      <input type="password" name="password-value" placeholder="Password / At least 6 digit" required>
      <div class="password">
        <p>Note? <small>Password at least contain one digit, special Symbol and Upper Case</small> </p>
      </div>

      <button type="submit" class="sub-btn">Registered</button>
      <button type="reset" class="res-btn">Reset</button>
    </form>
  </div>
</div>

</body>
</html>
