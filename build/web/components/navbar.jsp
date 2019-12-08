<%-- 
    Document   : navbar
    Created on : Dec 3, 2019, 10:12:05 PM
    Author     : HOME
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<c:set var="user" value="${sessionScope.user}" />

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="">ShirtsReview</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#appNavbar" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="appNavbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${context}/home">Home</a>
            </li>

            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="${context}/products">Products </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${context}/logout">Logout</a>
                </li>
            </c:if>
                
            <c:if test="${user == null}">
                <li class="nav-item">
                    <a class="nav-link" href="${context}/login">Login</a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>
