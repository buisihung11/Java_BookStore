<%-- 
    Document   : addDiscount
    Created on : Nov 28, 2019, 7:27:39 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../bootstrap-4.3.1-dist/css/bootstrap.css" />
        <title>Add Discount Page</title>
    </head>
    <body class="container-fluid">
        <h1 class="text-center font-italic font-weight-bold">Add Discount</h1>

        <div style="width: 60%" class="container mx-auto my-3">
            <form action="AddDiscount">
                <div class="form-group">
                    <label for="discountCode">Discount Code</label>
                    <input type="text" class="form-control" id="bookId" required minlength="5" maxlength="50" 
                           name="discountCode" placeholder="Enter Discount Code"  value="${param.discountCode}"/>
                    <small style="color: red" id="emailHelp" class="form-text">
                        ${requestScope.DISCOUNT_CODE_DUPLICATE_ERR}
                    </small>
                    <small style="color: red" id="emailHelp" class="form-text">
                        ${requestScope.DISCOUNT_CODE_ERR}
                    </small>
                </div>
                <div class="form-group">
                    <label for="discountValue">Discount value</label>
                    <select name="discountValue">
                        <option value="">Choose value</option>
                        <fmt:parseNumber var = "discountValueParam" type = "number" value = "${param.discountValue}" />
                        <c:forEach begin="10" end="100" step="10" var="val">
                            <option value="${val}"
                                    <c:if test="${discountValueParam eq val}">selected</c:if>
                                        >
                                    ${val}%
                            </option>
                        </c:forEach>
                    </select>
                    <small style="color: red" id="emailHelp" class="form-text">
                        ${requestScope.DISCOUNT_VALUE_ERR}
                    </small>
                </div>
                <button class="btn btn-primary" type="submit">Submit</button>
                <a href="${contextPath}/search.jsp" class="confirm-link">Return to HomePage</a>
            </form>
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
