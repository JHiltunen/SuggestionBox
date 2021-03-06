<%-- 
    Document   : addsuggestionprocedure
    Created on : May 24, 2018, 8:02:36 PM
    Author     : s1601378
--%>

<%@page import="com.jhiltunen.entity.UserBean"%>
<%@page import="com.jhiltunen.entity.Status"%>
<%@page import="com.jhiltunen.entity.ProcedureStatus"%>
<%@page import="com.jhiltunen.entity.SuggestionBean"%>
<%@page import="com.jhiltunen.entity.DatabaseHandler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>SuggestionBox</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css">
        <link rel="stylesheet" href="assets/css/styles.min.css">
    </head>

    <body>
        <div>
            <nav class="navbar navbar-light navbar-expand-md navigation-clean">
                <div class="container"><a class="navbar-brand" href="#">SuggestionBox</a><button class="navbar-toggler" data-toggle="collapse" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
                    <div class="collapse navbar-collapse"
                         id="navcol-1">
                        <ul class="nav navbar-nav ml-auto">
                            <li class="dropdown"><a class="dropdown-toggle nav-link dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#">${username}</a>
                                <div class="dropdown-menu" role="menu"><a class="dropdown-item" role="presentation" href="ControlGroupController">Home</a><a class="dropdown-item" role="presentation" href="ProfileController">Profile</a><a class="dropdown-item" role="presentation" href="logout">Log out</a></div>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
        <div class="register-photo">
            <div class="form-container">
                <form action="ProcedureController" method="POST">
                    <p class="errors">${errors}</p>
                    <h2 class="text-center"><strong>Add/Edit Procedure</strong></h2>
                    <div class="form-signin">
                        <label>Suggestion id</label>
                        <div class="form-group">
                            <input class="form-control" type="text" name="suggestionId" value="${suggestion.id}" readonly>
                        </div>
                        <label>Suggestion title</label>
                        <div class="form-group">
                            <input class="form-control" type="text" name="suggestionTitle" value="${suggestion.title}">
                        </div>
                        <label>Suggestion description</label>
                        <div class="form-group">
                            <textarea class="form-control" name="suggestionDescription" rows="3">${suggestion.description}</textarea>
                        </div>
                        <label>Suggestion creation date</label>
                        <div class="form-group">
                            <input class="form-control" type="text" name="suggestionCreationDate" value="${suggestion.creationDate}" readonly>
                        </div>
                        <label>User id (user that made the suggestion)</label>
                        <div class="form-group">
                            <input class="form-control" type="text" name="userId" value="${suggestion.userID}" readonly>
                        </div>
                        <label>Suggestion status</label>
                        <div class="form-group">
                            <select class="form-control" name="status" readonly>
                                <c:if test="${suggestion.status == 'Active'}">
                                    <option value="Active" selected>Active</option>
                                </c:if>
                                <c:if test="${suggestion.status == 'Deleted'}">
                                    <option value="Deleted" selected>Deleted</option>
                                </c:if>
                            </select>
                        </div>
                    </div>                

                    <form class="form-signin" action="ProcedureController" method="POST">
                        <input type="hidden" name="suggestionID" value="${suggestion.id}">
                        <label>Suggestion procedure</label>
                        <select id="suggestionProcedure" class="custom-select" name="procedure">

                            <c:if test="${suggestion.procedure.suggestionProcedure == 'NOPROCEDURE'}">
                                <option value="NOPROCEDURE" selected>No procedure</option>
                                <option value="AWAITINGDECISION">Waiting for the decision</option>
                                <option value="APPROVED">Accepted</option>
                                <option value="REJECTED">Rejected</option>
                            </c:if>
                            <c:if test="${suggestion.procedure.suggestionProcedure == 'AWAITINGDECISION'}">
                                <option value="NOPROCEDURE">No procedure</option>
                                <option value="AWAITINGDECISION" selected>Waiting for the decision</option>
                                <option value="APPROVED">Accepted</option>
                                <option value="REJECTED">Rejected</option>
                            </c:if>
                            <c:if test="${suggestion.procedure.suggestionProcedure == 'APPROVED'}">
                                <option value="NOPROCEDURE">No procedure</option>
                                <option value="AWAITINGDECISION">Waiting for the decision</option>
                                <option value="APPROVED" selected>Accepted</option>
                                <option value="REJECTED">Rejected</option>
                            </c:if>
                            <c:if test="${suggestion.procedure.suggestionProcedure == 'REJECTED'}">
                                <option value="NOPROCEDURE">No procedure</option>
                                <option value="AWAITINGDECISION">Waiting for the decision</option>
                                <option value="APPROVED">Accepted</option>
                                <option value="REJECTED" selected>Rejected</option>
                            </c:if>
                        </select>
                        <br>
                        <label>Procedure description</label>
                        <div class="form-group">
                            <textarea class="form-control" rows="5" id="procedureDescription" name="procedureDescription" value="${suggestion.procedure.description}">${suggestion.procedure.description}</textarea>
                        </div>
                        <button type="submit" class="btn btn-lg btn-primary btn-block" name="submit">Save changes</button>
                    </form>
            </div>
        </div>
        <div class="footer-basic">
            <footer>
                <div class="social"><a href="#"><i class="icon ion-social-instagram"></i></a><a href="#"><i class="icon ion-social-snapchat"></i></a><a href="#"><i class="icon ion-social-twitter"></i></a><a href="#"><i class="icon ion-social-facebook"></i></a></div>
                <ul class="list-inline">
                    <li class="list-inline-item"><a href="#">Home</a></li>
                    <li class="list-inline-item"><a href="#">Documentation</a></li>
                    <li class="list-inline-item"><a href="#">About</a></li>
                    <li class="list-inline-item"><a href="#">Terms</a></li>
                    <li class="list-inline-item"><a href="#">Privacy Policy</a></li>
                </ul>
                <p class="copyright">SuggestionBox © 2018</p>
            </footer>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/script.min.js"></script>
    </body>

</html>