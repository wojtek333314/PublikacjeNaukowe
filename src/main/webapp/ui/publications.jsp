<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    if (session.getAttribute("isLogged") == null) {
        response.sendRedirect("/login");
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

    <style>
        body {
            overflow-x: hidden;
            width: 100%;
            background-image: url("../resources/img/background_basic.jpg");
        }

        .container {
            margin-top: 5%;
            background-color: #4e4e4e;
            color: #ffffff;
        }

        tr:hover {
            background-color: #626262;
        }
    </style>
    <jsp:include page="navbar.jsp"/>
</head>
<body>

<div class="container ">
    <table class="table table-sm">
        <thead>
        <tr>
            <th>#</th>
            <th class="col-xs-5">Title</th>
            <th class="col-xs-8">Author</th>
            <th class="col-xs-2"></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${publications}" var="publication">
            <tr>
                <th scope="row">${publication.publicationId}</th>
                <td>${publication.title}</td>
                <td>${publication.authorName}</td>
                <td>
                    <button class="btn btn-primary center-block"
                            onclick="location.href='/publication?id=${publication.publicationId}'">See more
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


<script type="text/javascript" src="../resources/js/bootstrap.js"></script>
</body>
</html>