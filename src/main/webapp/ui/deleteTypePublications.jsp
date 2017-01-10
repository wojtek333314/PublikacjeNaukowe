<%@ page import="com.sciencepublications.models.TypeOfPublicationsEntity" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="../resources/js/jquery-3.1.1.min.js"></script>
    <link href="../resources/css/bootstrap.min.css" rel="stylesheet">
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
            <form method="POST" id="sendForm" action="deleteType" enctype="multipart/form-data">
                <div class=\"form-group\">
                    <label for="sel1">Select type of publication:</label>
                    <div class="form-group">
                        <select onchange="onTypeSelect(this)" class="form-control" name="types" id="types">
                            <option value="-1"></option>
                            <%
                                for (TypeOfPublicationsEntity typeOfPublicationsEntity : ((ArrayList<TypeOfPublicationsEntity>) request.getAttribute("typesList"))) {
                                    out.print("<option value=\"" + typeOfPublicationsEntity.getIdType() + "\" " + ">"
                                            + typeOfPublicationsEntity.getName() + "</option>");
                                }
                            %>
                        </select>

                    </div>
                </div>
                <button style="margin-top: 5%;" type="submit" class="btn btn-primary">Delete</button>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    createForm();
    function onTypeSelect(s) {
        createForm(s[s.selectedIndex].value);
    }

    function createForm(selectedIndex) {
        if (selectedIndex != undefined) {
            var data = {
                typePublicationId: selectedIndex
            };
            $.ajax({
                type: 'GET',
                url: '/getForm',
                data: data,
                success: function (form) {
                    document.getElementById("formContainer").innerHTML = form;
                }
            });
        }
    }
</script>
<script type="text/javascript" src="../resources/js/bootstrap.min.js"></script>
</body>
</html>