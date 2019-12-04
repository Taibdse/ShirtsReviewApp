<%-- 
    Document   : product
    Created on : Dec 3, 2019, 9:46:46 PM
    Author     : HOME
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <c:set var="context" value="${pageContext.request.contextPath}" />
        <link rel="stylesheet" href="${context}/static/css/style.css">
        <title>Product Details Page</title>
    </head>
    <body>
        <h1 class="text-center my-3">Product Details</h1>
        <div class="container">
            <c:if test="${product == null}">
                <h3 class="text-center mt-3">Can not find this product</h3>
            </c:if>
            <c:if test="${product != null}">
                <div class="row">
                    <div class="col-sm-8 mx-auto">
                        <div class="card card-body">
                            <div class="card" style="width: 100%">
                                <img src="${product.image}" class="card-img-top" alt="">
                                <div class="card-body">
                                    <h5 class="card-title">${product.name}</h5>
                                    <p class="card-text">Price: ${product.getPriceFormatted()} vnd</p>
                                    <p class="card-text">Description: ${product.description}</p>
                                    <p class="card-text">Sizes: ${product.sizes}</p>
                                    <p class="card-text">Colors: ${product.colors}</p>
                                    <c:if test="${category != null}">
                                        <p class="card-text">Category: ${category.name}</p>
                                    </c:if>
                                    <a href="${product.link}" target="_blank" class="btn btn-primary float-right">Go to buy now</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
        <script src=""></script>
    </body>
</html>
