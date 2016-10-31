<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="../resources/img/book-bookmark-icon.ico"/>
    <title>Register</title>
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
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <br>
                <p style="margin-left: 3.25%;" class="panel">Fill registration form:</p>
                <div class="container">
                    ${passwordTooShort}
                    <br>
                    ${userExist}
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form id="register-form" action="/register" method="post" role="form"
                                  style="display: block;">
                                <label class="col-md-4 control-label" for="username">User name: </label>
                                <div class="form-group">
                                    <input type="text" name="username" id="username" tabindex="1" class="form-control"
                                           placeholder="Username" value="">
                                </div>
                                <label class="col-md-4 control-label" for="password">Password</label>
                                <div class="form-group">
                                    <input type="password" name="password" id="password" tabindex="2"
                                           class="form-control" placeholder="Password">
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="registersubmit" id="register-submit" tabindex="4"
                                                   class="form-control btn btn-primary" value="Register">
                                        </div>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="../resources/js/bootstrap.min.js"></script>
</body>
</html>