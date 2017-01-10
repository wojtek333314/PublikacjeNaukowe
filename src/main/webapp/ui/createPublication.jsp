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
            <form>
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
            </form>

            <form method="POST" id="sendForm" action="uploadFile" enctype="multipart/form-data">
                <label for="title">Title:</label>
                <textarea class="form-control" rows="1" id="title" name="title"></textarea>
                <div class="form-group" id="formContainer">
                </div>
                <input type="hidden" name="userId" value="${userId}">
                <label class="control-label">Select File</label>
                <input id="json" type="hidden" value="{}" name="json"/>
                <input id="input-2" name="file" type="file" class="file" multiple data-show-upload="false"
                       data-show-caption="true">
                <button style="margin-top: 5%;" type="submit" class="btn btn-primary">Add publication</button>
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

    $('#sendForm').submit(function() {
        document.getElementById("json").value = prepareJsonResult();
        return true; // return false to cancel form action
    });

    function prepareJsonResult() {
        var result = {};
        var i = 0;
        while (document.getElementById("textarea" + i) != null) {
            result[document.getElementById("label" + i).innerHTML] = document.getElementById("textarea" + i).value;
            i++;
        }

        alert(JSON.stringify(result));
        return JSON.stringify(result);
    }
</script>
<script type="text/javascript" src="../resources/js/bootstrap.min.js"></script>
</body>
</html>