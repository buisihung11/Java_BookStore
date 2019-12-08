<%-- 
    Document   : viewCart
    Created on : Nov 25, 2019, 4:10:23 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../bootstrap-4.3.1-dist/css/bootstrap.css" />
        <title>Cart page</title>
    </head>
    <body class="my-3 mx-2">
        <h1>Your cart:</h1>
        <h5 class="font-italic" style="color: #007bff">${param.UPDATE_CART_SUCCESS}</h5>

        <h5 class="font-italic" style="color: #c82333">${param.UPDATE_CART_ERR}</h5>
        <h5 class="font-italic" style="color: #c82333">${requestScope.ERROR_SUMMARY}</h5>
        <a href="../search.jsp">Add more book</a>
        <c:set var="cart" value="${sessionScope.CART}" />
        <c:if var="isEmptyCart" test="${cart eq null}">
            <h3>You don't have anything in your cart</h3>
        </c:if>
        <c:if test="${not isEmptyCart}">

            <c:set var="items" value="${cart.items}" />
            <c:set var="lackItems" value="${requestScope.LACK_OF_QUANTITY}" />
            <div class="order-container container py-4 border">

                <h4>Items List: </h4>
                <table class="table table-dark text-left">
                    <thead>
                    <th scope="col">No.</th>
                    <th scope="col">Name</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Price</th>
                    <th scope="col">Total</th>
                    <th scope="col">Update</th>
                    <th scope="col">Remove</th>
                    </thead>
                    <tbody>
                        <c:forEach var="entry" items="${items}" varStatus="counter">
                            <c:set var="bookItem" value="${entry.value}" />
                            <tr>
                        <form action="UpdateItemInCart">
                            <td>${counter.count}</td>
                            <td>${bookItem.bookName}</td>
                            <td>
                                <input class="form-control-sm" type="number" name="updateQuantity" 
                                       required min="1" step="any" value="${bookItem.quantity}"/>
                                <c:if test="${lackItems[bookItem.bookId] ne null}">
                                    <p style="color:red" class="font-italic" >Lack of quantity. There are <strong>${lackItems[bookItem.bookId]}</strong> items available</p>
                                </c:if>
                            </td>
                            <td><fmt:formatNumber value = "${bookItem.price}" type = "currency"/> <strong></strong></td>
                            <td><fmt:formatNumber value = "${bookItem.amount}" type = "currency"/></td>
                            <td>
                                <input type="hidden" name="bookId" value="${bookItem.bookId}" />
                                <button type="submit" class="btn btn-primary" >Update</button>
                            </td>
                        </form>

                        <td><a class="btn btn-danger confirm-link" href="RemoveFromCart?bookId=${bookItem.bookId}">Remove</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="text-right">
                    <form action="CheckOutPage" method="POST">
                        <div class="form-group d-flex justify-content-end">
                            <p>Total: <strong class="mx-3"><fmt:formatNumber value = "${cart.total}" type = "currency"/></strong><p> 
                        </div>
                        <form id="checkoutpage-form" action="CheckOutPage" method="POST">

                            <div class="form-group d-flex justify-content-end text-center align-items-center">
                                <span class="font-italic mx-2" style="color:red">${requestScope.DISCOUNT_NOT_VALID}</span>
                                <c:if test="${empty param.discountCode}">
                                    <input style="width: 40%" type="text" class="form-control" name="discountCode" value="${cart.discountCode}" placeholder="Enter discount code you have"/>
                                </c:if>
                                <c:if test="${not empty param.discountCode}">
                                    <input style="width: 40%" type="text" class="form-control" name="discountCode" value="${param.discountCode}" placeholder="Enter discount code you have"/>
                                </c:if>


                            </div>
                            <div class="form-group row">
                                <label for="recipientPhone" class="col-sm-2 col-form-label">Recipient Phone</label>
                                <div class="col-sm-10">
                                    <input type="text" name="recipientPhone" class="form-control" id="recipientPhone"
                                           value="${cart.recipientPhone}"  pattern='^[0|(\+84)][1-9]\d{8}'
                                           placeholder="Enter Phone number"
                                           title="Phone must is valid number and has 10 digit."
                                           required="" />
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="recipientAddress" class="col-sm-2 col-form-label" >Recipient Address</label>
                                <div class="col-sm-10">
                                    <input type="text" name="recipientAddress" class="form-control" id="recipientAddress"
                                           value="${cart.recipientAddress}" 
                                           placeholder="Enter Address" value="" required="" />
                                </div>
                            </div>
                            <button type="submit"  class="btn btn-warning col-sm-2 ml-3">Checkout</button>
                        </form>
                    </form>
                </div>
            </div>



        </c:if>

        <style>
            .bookCover{
                width: 60px;
                height: 100px;
            }
        </style>
        <script src="../bootstrap-4.3.1-dist/js/jquery.js" ></script>                
        <script src="../bootstrap-4.3.1-dist/js/popper.js" ></script>
        <script src="../bootstrap-4.3.1-dist/js/bootstrap.js" ></script>
        <script>
//            $("button#checkount-btn").click(function () {
//                $("form#checkoutpage-form").submit();
//            })

            $('a.confirm-link').on('click', function () {
                return confirm('Are you sure?');
            });
        </script>
    </body>
</html>
