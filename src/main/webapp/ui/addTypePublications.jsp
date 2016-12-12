<%--
  Created by IntelliJ IDEA.
  User: Robert
  Date: 2016-12-09
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    if (session.getAttribute("isLogged") == null) {
        response.sendRedirect("/login");
    } else {
        Object userId = session.getAttribute("userId");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="../resources/img/book-bookmark-icon.ico"/>
    <title>Add Type of Publications</title>
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
            <form:form id="form"  method="POST" action="addType" enctype="multipart/form-data" modelAttribute="dane">
                <div class="form-group">
                    <label for="name">Name of Type:</label>
                    <input type="text" class="form-control" id="name" name="name">
                    ${dane.add("vhvn")}
                </div>
                <div id="div">

                    <button id="btn1" style="margin-top: 5%;" type="button" onclick="myFunction()" >Add New Attribute</button>
                </div>

                <button style="margin-top: 5%;" type="submit" class="btn btn-primary">Add Publication Type</button>
            </form:form>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
</script>
<script type="text/javascript" src="../resources/js/bootstrap.min.js"></script>
<c:set var="count" value="0" scope="page" />
<script>
    var counter=0;
    function myFunction() {
        var div_text = document.createElement("div");
        var p = document.createElement("P");
        var text="Set Attribut Name:";
        var t = document.createTextNode(text);
        div_text.appendChild(t);

        var input = document.createElement("INPUT");
        input.type="text";
        input.name = 'item';
        <% System.out.println("kurde");%>
        var test_id = document.createTextNode(input.id);
        p.appendChild(test_id);
        <c:set var="count" value="${count + 1}" scope="page" />
        ${dane.add("item")}
        counter++;

        document.getElementById("div").insertBefore(div_text,document.getElementById("btn1"));
        document.getElementById("div").insertBefore(input,document.getElementById("btn1"));
        document.getElementById("div").insertBefore(p,document.getElementById("btn1"));
    }
</script>
</body>
</html>
