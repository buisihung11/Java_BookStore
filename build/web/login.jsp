<%-- 
    Document   : index
    Created on : Nov 11, 2019, 8:18:17 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="bootstrap-4.3.1-dist/css/bootstrap.css" />
        <title>Login Page</title>
    </head>
    <body>
        <div class="container mx-auto my-5" style="width: 40%">

            <h1>LOGIN PAGE</h1>

            <p style="color: red">${requestScope.LOGIN_ERR}</p>

            <form action="Login" method="POST" class="form-signin">
                <!--<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>-->
                <label for="inputEmail" class="sr-only">Email address</label>
                <input type="text" id="inputEmail" class="form-control" placeholder="Email UserID"  name="username" required="" autofocus="">
                <label for="inputPassword" class="sr-only">Password</label>
                <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required="">
                <input type="hidden" name="action" value="Login" />
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>
        </div>

        <!--        <form action="LoginServlet" method="POST">
                    <input type="text" name="username" />
                    <br/>
                    <input type="password" name="password" />
                    <br/>
                    <input type="submit" value="Login" />
                </form>-->
        <script src="bootstrap-4.3.1-dist/js/jquery.js" ></script>                
        <script src="bootstrap-4.3.1-dist/js/popper.js" ></script>
        <script src="bootstrap-4.3.1-dist/js/bootstrap.js" ></script>
    </body>
</html>
