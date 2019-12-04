<%-- 
    Document   : loading
    Created on : Dec 4, 2019, 11:01:37 PM
    Author     : HOME
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />


<div class="row">
    <div class="col-sm-3 text-center mx-auto">
        <img id="spinner" src="${context}/static/img/loading.gif" style="width: 50px; height: 50px" 
             class="img-fluid d-none" />
    </div>
</div>