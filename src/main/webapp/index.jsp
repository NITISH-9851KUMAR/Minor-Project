<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link rel="stylesheet" href="css/index.css">
</head>
<body>

<div class="container">

    <div class="Bank_photo">
        <!--    photo Container-->
        <img src="img/bank_logo.png" alt="">
    </div>

    <div class="form-container">
        <div class="logo">
            <h2>Nitiya Bank</h2>
            <p>Login Your Account</p>
        </div>

        <div class="form-section">
            <form action="login-request" method="post">
                <input type="text" name="accNumber" placeholder="Account Number" class="inp"  required><br>
                <input type="password" name="pass" placeholder="Password" class="inp" required><br>

                <button type="submit" class="sign-btn">Sign in</button>
                <a href="forgot-password.jsp">Forgot Your Password?</a>
            </form>
        </div>

        <div class="new-user">
            <p>Don't have an account?</p>
            <a href="open-account.jsp">Open new Account </a>

        </div>

    </div>
</div>

</body>
</html>