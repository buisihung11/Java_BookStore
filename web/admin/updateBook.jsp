<%-- 
    Document   : updateBook
    Created on : Nov 23, 2019, 10:28:19 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../bootstrap-4.3.1-dist/css/bootstrap.css" />
        <title>Update Page</title>
    </head>
    <body>
        <c:set var="contextPath" value="${pageContext.servletContext.contextPath}" />
        <h1 class="text-center">Update book</h1>
        <div class="container-fluid">
            <div class="mx-auto my-3" style="width: 60%">

                <c:set var="err" value="${requestScope.UPDATE_BOOK_ERR}" />
                <c:set var="updateBook" value="${requestScope.UPDATE_BOOK}" />
                <form action="${contextPath}/admin/UpdateBook" method="POST" enctype="multipart/form-data" >
                    <div id="form-container">

                        <div class="form-group">
                            <label for="title">Book Title <strong>${updateBook.bookId}</strong></label>
                            <input type="text" class="form-control" id="title" required minlength="5" maxlength="50" 
                                   name="title" placeholder="Enter title"  value="${updateBook.title}"/>
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.titleErr}
                            </small>
                        </div>

                        <div class="form-group">
                            <label for="author">Author</label>
                            <input type="text" class="form-control" id="author" required minlength="5" maxlength="50" 
                                   name="author" placeholder="Enter author"  value="${updateBook.author}"/>
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.authorErr}
                            </small>
                        </div>   

                        <div class="form-group">
                            <label for="description">Description</label>
                            <textarea type="text" class="form-control" id="description" required minlength="5" maxlength="250" 
                                      name="description" placeholder="Enter description"  value="">${updateBook.description}</textarea>
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.descriptionErr}
                            </small>
                        </div>       
                        <div class="form-group">
                            <label for="quantity">Quantity</label>
                            <input class="form-control-sm" type="number" name="quantity" 
                                   required min="1" step="any" value="${updateBook.quantity}"/>
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.quantityErr}
                            </small>
                        </div>   
                        <div class="form-group">
                            <label for="price">Price</label>
                            <input class="form-control-sm" type="number" name="price" 
                                   required min="1" step="any" value="${updateBook.price}"/>
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.priceErr}
                            </small>
                        </div>  

                        <div class="form-group">
                            <label for="imagePath">Photo</label>
                            <input type="file" class="form-control" id="imagePath" aria-describedby="emailHelp" required 
                                   accept="image/*"
                                   name="imagePath" >
                            <small style="color: red" id="emailHelp" class="form-text">
                                ${err.imagePathErr}
                            </small>
                        </div>
                        <div>IsActive:  
                            <input type="checkbox" name="updateBookStatus" value="ON"  
                                   <c:if test="${updateBook.status}">checked</c:if>/>
                        </div>    
                        <c:set var="categories" value="${applicationScope.CATEGORIES}" />

                        <select name="categoryId" class="" required >
                            <option >Choose Category</option>
                            <c:forEach var="entry" items="${categories}">
                                <option value="${entry.key}" <c:if test="${entry.key eq updateBook.categoryID}" >selected</c:if>>
                                    ${entry.value}
                                </option>
                            </c:forEach>
                        </select>

                        <small style="color: red" id="emailHelp" class="form-text">
                            ${err.categoryIDErr}
                        </small>
                    </div>
                    <div class="text-right my-2">

                        <input type="hidden" name="searchBy" value="${param.searchBy}" />
                        <input type="hidden" name="searchTitle" value="${param.searchTitle}" />
                        <input type="hidden" name="fromPrice" value="${param.fromPrice}" />
                        <input type="hidden" name="toPrice" value="${param.toPrice}" />
                        <input type="hidden" name="searchCategoryId" value="${param.searchCategoryId}" />

                        <input type="hidden" name="bookId" value="${updateBook.bookId}" />
                        <button type="submit" class="btn btn-primary" >Submit</button>
                        <a class="btn btn-outline-warning confirm-link mx-2" href="${contextPath}/search.jsp">Cancel</a>
                    </div>

                </form>
            </div>
        </div>
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
            $('a.confirm-link').on('click', function () {
                return confirm('Are you sure?');
            });
        </script>
    </body>
</html>
