<%-- 
    Document   : deleteuser
    Created on : May 19, 2018, 8:34:59 PM
    Author     : s1601378
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <form id="deleteUser" action="DeleteUserController" method="POST">
            <input type="hidden" name="userId" value="${userId}">
        </form>
        <script>
            window.onload = document.getElementById("deleteUser").submit();
        </script>
    </body>
</html>
