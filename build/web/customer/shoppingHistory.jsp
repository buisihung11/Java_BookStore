<%-- 
    Document   : shoppingHistory
    Created on : Nov 26, 2019, 7:36:06 PM
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
        <link rel="stylesheet" href="${contextPath}/bootstrap-4.3.1-dist/css/bootstrap.css" />
        <link href="../assest/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
        <script src="https://js.stripe.com/v3/"></script>
        <title>Shopping History</title>
    </head>
    <body class="container-fluid">
        <h1 class="text-center font-italic font-weight-bold">Your shopping history: </h1>

        <c:set var="ordersMap" value="${requestScope.SEARCH_RESULTS}" />
        <div class="search-controls">
            <div class="mb-2">
                <form action="${contextPath}/customer/SearchHistory">
                    <input type="text" name="bookTitle" value="${param.bookTitle}" placeholder="Enter name" />
                    <input type="hidden" name="searchBy" value="bookTitle" />
                    <button type="submit">Search By name</button>
                </form>
            </div>
            <div class="mb-2">
                <form id="searchByDateForm" action="${contextPath}/customer/SearchHistory">
                    <input type="text" id="orderDate" name="orderDate" value="${param.orderDate}" >
                    <input type="hidden" name="searchBy" value="date" />
                    <button type="submit">Search By Date</button>
                </form>
            </div>
            <a href="${contextPath}/search.jsp" >Shopping</a>
        </div>
            
        <div class="search-result my-4">
            
          
            <c:if test="${empty ordersMap}">
                <h3>Not founded any orders with that filter</h3>
            </c:if>
            <c:if test="${not empty ordersMap}">
                <c:forEach var="orderEntry" items="${ordersMap}">
                    <c:set var="order" value="${orderEntry.value}" />
                    <div class="border p-2 mb-5 shadow" style="background-color: #DDD">
                        <div class="form-group d-flex justify-content-end flex-column">
                            <p>Order Id: <strong>${orderEntry.key}</strong></p>
                            <p>Order date: <span class="text-muted"><strong>${order.createdDate}</strong></span></p>
                            <p>Total: <strong class="mx-3"><fmt:formatNumber value="${order.orderTotal}" type="currency"/></strong></p>
                            <c:if test="${not empty order.cashedType}"><p>Cash Type: <span class="font-italic font-weight-bold">${order.cashedType}</span></p></c:if>
                            <c:if test="${not empty order.paymentOnlineId}"><p>PaymentID: ${order.paymentOnlineId}</p></c:if>
                        </div>
                        <c:set var="items" value="${order.items}" />
                        <table class="table table-dark text-center">
                            <thead>
                            <th scope="col">No.</th>
                            <th scope="col">Name</th>
                            <th scope="col">Quantity</th>
                            <th scope="col">Amount</th>

                            </thead>
                            <tbody>
                                <c:forEach var="entry" items="${items}" varStatus="counter">
                                    <c:set var="bookItem" value="${entry.value}" />
                                    <tr>
                                        <td>${counter.count}</td>
                                        <td>${bookItem.bookName}</td>
                                        <td>${bookItem.quantity}</td>
                                        <td><fmt:formatNumber value = "${bookItem.amountItem}" type = "currency"/></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:forEach>
            </c:if>

        </div>

        <script src="${contextPath}/bootstrap-4.3.1-dist/js/jquery.js" ></script>                
        <script src="${contextPath}/bootstrap-4.3.1-dist/js/popper.js" ></script>
        <script src="${contextPath}/bootstrap-4.3.1-dist/js/bootstrap.js" ></script>
        <script src="../assest/js/jquery-ui.js" type="text/javascript"></script>

        <script>
            $(function () {
                $("#orderDate").datepicker();
            });
            $('a.confirm-link').on('click', function () {
                return confirm('Are you sure?');
            });

            $("form#searchByDateForm").submit(function(){
                console.log('Check Search Date form')
                var searchDate = $("input#orderDate").val();
                //check invalid format
                
                
                if(searchDate.trim().length == 0){
                    return false;
                }
            })
        </script>
    </body>
</html>
