<%-- 
    Document   : deleteinitiative
    Created on : May 21, 2018, 11:28:39 PM
    Author     : s1601378
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <form id="deleteSuggestion" action="DeleteSuggestionController" method="POST">
            <input type="hidden" name="suggestionID" value="${suggestionId}">
        </form>
        <script>
            window.onload = document.getElementById("deleteSuggestion").submit();
        </script>
    </body>
</html>
