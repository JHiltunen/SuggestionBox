<%@page import="com.jhiltunen.entity.SuggestionBean"%>
<%@page import="com.jhiltunen.entity.UserBean"%>
<%@page import="com.jhiltunen.entity.DatabaseHandler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
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
        <div>
            <div class="container">
                <div class="row">
                    <div class="col-md-8">
                        <p>This is your home page. Here you can see all suggestions/users and edit/delete them.</p>
                        <hr>
                        <h3>All Suggestions.</h3>
                        <p>Number of suggestions: ${countOfAllSuggestions}</p>
                        <div class="row suggestions">
                            <div class="col bg-success">
                                <h4 class="text-nowrap text-center text-white" style="margin-top:10px;">Approved</h4>
                                <p class="text-center text-white">${countOfAllAcceptedSuggestions}/${countOfAllSuggestions}</p>
                            </div>
                            <div class="col bg-danger">
                                <h4 class="text-nowrap text-center text-white" style="margin-top:10px;">Rejected</h4>
                                <p class="text-center text-white">${countOfAllRejectedSuggestions}/${countOfAllSuggestions}</p>
                            </div>
                            <div class="col bg-warning">
                                <h4 class="text-nowrap text-center text-white" style="margin-top:10px;">Awaiting Decision</h4>
                                <p class="text-nowrap text-center text-white">${countOfAllWaitingDecisionSuggestions}/${countOfAllSuggestions}</p>
                            </div>
                            <div class="col bg-info">
                                <h4 class="text-nowrap text-center text-white" style="margin-top:10px;">No Procedure</h4>
                                <p class="text-center text-white">${countOfAllNoProcedureSuggestions}/${countOfAllSuggestions}</p>
                            </div>
                            <form action="AdminController" method="GET">
                                <input type="text" name="title" placeholder="Search from title"/>
                                <button type="submit" class="btn btn-success">Search</button>
                            </form>
                            <p id="clearSearch"><a href="AdminController"><button type="submit" class="btn btn-info">Clear Search</button></a></p>
                        </div>
                        <hr>
                        <c:forEach items="${suggestionsList}" var="currentSuggestion">
                            <div class="card">
                                <div class="card-body">
                                    <h4 class="card-title">${currentSuggestion.title} | ${currentSuggestion.status}</h4>
                                    <h6 class="text-muted card-subtitle mb-2">${currentSuggestion.username} | ${currentSuggestion.creationDate}</h6>
                                    <p class="card-text">${currentSuggestion.description}</p>
                                    <div class="card">
                                        <div class="card-body">
                                            <h4 class="card-title">Procedure: ${currentSuggestion.procedure.suggestionProcedure} | ${currentSuggestion.procedure.date}</h4>
                                            <h6 class="text-muted card-subtitle mb-2">${currentSuggestion.procedure.description}</h6>
                                        </div>
                                    </div>
                                    <a href="#" name="${currentSuggestion.title}" id="${currentSuggestion.id}" onclick="confirmSuggestionDeletion(this)"><button type="button" class="btn btn-danger float-left management-control">Delete</button></a>
                                    <a href="EditSuggestionController?suggestionId=${currentSuggestion.id}"><button type="button" class="btn btn-info float-right management-control">Edit</button></a>
                                </div>
                            </div>
                            <hr>
                        </c:forEach>
                    </div>
                    <div class="col-md-4 users-list" id="users-list">
                        <a href="AddUserController"><button type="button" class="btn btn-success float-right management-control">Create a new user</button></a>
                        <form action="AdminController" method="GET">
                            <input type="text" name="name" placeholder="Search user by name"/>
                            <button type="submit" class="btn btn-success">Search</button>
                        </form>

                        <p><a href="AdminController"><button type="submit" class="btn btn-info">Clear Search</button></a></p>
                        <h3>Users</h3>
                        <hr>
                        <c:forEach items="${usersList}" var="currentUser">
                            <div class="user-card">
                                <div class="row">
                                    <div class="col user-img-col" style="max-height:50px;"><img class="img-fluid" src="assets/img/blank-profile-picture-973460_640.png" style="max-width:50px;height:50px;"><span>${currentUser.firstname} ${currentUser.lastname}</span></div>
                                    <div class="col-12">
                                        <p>Email: ${currentUser.email}</p>
                                        <p>Phone: ${currentUser.phone}</p>
                                        <p>Status: ${currentUser.status}</p>
                                        <a href="#" name="${currentUser.firstname} ${currentUser.lastname}" id="${currentUser.userID}" onclick="confirmUserDeletion(this)"><button type="button" class="btn btn-danger float-left management-control">Delete</button></a>
                                        <a href="EditUserController?userId=${currentUser.userID}"><button type="button" class="btn btn-info float-right management-control">Edit</button></a>
                                    </div>
                                </div>
                            </div>
                            <hr>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer-basic">
            <footer>
                <div class="social"><a href="#"><i class="icon ion-social-instagram"></i></a><a href="#"><i class="icon ion-social-snapchat"></i></a><a href="#"><i class="icon ion-social-twitter"></i></a><a href="#"><i class="icon ion-social-facebook"></i></a></div>
                <ul class="list-inline">
                    <li class="list-inline-item"><a href="#">Home</a></li>
                    <li class="list-inline-item"><a href="#">Services</a></li>
                    <li class="list-inline-item"><a href="#">About</a></li>
                    <li class="list-inline-item"><a href="#">Terms</a></li>
                    <li class="list-inline-item"><a href="#">Privacy Policy</a></li>
                </ul>
                <p class="copyright">SuggestionBox © 2018</p>
            </footer>
        </div>
        <script>
            function confirmUserDeletion(user) {
                var userDeleteConfirmed = confirm('Do you want to delete "' + user.name + '" user?');

                if (userDeleteConfirmed) {
                    window.location.replace("DeleteUserController?userId=" + user.id);
                } else {
                    // nothing to do here
                }
            }

            function confirmSuggestionDeletion(suggestion) {
                var suggestionDeleteConfirmed = confirm('Do you want to delete "' + suggestion.name + '" suggestion?');

                if (suggestionDeleteConfirmed) {
                    window.location.replace("DeleteSuggestionController?suggestionId=" + suggestion.id);
                } else {
                    // nothing to do here
                }
            }
        </script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
    </body>

</html>