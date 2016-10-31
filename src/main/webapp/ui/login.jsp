<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="../resources/img/book-bookmark-icon.ico"/>
    <title>Login</title>
    <style>
        body {
            overflow-x: hidden;
            width: 100%;
            background-image: url("../resources/img/background_basic.jpg");
        }
    </style>
    <jsp:include page="navbar.jsp"/>
</head>
<body>

<c:if test="${result==false}">
    <%
        response.sendRedirect("/login");
    %>
</c:if>
<c:if test="${result==true}">
    <%
        session.setAttribute("isLogged", "true");
        session.setAttribute("userId", request.getAttribute("userId"));
        response.sendRedirect("/publications");
    %>
</c:if>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <br>
                <p style="margin-left: 3.25%;" class="panel">Fill login form:</p>
                <form action="/login" method="post" class="form-horizontal">
                    <fieldset class="form-group">
                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="logininput">Login</label>
                            <div class="col-md-4">
                                <input id="logininput" name="logininput" type="text" placeholder="Enter login"
                                       class="form-control input-md" required="">
                            </div>
                        </div>

                        <!-- Password input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="passwordinput">Password</label>
                            <div class="col-md-4">
                                <input id="passwordinput" name="passwordinput" type="password"
                                       placeholder="Enter password"
                                       class="form-control input-md" required="">

                            </div>
                        </div>

                        <!-- Button -->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="loginbutton"></label>
                            <div class="col-md-4">
                                <button type="submit" id="loginbutton" name="loginbutton"
                                        class="btn btn-primary btn-block center-block">Login
                                </button>
                            </div>
                        </div>

                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="../resources/js/bootstrap.min.js"></script>
</body>
</html>