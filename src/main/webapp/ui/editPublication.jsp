<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    response.setCharacterEncoding("UTF-8");
    request.setCharacterEncoding("UTF-8");

    if (session.getAttribute("isLogged") == null) {
        response.sendRedirect("/login");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <script src="../resources/js/jquery-3.1.1.min.js"></script>
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="../resources/img/book-bookmark-icon.ico"/>
    <title>${publication.title}</title>
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

        .button_style {
            margin-bottom: 1%;
            width: 15%;
        }

        tr:hover {
            background-color: #626262;
        }

    </style>

    <jsp:include page="navbar.jsp"/>
    <c:set var="publicationId" value="${publication.publicationId}"/>
</head>
<body>
<form method="POST" id="sendForm" action="update${publication.publicationId}" enctype="multipart/form-data">
    <div class="container ">
        <table class="table table-sm">
            <thead>
            <tr>
                <th class="col-xs-1">Author:</th>
                <th class="col-xs-1">${publication.authorName}</th>

            </tr>
            <tr>
                <th class="col-xs-1">Title:</th>
                <th class="col-xs-1">${publication.title}</th>
                <th class="col-xs-1"><input type="text" id="title" name="title" class="form-control"></th>
            </tr>
            </thead>
        </table>
        <div id="publicationContainer"></div>
        <button class="button_style btn btn-success btn-block">Update</button>
        <input id="json" type="hidden" value="{}" name="json"/>
    </div>
</form>


<script type="text/javascript">
    getPublicationDetails();

    $('#sendForm').submit(function() {
        document.getElementById("json").value = prepareJsonResult();
        return true; // return false to cancel form action
    });

    function prepareJsonResult(){
        var result = {};
        var i = 0;
        console.log(i);
        while (document.getElementById("textarea" + i) != null) {
            result[document.getElementById("label" + i).innerHTML] = document.getElementById("textarea" + i).value;
            i++;
        }

        alert(JSON.stringify(result));
        return JSON.stringify(result);
    }

    function getPublicationDetails() {
        var data = {
            id: findGetParameter("id")
        };
        $.ajax({
            type: 'GET',
            url: '/getPublication',
            data: data,
            success: function (form) {
                var array = JSON.parse(form);
                var module = " <table class=\"table table-sm\" <thead>";
                var id = 0;
                for (var key in array) {
                    if (array.hasOwnProperty(key)) {
                        module += "<p id='label" + id + "'>" + key + "</p><textarea class='form-control'  id='textarea" + id + "'>" + array[key] + "</textarea>";
                        id++;
                    }
                }
                module += "</thead></table>"
                document.getElementById("publicationContainer").innerHTML = module;
            }
        });
    }

    function findGetParameter(parameterName) {
        var result = null,
                tmp = [];
        location.search
                .substr(1)
                .split("&")
                .forEach(function (item) {
                    tmp = item.split("=");
                    if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);
                });
        return result;
    }
</script>
<script type="text/javascript" src="../resources/js/bootstrap.min.js"></script>
</body>
</html>