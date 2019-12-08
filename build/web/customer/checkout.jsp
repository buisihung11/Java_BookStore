<%-- 
    Document   : checkout
    Created on : Nov 26, 2019, 8:04:07 AM
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
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="../bootstrap-4.3.1-dist/css/bootstrap.css" />
        <!--<script src="https://js.stripe.com/v3/"></script>-->
        <title>Checkout Page</title>
    </head>
    <body>  
        <h1 class="text-center my-3 font-italic font-weight-bold">Checkout Confirm</h1>

        <div class="order-container container py-4 border ">
                <c:set var="cart" value="${sessionScope.CART}" />

                <div class="form-group row">
                    <label for="recipientPhone" class="col-sm-2 col-form-label">Recipient Phone</label>
                    <div class="col-sm-10">
                        <input type="text" name="recipientPhone" class="form-control" id="recipientPhone"
                               value="${cart.recipientPhone}" disabled />
                    </div>
                </div>
                <div class="form-group row">
                    <label for="recipientAddress" class="col-sm-2 col-form-label" >Recipient Address</label>
                    <div class="col-sm-10">
                        <input type="text" name="recipientAddress" value="${cart.recipientAddress}" class="form-control" id="recipientAddress"
                               disabled >
                    </div>
                </div>


                <h4>Items List: </h4>
                <c:set var="items" value="${cart.items}" />
                <table class="table table-dark text-center">
                    <thead>
                    <th scope="col">No.</th>
                    <th scope="col">Name</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Price</th>
                    <th scope="col">Amount</th>

                    </thead>
                    <tbody>
                        <c:forEach var="entry" items="${items}" varStatus="counter">
                            <c:set var="bookItem" value="${entry.value}" />
                            <tr>
                                <td>${counter.count}</td>
                                <td>${bookItem.bookName}</td>
                                <td>${bookItem.quantity}</td>
                                <td><fmt:formatNumber value = "${bookItem.price}" type = "currency"/> <strong></strong></td>
                                <td><fmt:formatNumber value = "${bookItem.amount}" type = "currency"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="text-right">
                    <div class="form-group d-flex justify-content-end flex-column">
                        <p>SubTotal: <strong class="mx-3"><fmt:formatNumber value="${cart.total}" type="currency"/></strong></p>
                        <p>Discount: <strong class="mx-3">${cart.discountValue}</strong></p>
                        <p>Total: <strong class="mx-3"><fmt:formatNumber value="${cart.totalWithDiscount}" type="currency"/></strong></p>
                    </div>
                    <div class="form-group d-flex justify-content-end text-center align-items-center">
                            <a href="CheckOut" class="btn btn-success confirm-link col-sm-2 my-3 mx-3">
                                Normal
                            </a>
                        <form action="CheckoutOnline" method="POST">
                            <script
                                src="https://checkout.stripe.com/checkout.js" class="stripe-button"
                                data-key="pk_test_0Y55XU885X1edW0lDgeHlc9y00Mnid4Lja"
                                data-amount="${cart.totalWithDiscount * 100}"
                                data-name="TestBiz"
                                data-description="Books order"
                                data-image="https://stripe.com/img/documentation/checkout/marketplace.png"
                                data-currency="usd">
                            </script>
                        </form>
                        <!--                        <input type="submit" 
                                                       name="checkoutType"
                                                       value="online"
                                                       class="btn btn-warning confirm-link col-sm-2 ml-3">-->
                    </div>
                    <p class="m-0"><a href="${contextPath}/search.jsp" class=" mb-0 confirm-link col-sm-2 ml-3">Continue Shopping</a></p>

                </div>
        </div>

        <script src="../bootstrap-4.3.1-dist/js/jquery.js" ></script>                
        <script src="../bootstrap-4.3.1-dist/js/popper.js" ></script>
        <script src="../bootstrap-4.3.1-dist/js/bootstrap.js" ></script>
        <script>


//            // Create a Stripe client.
//            var stripe = Stripe('pk_test_0Y55XU885X1edW0lDgeHlc9y00Mnid4Lja');
//
//            // Create an instance of Elements.
//            var elements = stripe.elements();
//
//            // Custom styling can be passed to options when creating an Element.
//            // (Note that this demo uses a wider set of styles than the guide below.)
//            var style = {
//                base: {
//                    color: '#32325d',
//                    fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
//                    fontSmoothing: 'antialiased',
//                    fontSize: '16px',
//                    '::placeholder': {
//                        color: '#aab7c4'
//                    }
//                },
//                invalid: {
//                    color: '#fa755a',
//                    iconColor: '#fa755a'
//                }
//            };
//
//            // Create an instance of the card Element.
//            var card = elements.create('card', {style: style});
//            // Add an instance of the card Element into the `card-element` <div>.
//            card.mount('#card-element');
//
//            var form = document.getElementById('checkout-form');
//            form.addEventListener('submit', function (event) {
//                event.preventDefault();
//
//                stripe.createToken(card).then(function (result) {
//                    if (result.error) {
//                        // Inform the customer that there was an error.
////                        var errorElement = document.getElementById('card-errors');
////                        errorElement.textContent = result.error.message;
//                        console.log(result.error)
//                    } else {
//                        // Send the token to your server.
////                        stripeTokenHandler(result.token);
//                        console.log(result.token)
//                    }
//                });
//            });


            $('a.confirm-link').on('click', function () {
                return confirm('Are you sure?');
            });
        </script>
    </body>
</html>
