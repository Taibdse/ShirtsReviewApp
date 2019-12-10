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
            <c:if test="${notfound != true}">
                <c:import url="/WEB-INF/products.xsl" var="xslProductList" />
                <c:set value="${requestScope.products}" var="xmlProducts" />
                <c:if test="${xmlProducts != null}">
                    <x:transform xml="${xmlProducts}" xslt="${xslProductList}" />
                </c:if>
            </c:if>
            <c:if test="${notfound}">
                <h3 class="text-center">No products to suggest for you now!</h3>
            </c:if>
            <c:if test="${!notfound && isAdmin}">
                <button id="btnExportPDF" class="btn btn-danger float-right mt-3">Export PDF</button>
            </c:if>

        </div>
    </body>

    <script>
        var $btnExportPDF = document.getElementById('btnExportPDF');
        
        $btnExportPDF.addEventListener('click', function () {
              window.open('exportPDF', '_blank');
        })
    </script>


</html>
