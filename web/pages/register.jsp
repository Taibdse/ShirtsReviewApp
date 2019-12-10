<%-- 
    Document   : register
    Created on : Dec 3, 2019, 3:52:15 PM
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
        <title>Register Page</title>
    </head>
    <body>
        <h1 class="text-center my-3">Register</h1>
        <div class="container">
            <c:if test="${!errors.isEmpty()}">
                <div class="row">
                    <div class="col-sm-5 mx-auto">
                        <c:forEach items="${errors.errors}" var="error" >
                            <div class="alert alert-danger" role="alert">
                                ${error.value}
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            <div class="row">
                <div class="col-sm-5 mx-auto">
                    <div class="card card-body">
                        <form action="${context}/register" method="POST">
                            <div class="form-group">
                                <label>Username</label>
                                <input name="username" 
                                       value="${username}" 
                                       class="form-control" 
                                       placeholder="Enter your username...">
                            </div>
                            <div class="form-group">
                                <label>Password</label>
                                <input type="password" 
                                       name="password" 
                                       value="${password}" 
                                       class="form-control" placeholder="Enter your password">
                            </div>
                            <button type="submit" class="btn btn-success float-right">Register</button>
                        </form>
                        <div class="mt-2">If you had an account, please <a href="${context}/pages/login.jsp">login</a> here</div>

                    </div>
                </div>
            </div>
        </div>
        <script src=""></script>
    </body>
</html>
