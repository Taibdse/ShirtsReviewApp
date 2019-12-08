<%-- 
    Document   : login
    Created on : Dec 2, 2019, 12:44:09 AM
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
        <title>Login Page</title>
    </head>
    <body style="background-color: lightblue">
        <h1 class="text-center my-3">Login</h1>
        <div class="container">
            
            <div class="row">
                <c:if test="${registerSuccess}">
                    <div class="col-sm-5 mx-auto">
                        <div class="alert alert-success" role="alert">
                            You have successfully registered the account, please use this account to log in!
                        </div>
                    </div>
                 </c:if>

                <c:if test="${errors != null && !errors.isEmpty()}">
                    <div class="col-sm-8 mx-auto">
                        <div class="alert alert-danger" role="alert">
                            <c:forEach items="${errors.errors}" var="error">
                                <div>${error.value}</div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </div>
            
            <div class="row">
                <div class="col-sm-5 mx-auto">
                    <div class="card card-body">
                        <form action="${context}/login" method="POST">
                            <div class="form-group">
                                <label>Username</label>
                                <input name="username" value="${username}" class="form-control" placeholder="Enter you username..">
                            </div>
                            <div class="form-group">
                                <label>Password</label>
                                <input type="password" name="password" value="${password}" class="form-control" placeholder="Enter your password...">
                            </div>
                            <button type="submit" class="btn btn-success float-right">Log In</button>
                        </form>
                        <div class="mt-2">If you do not have account, please <a href="${context}/pages/register.jsp">register</a> here</div>
                    </div>
                </div>
            </div>
        </div>
        <script src=""></script>
    </body>
</html>
