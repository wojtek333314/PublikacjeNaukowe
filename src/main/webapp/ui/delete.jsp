<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    if (session.getAttribute("isLogged") == null) {
        response.sendRedirect("/login");
    } else {
        response.sendRedirect("/publications");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Publications</title>
    <link rel="shortcut icon" href="../resources/img/book-bookmark-icon.ico"/>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link href="../resources/css/bootstrap.css" rel="stylesheet">
    <jsp:include page="navbar.jsp"/>
</head>
<body>

<div class="container ">

</div>


<script type="text/javascript" src="../resources/js/bootstrap.js"></script>
</body>
</html>