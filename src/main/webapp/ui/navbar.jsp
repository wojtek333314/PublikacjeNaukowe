<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    boolean isLogged = false;
    boolean isAdmin = false;

    if (session.getAttribute("isLogged") != null) {
        isLogged = true;
        session.setMaxInactiveInterval(180);
        if (session.getAttribute("isAdmin") != null) {
            isAdmin = true;
        }
    }
%>
<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <%
            if (isLogged) {
                out.print("<div class=\"navbar-header\">\n" +
                        "            <a class=\"navbar-brand\" href=\"/publications\">Science publications</a>\n" +
                        "        </div>");

                out.print("<ul class=\"nav navbar-nav\">\n" +
                        "<li><a href=\"/publications\">Publications list</a></li>\n" +
                        ( "<li><a href=\"/create\">Add publication</a></li>\n" )
                        +
                        (isAdmin ? "<li><a href=\"/addTypePublications\">Add type of publications</a></li>\n" : "")
                        +
                        (isAdmin ? "<li><a href=\"/deleteTypePublications\">Delete type of publications</a></li>\n" : "") +
                        "        </ul>");
            } else {
                out.print("<div class=\"navbar-header\">\n" +
                        "            <a class=\"navbar-brand\" href=\"/\">Science publications</a>\n" +
                        "        </div>");
            }
        %>

        <ul class="nav navbar-nav navbar-right">
            <% if (!isLogged) {
                out.print(" <li><a href=\"/register\"><span class=\"glyphicon glyphicon-user\"></span> Sign Up</a></li>");
            }%>
            <% if (isLogged) {
                out.print("<li><a href=\"/logout\"><span class=\"glyphicon glyphicon-log-in\"></span> Logout</a></li>");
            } else {
                out.print("<li><a href=\"/login\"><span class=\"glyphicon glyphicon-log-in\"></span> Login</a></li>");
            }%>
        </ul>
    </div>
</nav>