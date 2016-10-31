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
<%
    session.removeAttribute("isLogged");
    response.sendRedirect("/login");
%>
<script type="text/javascript" src="../resources/js/bootstrap.min.js"></script>
</body>
</html>