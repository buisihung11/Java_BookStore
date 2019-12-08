<%-- 
    Document   : search
    Created on : Nov 22, 2019, 4:50:16 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${contextPath}/bootstrap-4.3.1-dist/css/bootstrap.css" />
        <title>Search Page</title>
    </head>
    <body class="my-4 mx-3">
        <h1 class="text-center">Search Page</h1>
        <c:set var="currentUser" value="${sessionScope.USER}" />
        <h5 class="font-italic font-weight-bold" style="color: #007bff">${param.ADD_BOOK_SUCCESS}</h5>
        <h5 class="font-italic font-weight-bold" style="color: #007bff">${param.ADD_USER_SUCCESS}</h5>
        <h5 class="font-italic font-weight-bold" style="color: #007bff">${param.UPDATE_BOOK_SUCCESS}</h5>
        <h5 class="font-italic font-weight-bold" style="color: #007bff">${param.DELETE_BOOK_SUCCESS}</h5>
        <h5 class="font-italic font-weight-bold" style="color: #d39e00">${param.CHECKOUT_SUCCESS}</h5>
        <h5 class="font-italic font-weight-bold" style="color: #d39e00">${param.ADD_DISCOUNT_SUCCESS}</h5>

        <h5 style="color: #007bff">${param.ADD_TO_CART_SUCCESS}</h5>

        <c:if test="${empty currentUser}">
            <a href="login.jsp" class="btn btn-primary">Login</a>
        </c:if>

        <c:if test="${not empty currentUser}" >
            <h3 style="color: #138496">Welcome ${currentUser.fullName}</h3>
            <a class="btn btn-secondary mb-3" href="Logout">Log out</a>
        </c:if>
        <div id="search_form">
            <!--BY NAME-->
            <div class="mb-2">
                <form action="${contextPath}/Search">
                    <input type="text" name="searchTitle" value="${param.searchTitle}" placeholder="Enter name" />
                    <input type="hidden" name="searchBy" value="searchByName" />
                    <button class="btn btn-success" type="submit">Search By name</button>
                </form>
            </div>
            <!--RANGE OF MONEY-->
            <div class="mb-2">
                <form action="${contextPath}/Search">
                    <input type="number" min="0" step="1" name="fromPrice" value="${param.fromPrice}" required="" placeholder="From" />
                    <input type="number" min="1" step="1" name="toPrice" value="${param.toPrice}" required="" placeholder="To" />
                    <input type="hidden" name="searchBy" value="searchInRange" />
                    <button class="btn btn-success" type="submit">Search In range</button>
                </form>
            </div>
            <!--CATEGORY-->
            <div>
                <form action="${contextPath}/Search">
                    <c:set var="categories" value="${applicationScope.CATEGORIES}" />

                    <select name="searchCategoryId" class="" required >
                        <option >Choose role</option>
                        <c:forEach var="entry" items="${categories}">
                            <option value="${entry.key}" <c:if test="${entry.key eq param.searchCategoryId}">selected</c:if>>
                                ${entry.value}
                            </option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="searchBy" value="searchByCategory" />
                    <button class="btn btn-success" type="submit">Search By Category</button>
                </form>

            </div>

        </div>
        <!--USER CONTROL-->     
        <c:if test="${not empty currentUser}">
            <c:if test="${currentUser.isAdmin eq true}">
                <div class="my-2">
                    <a class="btn btn-primary" href="admin/addBook.jsp">Add Book</a>
                    <a class="btn btn-secondary mx-2" href="admin/addUser.jsp">Add User</a>
                    <a class="btn btn-warning mx-2" href="admin/addDiscount.jsp">Add Discount</a>
                </div>        
            </c:if>
            <c:if test="${currentUser.isAdmin eq false}">
                <div class="my-2">
                    <a class="btn btn-primary" href="customer/viewCart.jsp">View Cart</a>
                    <a class="btn btn-outline-success" href="customer/shoppingHistory.jsp">View Order's History</a>
                </div>        
            </c:if>
        </c:if>


        <div id="search_results" class="my-3">
            <c:set var="results" value="${requestScope.SEARCH_RESULT}" />

            <c:if test="${empty results and not empty param.searchTitle or not empty param.fromPrice or not empty param.searchCategoryId}">
                <h3>No book has found with that filter</h3>
            </c:if>
            <c:if test="${not empty results}">
                <table class="table table-dark text-left">
                    <thead >
                        <tr>
                            <th scope="col">No.</th>
                            <th scope="col">Cover</th>
                            <th scope="col">Book Title</th>
                            <th scope="col">Author</th>
                            <th scope="col">Category</th>
                            <th scope="col" style="max-width: 200px">Description</th>
                            <th scope="col">Number In Stock</th>
                            <th scope="col">Price</th>
                                <c:if test="${not empty currentUser}">
                                    <c:if test="${currentUser.isAdmin eq true}">
                                    <th>Status</th>
                                    <th>Update</th>
                                    <th>Delete</th>
                                    </c:if>
                                    <%--<c:if test="${currentUser.isAdmin eq false}">--%>
                                <!--<th>Add to cart</th>-->       
                                <%--</c:if>--%>
                                <c:if test="${currentUser.isAdmin eq false}">
                                    <th>Add to cart</th>       
                                    </c:if>
                                </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${results}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td><img class="bookCover" src="/BookStore/assest/images/${book.imagePath}" alt="Book Cover" /></td>
                                <td>${book.title}</td>
                                <td>${book.author}</td>
                                <c:set var="bookCategoryId" value="${book.categoryID}" />
                                <td>${categories[bookCategoryId]}</td>
                                <td class="">
                                    <p  class="text-truncated bookDescription">${book.description}</p>
                                </td>
                                <td>${book.quantity}</td>
                                <td><strong>${book.price}</strong></td>

                                <c:if test="${not empty currentUser}">
                                    <c:if test="${currentUser.isAdmin eq true}">
                                        <td><p class="text-muted">${book.status}</p></td>
                                            <c:url var="updateLink" value="/admin/UpdateBookPage">
                                                <c:param name="bookId" value="${book.bookId}" />
                                                <c:param name="searchBy" value="${param.searchBy}" />
                                                <c:param name="searchTitle" value="${param.searchTitle}" />
                                                <c:param name="fromPrice" value="${param.fromPrice}" />
                                                <c:param name="toPrice" value="${param.toPrice}" />
                                                <c:param name="searchCategoryId" value="${param.searchCategoryId}" />
                                            </c:url>
                                        <td><a class="btn btn-primary btn-sm" href="${updateLink}">Update</a></td>
                                        <c:url var="deleteLink" value="/admin/DeleteBook">
                                            <c:param name="bookId" value="${book.bookId}" />
                                            <c:param name="searchBy" value="${param.searchBy}" />
                                            <c:param name="searchTitle" value="${param.searchTitle}" />
                                            <c:param name="fromPrice" value="${param.fromPrice}" />
                                            <c:param name="toPrice" value="${param.toPrice}" />
                                            <c:param name="searchCategoryId" value="${param.searchCategoryId}" />
                                        </c:url>
                                        <td><a class="btn btn-danger confirm-link btn-sm" href="${deleteLink}">Delete</a></td>
                                    </c:if>
                                    <c:if test="${currentUser.isAdmin eq false}">

                                        <c:url var="addToCartLink" value="/customer/AddToCart">
                                            <c:param name="bookId" value="${book.bookId}" />
                                            <c:param name="price" value="${book.price}" />
                                            <c:param name="bookName" value="${book.title}" />
                                            <c:param name="quantity" value="1" />
                                            <c:param name="searchBy" value="${param.searchBy}" />
                                            <c:param name="searchTitle" value="${param.searchTitle}" />
                                            <c:param name="fromPrice" value="${param.fromPrice}" />
                                            <c:param name="toPrice" value="${param.toPrice}" />
                                            <c:param name="searchCategoryId" value="${param.searchCategoryId}" />
                                        </c:url>

                                        <td><a class="btn btn-primary btn-sm" href="${addToCartLink}">Add</a></td>       
                                    </c:if>
                                </c:if>

                                <!--<td><a class="btn btn-primary btn-sm " href="${addToCartLink}">Add</a></td>-->       
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

        </div>
        <style>
            .bookDescription{
                margin: 0;
                padding: 0;
                max-height: 100px;
                overflow: auto;
            }
            .bookCover{
                width: 60px;
                height: 100px;
            }
            .table{
                font-size: 0.8rem;
            }
        </style>
        <script src="${contextPath}/bootstrap-4.3.1-dist/js/jquery.js" ></script>                
        <script src="${contextPath}/bootstrap-4.3.1-dist/js/popper.js" ></script>
        <script src="${contextPath}/bootstrap-4.3.1-dist/js/bootstrap.js" ></script>
        <script>
            $('a.confirm-link').on('click', function () {
                return confirm('Are you sure?');
            });
        </script>
    </body>
</html>
