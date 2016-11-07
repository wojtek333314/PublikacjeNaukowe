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
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="../resources/img/book-bookmark-icon.ico"/>
    <title>Create publication</title>
    <style>
        body {
            overflow-x: hidden;
            width: 100%;
            background-image: url("../resources/img/background_basic.jpg");
        }

        form {
            margin: 5%;
        }

        tr:hover {
            background-color: #626262;
        }

    </style>

    <jsp:include page="navbar.jsp"/>
</head>
<body>

<div class="row">
    <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-login">
            <form method="POST" action="uploadFile" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="title">Title:</label>
                    <input value="${publication.title}" type="text" class="form-control" id="title" name="title">
                </div>
                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" rows="5" id="description" name="description">${publication.description}</textarea>
                </div>
                <input type="hidden" name="userId" value="${userId}">
                <label class="control-label">Select File</label>
                <input id="input-2" name="file" type="file" class="file" multiple data-show-upload="false"
                       data-show-caption="true">
                <button style="margin-top: 5%;" type="submit" class="btn btn-primary">Add publication</button>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="../resources/js/bootstrap.min.js"></script>
</body>
</html>