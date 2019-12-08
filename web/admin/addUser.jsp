<%-- 
    Document   : addUser
    Created on : Nov 23, 2019, 7:30:07 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../bootstrap-4.3.1-dist/css/bootstrap.css" />
        <title>Add user</title>
    </head>
    <body>
        <c:set var="contextPath" value="${pageContext.servletContext.contextPath}" />
        <div class="container-fluid text-center">
            <div style="width: 60%" class="mx-auto my-2">
                <h1>Add new User</h1>
                <form action="${contextPath}/admin/AddUser" method="POST"  >
                    <div id="form-container">
                        <c:set var="err" value="${requestScope.ADD_USER_ERR}" />
                        <div class="form-group">
                            <label for="username">UserID</label>
                            <input type="text" class="form-control" id="exampleInputPassword1" required minlength="5" maxlength="50" 
                                   name="username" placeholder="Enter UserID"  value="${param.username}"/>
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.usernameErr}
                            </small>
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.duplicateUsernameErr}
                            </small>
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required=""  placeholder="Password">
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.passwordErr}
                            </small>
                        </div>
                        <div class="form-group">
                            <label for="passwordConfirm">Confirm</label>
                            <input type="password" class="form-control" id="passwordCfm" name="passwordConfirm" required=""  placeholder="Password">
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.confirmPasswordErr}
                            </small>
                        </div>
                        <div class="form-group">
                            <label for="fullName">Full Name</label>
                            <input type="text" class="form-control" id="name" aria-describedby="emailHelp" required="" 
                                   name="fullName" value="${param.fullName}" placeholder="Enter Name">
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.fullNameErr}
                            </small>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone</label>
                            <input type="text" class="form-control" id="phone" aria-describedby="emailHelp" required="" 
                                   name="phone" value="${param.phone}"  pattern='^[0|(\+84)][1-9]\d{8}'
                                   placeholder="Enter Phone number"
                                   title="Phone must is valid number and has 10 digit.">
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.phoneErr}
                            </small>
                        </div>
                        <div class="form-group">
                            <label for="address">Address</label>
                            <input type="text" class="form-control" id="phone" aria-describedby="emailHelp" required="" 
                                   placeholder="Enter address"
                                   name="address" value="${param.address}" >
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.addressErr}
                            </small>
                        </div>    
                    </div>
                    <div class="text-right my-2">
                        <input type="hidden" name="action" value="AddUser" />
                        <button type="submit" class="btn btn-primary" >Submit</button>
                        <a class="btn btn-outline-warning confirm-link mx-2" href="${contextPath}/search.jsp">Cancel</a>
                    </div>

                </form>
            </div>
        </div>
        <script src="../bootstrap-4.3.1-dist/js/jquery.js" ></script>                
        <script src="../bootstrap-4.3.1-dist/js/popper.js" ></script>
        <script src="../bootstrap-4.3.1-dist/js/bootstrap.js" ></script>
        <script>
            $('a.confirm-link').on('click', function () {
                return confirm('Are you sure?');
            });
        </script>
    </body>
</html>
