<%-- 
    Document   : home
    Created on : Dec 3, 2019, 3:46:32 PM
    Author     : HOME
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:set var="context" value="${pageContext.request.contextPath}" />
        <link rel="stylesheet" href="${context}/static/css/style.css">
        <title>Home Page</title>
    </head>
    <body style="">
        <jsp:include page="../components/navbar.jsp" />
        <h1 class="text-center my-3">Welcome to Shirt Reviews</h1>
        <div class="container" style="padding-bottom: 300px">
            <div class="row mb-4">
                <div class="col-12">
                    <div class="card">
                        <div class="card-header">
                            <h3>Filter shirts</h3>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>Category</label>
                                        <select class="form-control" id="selectCategory">
                                            <option value="0">All</option>
                                            <c:if test="${categories != null && fn:length(categories) > 0}">
                                                <c:forEach var="cate" items="${categories}">
                                                    <option value="${cate.id}">${cate.name}</option>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                    </div>
                                </div>
                                
                                <div class="col-sm-3">
                                    <div class="row">
                                        <div class="col-sm-6 pr-1">
                                            <label>From price</label>
                                            <input min="0" type="number" name="fromPrice" 
                                                   id="fromPrice" class="form-control">
                                        </div>
                                        <div class="col-sm-6 pl-1">
                                            <label>To price</label>
                                            <input min="0" type="number" name="fromPrice" 
                                                   id="toPrice" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="col-12">
                                    <button class="btn btn-success float-right" id="btnSearch">Search</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <c:if test="${products == null || fn:length(products) == 0 }">
                <h3 class="text-center">No Products Found</h3>
            </c:if>
            <div class="card card-body">
                <div class="row mb-5">
                    <div class="col-sm-6 mx-auto">
                        <div class="form--group">
                            <input id="inputQuickSearch" class="form-control" placeholder="Quick Search..." />
                        </div>
                    </div>
                </div>
                <div class="row" id="productList">
                   
<!--                     <div class="col-lg-3 col-md-4 col-sm-6 mb-4 product-item">
                                <div class="card" style="width: 100%">
                                    <img src="${product.image}" class="card-img-top" alt="">
                                    <div class="card-body">
                                        <h5 class="card-title">${product.name}</h5>
                                        <p class="card-text">${product.getPriceFormatted()} vnd</p>
                                        <a href="${context}/products?productId=${product.id}" class="btn btn-primary float-right">Details</a>
                                    </div>
                                </div>
                            </div>-->
                </div>
                 <jsp:include page="../components/loading.jsp" />
            </div>
        </div>
    </body>
    
    <script>
        var CONTEXT_PATH = "${context}";
    </script>  
    
    <script src="${context}/static/js/services/HttpUtils.js"></script>
    <script src="${context}/static/js/services/ValidationUtils.js"></script>
    <script src="${context}/static/js/services/StringUtils.js"></script>
    <script src="${context}/static/js/index.js"></script>
</html>
