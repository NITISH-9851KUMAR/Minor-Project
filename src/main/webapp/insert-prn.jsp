<%--
  Created by IntelliJ IDEA.
  User: nitis
  Date: 17-06-2025
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Insert Prn Number</title>
    <link rel="stylesheet" href="css/InsertPrnNumber.css">
</head>
<body>

<div class="container">

    <div class="logo">
        <h2>Nitiya Bank</h2>
        <p>Insert PRN Number</p>
    </div>

    <div class="form-section">
        <form action="insert-prn" method="post">
            <input type="text" name="prnNumber" placeholder="PRN Number" class="inp" required><br>
            <button type="submit" class="sign-btn">Insert</button>
        </form>
    </div>

</div>


</body>
</html>
