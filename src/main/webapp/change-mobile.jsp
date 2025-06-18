<%--
  Created by IntelliJ IDEA.
  User: nitish
  Date: 19-05-2025
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Change Mobile Number</title>
  <link rel="stylesheet" href="css/deposit-bal.css">
  <%
    String accNumber = request.getParameter("accNumber");
  %>
</head>
<body>


<div class="container">

  <div class="logo">
    <h2>Nitiya Bank</h2>
    <p>Change Mobile Number</p>
  </div>

  <div class="form-section">
    <form action="change-mob-number" method="post">
      <input type="number" name="mobNumber" placeholder="New Mobile Number" class="inp"  required><br>
      <input type="hidden" name="accNumber" value="<%=accNumber%>"><br>
      <button type="submit" class="sign-btn">Change Number</button>
    </form>
  </div>

</div>

</body>
</html>
