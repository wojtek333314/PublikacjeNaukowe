<%@ page import="com.sciencepublications.models.PublicationEntity" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    boolean isAdmin = false;
    if (session.getAttribute("isLogged") == null) {
        response.sendRedirect("/login");
    } else {
        if (session.getAttribute("isAdmin") != null) {
            isAdmin = true;
        }
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

<c:choose>
    <c:when test="${publications.size()==0}">
        <div class="alert alert-danger" style="padding-bottom: 10%; padding-top: 10%;
              margin-left: 10%; margin-right: 10%; margin-top: 10%;" role="alert">
            <h1 align="center">
                <strong>O kurcze!</strong> Niestety nie ma żadnych publikacji!
            </h1>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container ">
            <table class="table table-sm">
                <thead>
                <tr>
                    <th align="center" style="vertical-align:middle;">ID</th>
                    <th align="center" style="vertical-align:middle;">Title</th>
                    <th align="center" style="vertical-align:middle;">Author</th>
                    <th class="col-xs-2" align="center" style="vertical-align:middle;"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${publications}" var="publication">
                    <tr>
                        <th scope="row" style="vertical-align:middle;">${publication.publicationId}</th>
                        <td align="left" style="vertical-align:middle;">${publication.title}</td>
                        <td align="left" style="vertical-align:middle;">${publication.authorName}</td>
                        <td class="col-xs-2">
                            <button type="button" class="btn btn-primary btn-block"
                                    onclick="location.href='/publication?id=${publication.publicationId}'">See more
                            </button>

                            <%
                                PublicationEntity publication = (PublicationEntity) pageContext.getAttribute("publication");
                                if (isAdmin || publication.getAuthorId() == (int) session.getAttribute("userId")) {
                                    out.print(" <button type=\"button\"  class=\"btn btn-primary btn-block\"\n" +
                                            "  onclick=\"location.href='/edit?id=" + publication.getPublicationId() + "'\">Edit\n" +
                                            " </button>");
                                }

                                if (isAdmin) {
                                    out.print(" <button type=\"button\" class=\"btn btn-danger btn-block\"\n" +
                                            "  onclick=\"location.href='/delete?id=" + publication.getPublicationId() + "'\">Delete\n" +
                                            " </button>");
                                }
                            %>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>


<script type="text/javascript" src="../resources/js/bootstrap.js"></script>
</body>
</html>