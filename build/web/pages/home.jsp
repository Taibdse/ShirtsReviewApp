<%-- 
    Document   : home
    Created on : Dec 3, 2019, 3:46:32 PM
    Author     : HOME
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:set var="context" value="${pageContext.request.contextPath}" />
        <link rel="stylesheet" href="${context}/static/css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <title>Home Page</title>

        <style>
            .table *{
                font-size: 0.95em!important;
            }
            </style>
    </head>
    <body style="">
        <jsp:include page="../components/navbar.jsp" />
        <h1 class="text-center my-3">Welcome to Shirt Reviews</h1>
        <p class="text-center">Here is the list of hottest products in Shirts Reviews</p>
        <div class="container-fluid" style="padding-bottom: 300px">
            <c:import url="/WEB-INF/products.xsl" var="xslProductList" />
            <c:set value="${requestScope.products}" var="xmlProducts" />
            <c:if test="${xmlProducts != null}">
                <x:transform xml="${xmlProducts}" xslt="${xslProductList}" />
            </c:if>
            <div>
                <a href="" class="btn btn-danger float-right">Export PDF File</a>
            </div>
        </div>
    </body>


    
</html>
