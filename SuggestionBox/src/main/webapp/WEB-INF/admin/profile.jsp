<%-- 
    Document   : profile
    Created on : Jun 24, 2018, 11:55:04 PM
    Author     : s1601378
--%>

<%@page import="com.jhiltunen.entity.Status"%>
<%@page import="com.jhiltunen.entity.UserBean"%>
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
                                <div class="dropdown-menu" role="menu"><a class="dropdown-item" role="presentation" href="AdminController">Home</a><a class="dropdown-item" role="presentation" href="ProfileController">Profile</a><a class="dropdown-item" role="presentation" href="logout">Log out</a></div>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
        <div class="register-photo">
            <div class="form-container">
                <form action="ProfileController" method="POST">
                    <p class="errors">${errors}</p>
                    <h2 class="text-center"><strong>Edit User</strong></h2>
                    <label>User id</label>
                    <div class="form-group">
                        <input class="form-control" type="text" name="userId" value="${user.userID}" readonly>
                    </div>
                    <label>Firstname</label>
                    <div class="form-group">
                        <input class="form-control" type="text" name="firstname" value="${user.firstname}">
                    </div>
                    <label>Lastname</label>
                    <div class="form-group">
                        <input class="form-control" type="text" name="lastname" value="${user.lastname}">
                    </div>
                    <label>Email</label>
                    <div class="form-group">
                        <input type="email" class="form-control" name="email" value="${user.email}" maxlength="60">
                    </div>
                    <label >Username</label>
                    <div class="form-group">
                        <input type="text" class="form-control" name="username" value="${user.username}" maxlength="10">
                    </div>

                    <label>Phone</label>
                    <div class="form-group">
                        <input type="text" class="form-control" name="phone" value="${user.phone}" maxlength="15">
                    </div>

                    <label>Luontipaivays</label>
                    <div class="form-group">
                        <input type="text" class="form-control" name="creationDate" value="${user.creationDate}" readonly>
                    </div>

                    <label>Ryhmä id</label>
                    <div class="form-group">
                        <input type="number" class="form-control" min="1" max="3" name="groupId" value="${user.groupID}" readonly>
                    </div>
                    <label>Tila</label>
                    <select class="custom-select" name="status">
                        <c:if test="${user.status == 'Aktiivinen'}">
                            <option value="Aktiivinen" selected>Aktiivinen</option>
                            <option value="Poistettu">Poistettu</option>
                        </c:if>
                        <c:if test="${user.status == 'Poistettu'}">
                            <option value="Aktiivinen">Aktiivinen</option>
                            <option value="Poistettu" selected>Poistettu</option>
                        </c:if>
                    </select>

                    <div class="form-group">
                        <button class="btn btn-primary btn-block" type="submit">Save Changes</button>
                    </div>
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
                <p class="copyright">SuggestionBox Â© 2018</p>
            </footer>
        </div>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/script.min.js"></script>
    </body>
</html>
