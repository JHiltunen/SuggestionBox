/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.entity;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.mindrot.jbcrypt.*;

/**
 *
 * @author s1601378
 */
public class DatabaseHandler {

    private String driver;
    private String url;
    private String username;
    private String password;

    public DatabaseHandler() {

        try {
            // call the method to read database configuration from properties file
            readProperties();
        } catch (IOException ex) {
            System.out.println("Virhe Configiä luettaessa: " + ex.getMessage());
        }
    }

    /*
    Use this method when you wan't to check that the username given in parameter is only taken by the user that's information is going to be modified.
    (In case user edits his/her own information and doesn't change his/her username.)
     */
    public boolean usernameTakenOnlyById(String username, int id) {
        Connection connection = null;
        PreparedStatement usernameAvailable = null;
        ResultSet rs = null;

        try {
            // connection to database
            connection = ConnectionManagement.openConnection(driver, url, this.username, password);
            // if connection fails
            if (connection == null) {
                return false;
            }

            String selectUsername = "SELECT username FROM users WHERE username=? AND userId != ?";
            usernameAvailable = connection.prepareStatement(selectUsername);
            usernameAvailable.setString(1, username);
            usernameAvailable.setInt(2, id);

            rs = usernameAvailable.executeQuery();

            if (rs.next()) {
                // if there is already the same username (in database) that is given in parameter and the userId is different than given in parameter
                // someone else already has username given in parameter
                return false;
            } else {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // close all the connections and resources
            ConnectionManagement.closeResultSet(rs);
            ConnectionManagement.closeStatement(usernameAvailable);
            ConnectionManagement.closeConnection(connection);
        }
    }

    /*
    Use this method when you wan't to check that the email given in parameter is only taken by the user that's information is going to be modified
    (In case user edits his/her own information and doesn't change his/her username.)
     */
    public boolean emailTakenOnlyById(String email, int id) {
        Connection connection = null;
        PreparedStatement emailAvailable = null;
        ResultSet rs = null;

        try {
            // connection to database
            connection = ConnectionManagement.openConnection(driver, url, this.username, password);
            // if connection fails
            if (connection == null) {
                return false;
            }

            String selectEmail = "SELECT email FROM users WHERE email=? AND userId != ?";
            emailAvailable = connection.prepareStatement(selectEmail);
            emailAvailable.setString(1, email);
            emailAvailable.setInt(2, id);

            rs = emailAvailable.executeQuery();

            if (rs.next()) {
                // if there is already the same email (in database) that is given in parameter and the userId is different than given in parameter
                // someone else already has email given in parameter
                return false;
            } else {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // close all the connections and resources
            ConnectionManagement.closeResultSet(rs);
            ConnectionManagement.closeStatement(emailAvailable);
            ConnectionManagement.closeConnection(connection);
        }
    }

    /*
    Use this method to check if username isn't already taken
     */
    public boolean usernameAvailable(String username) {

        Connection connection = null;
        PreparedStatement usernameAvailable = null;
        ResultSet rs = null;

        try {
            // connection to database
            connection = ConnectionManagement.openConnection(driver, url, this.username, password);
            // if connection fails
            if (connection == null) {
                return false;
            }

            String selectUsername = "SELECT username FROM users WHERE username=?";
            usernameAvailable = connection.prepareStatement(selectUsername);
            usernameAvailable.setString(1, username);

            rs = usernameAvailable.executeQuery();

            if (rs.next()) {
                // if there is already the same username (in database) that is given in parameter
                return false;
            } else {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // close all the connections and resources
            ConnectionManagement.closeResultSet(rs);
            ConnectionManagement.closeStatement(usernameAvailable);
            ConnectionManagement.closeConnection(connection);
        }
    }

    /*
    Use this method to check if email isn't already taken
     */
    public boolean emailAvailable(String email) {

        Connection connection = null;
        PreparedStatement emailAvailable = null;
        ResultSet rs = null;

        try {
            // connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);
            // if connection fails
            if (connection == null) {
                return false;
            }

            String selectEmail = "SELECT email FROM users WHERE email=?";
            emailAvailable = connection.prepareStatement(selectEmail);
            emailAvailable.setString(1, email);

            rs = emailAvailable.executeQuery();

            if (rs.next()) {
                // if there is already the same email (in database) that is given in parameter
                return false;

            } else {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // close all the connections and resources
            ConnectionManagement.closeResultSet(rs);
            ConnectionManagement.closeStatement(emailAvailable);
            ConnectionManagement.closeConnection(connection);
        }
    }

    /*
    Use this method when new user should be registered
     */
    public boolean addUser(UserBean user) {
        Connection connection = null;
        PreparedStatement addUserStatement = null;

        try {
            // connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            // if connection fails
            if (connection == null) {
                return false;
            }

            // declare SQL-clause for adding new user
            String addUserSQL = "INSERT INTO users (firstname, lastname, email, username, password, phone, userCreationDate, groupId) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

            // prepare the sql statement for database
            addUserStatement = connection.prepareStatement(addUserSQL);

            String hashedUserPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            user.setPassword(hashedUserPassword);

            // bind the values from object for the addUserSQL statement
            addUserStatement.setString(1, user.getFirstname());
            addUserStatement.setString(2, user.getLastname());
            addUserStatement.setString(3, user.getEmail());
            addUserStatement.setString(4, user.getUsername());
            addUserStatement.setString(5, user.getPassword());
            addUserStatement.setString(6, user.getPhone());
            addUserStatement.setString(7, user.getCreationDate());
            addUserStatement.setInt(8, user.getGroupID());

            // if user is succesfully added, then return 1 else return 0
            return addUserStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // close all the connections and resources
            ConnectionManagement.closeStatement(addUserStatement);
            ConnectionManagement.closeConnection(connection);
        }
    }

    /*
    Method to add new suggestion
     */
    public boolean addSuggestion(SuggestionBean suggestion) {
        Connection connection = null;
        PreparedStatement addSuggestionStatement = null;

        try {
            // connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            // if connection fails
            if (connection == null) {
                return false;
            }

            // declare SQL-clause for adding new suggestion
            String addSuggestionSQL = "INSERT INTO suggestions (suggestionTitle, suggestionDescription, suggestionCreationDate, userId, suggestionprocedure) VALUES(?, ?, ?, ?, ?)";

            // prepare the sql statement for database
            addSuggestionStatement = connection.prepareStatement(addSuggestionSQL);

            // bind the values from object for the addSuggestionSQL statement
            addSuggestionStatement.setString(1, suggestion.getTitle());
            addSuggestionStatement.setString(2, suggestion.getDescription());
            addSuggestionStatement.setString(3, suggestion.getCreationDate());
            addSuggestionStatement.setInt(4, suggestion.getUserID());
            addSuggestionStatement.setString(5, suggestion.getProcedure().getSuggestionProcedure().toString());

            // if suggestion is succesfully added, then return 1 else return 0
            return addSuggestionStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // close all the connections and resources
            ConnectionManagement.closeStatement(addSuggestionStatement);
            ConnectionManagement.closeConnection(connection);
        }
    }

    // method that checks if users credentials matched in database
    public UserBean verifyLogin(UserBean user) {

        Connection connection = null;
        PreparedStatement searchClause = null;
        ResultSet resultSet = null;

        try {
            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                System.out.println("Database connection failed!");
                return null;
            } else {
                System.out.println("Connection succesful!");
            }

            // first find user with the username given in parametert
            // SQL clause that searches for user with username given in parameter
            String searchUserByUsername = "SELECT userId, firstname, lastname, email, username, password, phone, userCreationDate, groupId, status FROM users WHERE username=? AND status=?";

            // prepare the sql statement for database
            searchClause = connection.prepareStatement(searchUserByUsername);

            searchClause.setString(1, user.getUsername());
            searchClause.setString(2, Status.Active.displayName());

            // execute the query and save the result to resultSet variable
            resultSet = searchClause.executeQuery();

            if (resultSet.next()) {
                // check if password matches the hashed password from database
                if (BCrypt.checkpw(user.getPassword(), resultSet.getString("password"))) {
                    UserBean loggedIn = new UserBean();

                    loggedIn.setUserID(resultSet.getInt("userId"));
                    loggedIn.setFirstname(resultSet.getString("firstname"));
                    loggedIn.setLastname(resultSet.getString("lastname"));
                    loggedIn.setEmail(resultSet.getString("email"));
                    loggedIn.setUsername(resultSet.getString("username"));
                    loggedIn.setPhone(resultSet.getString("phone"));
                    loggedIn.setCreationDate(resultSet.getString("userCreationDate"));
                    loggedIn.setGroupID(resultSet.getInt("groupId"));
                    loggedIn.setStatus(Status.valueOf(resultSet.getString("status")));

                    return loggedIn;
                } else {
                    return null;
                }

            } else {
                // There is no such a user with credentials that user filled to the verifyLogin form
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // close all the connections and resources
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(searchClause);
            ConnectionManagement.closeConnection(connection);
        }
    }

    /*
    Counts how many suggestion specific user has
     */
    public int countUsersAllSuggestions(int userId) {

        Connection connection = null;
        PreparedStatement countUsersAllSuggestionsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                return 0;
            }

            // SQL clause that counts all suggestions by one user
            String countUsersAllSuggestions = "SELECT COUNT(suggestionProcedure) AS usersAllSuggestions FROM suggestions WHERE userId=?";

            // prepare the sql statement for database
            countUsersAllSuggestionsStatement = connection.prepareStatement(countUsersAllSuggestions);

            countUsersAllSuggestionsStatement.setInt(1, userId);

            // execute the query and save the result to resultSet variable
            resultSet = countUsersAllSuggestionsStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("usersAllSuggestions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(countUsersAllSuggestionsStatement);
            ConnectionManagement.closeConnection(connection);
        }

        return 0;
    }

    /*
    Count number of (specific) user's suggestions that have "Accepted" procedure
     */
    public int countUsersAcceptedSuggestions(int userId) {

        Connection connection = null;
        PreparedStatement countUsersAcceptedSuggestionsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                return 0;
            }

            // SQL clause that fetches all suggestion that's suggestionProcedure is "ACCEPTED"
            String countUsersAcceptedSuggestions = "SELECT COUNT(suggestionProcedure) AS usersAcceptedSuggestions FROM suggestions WHERE suggestionProcedure=? AND userId=?";

            // prepare the sql statement for database
            countUsersAcceptedSuggestionsStatement = connection.prepareStatement(countUsersAcceptedSuggestions);

            countUsersAcceptedSuggestionsStatement.setString(1, "ACCEPTED");
            countUsersAcceptedSuggestionsStatement.setInt(2, userId);

            // execute the query and save the result to resultSet variable
            resultSet = countUsersAcceptedSuggestionsStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("usersAcceptedSuggestions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(countUsersAcceptedSuggestionsStatement);
            ConnectionManagement.closeConnection(connection);
        }

        return 0;
    }

    /*
    Count number of (specific) user's suggestions that have "Rejected" procedure
     */
    public int countUsersRejectedSuggestions(int userId) {

        Connection connection = null;
        PreparedStatement countUsersRejectedSuggestionsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                return 0;
            }

            // SQL clause that fetches all suggestion that's suggestionProcedure is "REJECTED"
            String countUsersRejectedSuggestions = "SELECT COUNT(suggestionProcedure) AS usersRejectedSuggestions FROM suggestions WHERE suggestionProcedure=? AND userId=?";

            // prepare the sql statement for database
            countUsersRejectedSuggestionsStatement = connection.prepareStatement(countUsersRejectedSuggestions);

            countUsersRejectedSuggestionsStatement.setString(1, "REJECTED");
            countUsersRejectedSuggestionsStatement.setInt(2, userId);

            // execute the query and save the result to resultSet variable
            resultSet = countUsersRejectedSuggestionsStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("usersRejectedSuggestions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(countUsersRejectedSuggestionsStatement);
            ConnectionManagement.closeConnection(connection);
        }

        return 0;
    }

    /*
    Count number of (specific) user's suggestions that have "WaitingDecision" procedure
     */
    public int countUsersWaitingDecisionSuggestions(int userId) {

        Connection connection = null;
        PreparedStatement countUsersWaitingDecisionSuggestionsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                return 0;
            }

            // SQL clause that fetches all suggestion that's suggestionProcedure is "WAITINGDECISION"
            String countUsersWaitingDecisionSuggestions = "SELECT COUNT(suggestionProcedure) AS usersWaitingDecisionSuggestions FROM suggestions WHERE suggestionProcedure=? AND userId=?";

            // prepare the sql statement for database
            countUsersWaitingDecisionSuggestionsStatement = connection.prepareStatement(countUsersWaitingDecisionSuggestions);

            countUsersWaitingDecisionSuggestionsStatement.setString(1, "WAITINGDECISION");
            countUsersWaitingDecisionSuggestionsStatement.setInt(2, userId);

            // execute the query and save the result to resultSet variable
            resultSet = countUsersWaitingDecisionSuggestionsStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("usersWaitingDecisionSuggestions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(countUsersWaitingDecisionSuggestionsStatement);
            ConnectionManagement.closeConnection(connection);
        }

        return 0;
    }

    /*
    Count number of (specific) user's suggestions that have "Noprocedure" procedure
     */
    public int countUsersNoProcedureSuggestions(int userId) {

        Connection connection = null;
        PreparedStatement countUsersNoProcedureSuggestionsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                return 0;
            }

            // SQL clause that fetches all suggestion that's suggestionProcedure is "NOPROCEDURE"
            String countUsersNoProcedureSuggestions = "SELECT COUNT(suggestionProcedure) AS usersNoProcedureSuggestions FROM suggestions WHERE suggestionProcedure=? AND userId=?";

            // prepare the sql statement for database
            countUsersNoProcedureSuggestionsStatement = connection.prepareStatement(countUsersNoProcedureSuggestions);

            countUsersNoProcedureSuggestionsStatement.setString(1, "NOPROCEDURE");
            countUsersNoProcedureSuggestionsStatement.setInt(2, userId);

            // execute the query and save the result to resultSet variable
            resultSet = countUsersNoProcedureSuggestionsStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("usersNoProcedureSuggestions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(countUsersNoProcedureSuggestionsStatement);
            ConnectionManagement.closeConnection(connection);
        }

        return 0;
    }

    /*
    count number of all suggestions
     */
    public int countAllSuggestions() {

        Connection connection = null;
        PreparedStatement countAllSuggestionsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                return 0;
            }

            // SQL clause that counts all suggestions
            String countAllSuggestions = "SELECT COUNT(suggestionProcedure) AS allAllSuggestions FROM suggestions";

            // prepare the sql statement for database
            countAllSuggestionsStatement = connection.prepareStatement(countAllSuggestions);

            // execute the query and save the result to resultSet variable
            resultSet = countAllSuggestionsStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("allAllSuggestions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(countAllSuggestionsStatement);
            ConnectionManagement.closeConnection(connection);
        }

        return 0;
    }

    /*
    count number of all suggestions that have "Accepted" procedure
    */
    public int countAllAcceptedSuggestions() {

        Connection connection = null;
        PreparedStatement countAllAcceptedSuggestionsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                return 0;
            }

            // SQL clause that fetches all suggestion that's suggestionProcedure is "ACCEPTED"
            String countAllAcceptedSuggestions = "SELECT COUNT(suggestionProcedure) AS allAcceptedSuggestions FROM suggestions WHERE suggestionProcedure=?";

            // prepare the sql statement for database
            countAllAcceptedSuggestionsStatement = connection.prepareStatement(countAllAcceptedSuggestions);

            countAllAcceptedSuggestionsStatement.setString(1, "ACCEPTED");

            // execute the query and save the result to resultSet variable
            resultSet = countAllAcceptedSuggestionsStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("allAcceptedSuggestions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(countAllAcceptedSuggestionsStatement);
            ConnectionManagement.closeConnection(connection);
        }

        return 0;
    }

    /*
    count number of all suggestions that have "Rejected" procedure
    */
    public int countAllRejectedSuggestions() {

        Connection connection = null;
        PreparedStatement countAllRejectedSuggestionsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                return 0;
            }

            // SQL clause that fetches all suggestion that's suggestionProcedure is "REJECTED"
            String countAllRejectedSuggestions = "SELECT COUNT(suggestionProcedure) AS allRejectedSuggestions FROM suggestions WHERE suggestionProcedure=?";

            // prepare the sql statement for database
            countAllRejectedSuggestionsStatement = connection.prepareStatement(countAllRejectedSuggestions);

            countAllRejectedSuggestionsStatement.setString(1, "REJECTED");

            // execute the query and save the result to resultSet variable
            resultSet = countAllRejectedSuggestionsStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("allRejectedSuggestions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(countAllRejectedSuggestionsStatement);
            ConnectionManagement.closeConnection(connection);
        }

        return 0;
    }

    /*
    count number of all suggestions that have "WaitingDecision" procedure
    */
    public int countAllWaitingDecisionSuggestions() {

        Connection connection = null;
        PreparedStatement countAllWaitingDecisionSuggestionsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                return 0;
            }

            // SQL clause that fetches all suggestion that's suggestionProcedure is "WAITINGDECISION"
            String countAllWaitingDecisionSuggestions = "SELECT COUNT(suggestionProcedure) AS allWaitingDecisionSuggestions FROM suggestions WHERE suggestionProcedure=?";

            // prepare the sql statement for database
            countAllWaitingDecisionSuggestionsStatement = connection.prepareStatement(countAllWaitingDecisionSuggestions);

            countAllWaitingDecisionSuggestionsStatement.setString(1, "WAITINGDECISION");

            // execute the query and save the result to resultSet variable
            resultSet = countAllWaitingDecisionSuggestionsStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("allWaitingDecisionSuggestions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(countAllWaitingDecisionSuggestionsStatement);
            ConnectionManagement.closeConnection(connection);
        }

        return 0;
    }

    /*
    count number of all suggestions that have "Noprocedure" procedure
    */
    public int countAllNoProcedureSuggestions() {

        Connection connection = null;
        PreparedStatement countAllNoProcedureSuggestionsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                return 0;
            }

            // SQL clause that fetches all suggestion that's suggestionProcedure is "NOPROCEDURE"
            String countAllNoProcedureSuggestions = "SELECT COUNT(suggestionProcedure) AS allNoProcedureSuggestions FROM suggestions WHERE suggestionProcedure=?";

            // prepare the sql statement for database
            countAllNoProcedureSuggestionsStatement = connection.prepareStatement(countAllNoProcedureSuggestions);

            countAllNoProcedureSuggestionsStatement.setString(1, "NOPROCEDURE");

            // execute the query and save the result to resultSet variable
            resultSet = countAllNoProcedureSuggestionsStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("allNoProcedureSuggestions");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(countAllNoProcedureSuggestionsStatement);
            ConnectionManagement.closeConnection(connection);
        }

        return 0;
    }

    /*
    Method that fetches all users from database
    */
    public List<UserBean> fetchAllusers() {
        List<UserBean> users = new ArrayList<>();

        Connection connection = null;
        PreparedStatement fetchAllUsersStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                System.out.println("Database connection failed!");
                return null;
            } else {
                System.out.println("Connection succesful!");
            }

            // SQL clause that fetches all users
            String fetchAllUsersSQL = "SELECT userId, firstname, lastname, email, username, phone, userCreationDate, groupId, status FROM users ORDER BY status, userCreationDate DESC";

            // prepare the sql statement for database
            fetchAllUsersStatement = connection.prepareStatement(fetchAllUsersSQL);

            // execute the query and save the result to resultSet variable
            resultSet = fetchAllUsersStatement.executeQuery();

            while (resultSet.next()) {
                // Luodaan olio ja lisätään listaan                    
                UserBean user = new UserBean();
                user.setUserID(Integer.parseInt(resultSet.getString("userId")));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPhone(resultSet.getString("phone"));
                user.setCreationDate(resultSet.getString("userCreationDate"));
                user.setGroupID(Integer.parseInt(resultSet.getString("groupId")));
                user.setStatus(Status.valueOf(resultSet.getString("status")));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(fetchAllUsersStatement);
            ConnectionManagement.closeConnection(connection);
        }
        // return list of all users
        return users;
    }

    /*
    Method that searches all users that's firstname/lastname contains name or a part of name given in parameter
    */
    public List<UserBean> searchUserByName(String name) {
        List<UserBean> users = new ArrayList<>();

        Connection connection = null;
        PreparedStatement fetchAllUsersByNameStatement = null;
        ResultSet resultSet = null;

        String nameInUpperCase = name.toUpperCase();

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                System.out.println("Database connection failed!");
                return null;
            } else {
                System.out.println("Connection succesful!");
            }

            // SQL clause that fetches all users
            String fetchAllUsersByNameSQL = "SELECT userId, CONCAT(firstname, ' ', lastname) AS fullname, firstname, lastname, email, username, phone, userCreationDate, groupId, status FROM users HAVING UPPER(fullname) LIKE (?) ORDER BY status, userCreationDate DESC";

            // prepare the sql statement for database
            fetchAllUsersByNameStatement = connection.prepareStatement(fetchAllUsersByNameSQL);

            fetchAllUsersByNameStatement.setString(1, '%' + nameInUpperCase + '%');

            // execute the query and save the result to resultSet variable
            resultSet = fetchAllUsersByNameStatement.executeQuery();

            while (resultSet.next()) {
                // Luodaan olio ja lisätään listaan                    
                UserBean user = new UserBean();
                user.setUserID(Integer.parseInt(resultSet.getString("userId")));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPhone(resultSet.getString("phone"));
                user.setCreationDate(resultSet.getString("userCreationDate"));
                user.setGroupID(Integer.parseInt(resultSet.getString("groupId")));
                user.setStatus(Status.valueOf(resultSet.getString("status")));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(fetchAllUsersByNameStatement);
            ConnectionManagement.closeConnection(connection);
        }
        // return list of all users
        return users;
    }

    /*
    Method that fetches all suggestions from database
    */
    public List<SuggestionBean> fetchAllSuggestions() {
        List<SuggestionBean> suggestions = new ArrayList<>();

        Connection connection = null;
        PreparedStatement fetchAllSuggestionsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                System.out.println("Database connection failed!");
                return null;
            } else {
                System.out.println("Connection succesful!");
            }

            // SQL clause that fetches all suggestions
            String fetchAllSuggestionsSQL = "SELECT suggestions.suggestionId, suggestionTitle, suggestionDescription, suggestionCreationDate, suggestions.userId, suggestions.status, suggestionprocedure, username, procedureDescription, procedureCreationDate, procedures.userId AS procedureUserId FROM suggestions INNER JOIN users ON suggestions.userId = users.userId LEFT JOIN procedures ON suggestions.suggestionId = procedures.suggestionId ORDER BY suggestions.status, suggestionCreationDate DESC";

            // prepare the sql statement for database
            fetchAllSuggestionsStatement = connection.prepareStatement(fetchAllSuggestionsSQL);

            // execute the query and save the result to resultSet variable
            resultSet = fetchAllSuggestionsStatement.executeQuery();

            while (resultSet.next()) {
                // Luodaan olio ja lisätään listaan                    
                SuggestionBean suggestion = new SuggestionBean();
                ProcedureBean procedure = new ProcedureBean();

                suggestion.setUserID(resultSet.getInt("userId"));
                suggestion.setUsername(resultSet.getString("username"));
                suggestion.setId(resultSet.getInt("suggestionId"));
                suggestion.setTitle(resultSet.getString("suggestionTitle"));
                suggestion.setDescription(resultSet.getString("suggestionDescription"));
                suggestion.setCreationDate(resultSet.getString("suggestionCreationDate"));
                suggestion.setStatus(Status.valueOf(resultSet.getString("status")));
                suggestion.setProcedure(procedure);

                procedure.setSuggestionProcedure(ProcedureStatus.valueOf(resultSet.getString("suggestionprocedure")));
                procedure.setDescription(resultSet.getString("procedureDescription"));
                procedure.setDate(resultSet.getString("procedureCreationDate"));
                procedure.setUserId(resultSet.getInt("procedureUserId"));

                suggestions.add(suggestion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(fetchAllSuggestionsStatement);
            ConnectionManagement.closeConnection(connection);
        }
        // return list of all suggestions
        return suggestions;
    }

    /*
    Fetches all suggestions where suggestionTitle contains string given in method parameter
    */
    public List<SuggestionBean> fetchAllSuggestionsWhereTitleContains(String suggestionTitleContains) {
        List<SuggestionBean> suggestions = new ArrayList<>();

        Connection connection = null;
        PreparedStatement fetchAllSuggestionsWhereTitleContainsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                System.out.println("Database connection failed!");
                return null;
            } else {
                System.out.println("Connection succesful!");
            }

            String suggestionTitleUppercase = suggestionTitleContains.toUpperCase();

            // SQL clause that fetches all suggestions
            String fetchAllSuggestionsWhereTitleContainsSQL = "SELECT suggestions.suggestionId, suggestionTitle, suggestionDescription, suggestionCreationDate, suggestions.userId, suggestions.status, suggestionprocedure, username, procedureDescription, procedureCreationDate, procedures.userId AS procedureUserId FROM suggestions INNER JOIN users ON suggestions.userId = users.userId LEFT JOIN procedures ON suggestions.suggestionId = procedures.suggestionId WHERE UPPER(suggestionTitle) LIKE ? ORDER BY suggestions.status, suggestionCreationDate DESC";

            // prepare the sql statement for database
            fetchAllSuggestionsWhereTitleContainsStatement = connection.prepareStatement(fetchAllSuggestionsWhereTitleContainsSQL);

            fetchAllSuggestionsWhereTitleContainsStatement.setString(1, '%' + suggestionTitleUppercase + '%');

            // execute the query and save the result to resultSet variable
            resultSet = fetchAllSuggestionsWhereTitleContainsStatement.executeQuery();

            while (resultSet.next()) {
                // Luodaan olio ja lisätään listaan                    
                SuggestionBean suggestion = new SuggestionBean();
                ProcedureBean procedure = new ProcedureBean();

                suggestion.setUserID(resultSet.getInt("userId"));
                suggestion.setUsername(resultSet.getString("username"));
                suggestion.setId(resultSet.getInt("suggestionId"));
                suggestion.setTitle(resultSet.getString("suggestionTitle"));
                suggestion.setDescription(resultSet.getString("suggestionDescription"));
                suggestion.setCreationDate(resultSet.getString("suggestionCreationDate"));
                suggestion.setStatus(Status.valueOf(resultSet.getString("status")));
                suggestion.setProcedure(procedure);

                procedure.setSuggestionProcedure(ProcedureStatus.valueOf(resultSet.getString("suggestionprocedure")));
                procedure.setDescription(resultSet.getString("procedureDescription"));
                procedure.setDate(resultSet.getString("procedureCreationDate"));
                procedure.setUserId(resultSet.getInt("procedureUserId"));

                suggestions.add(suggestion);
            }

            System.out.println("Suggestions: " + suggestions);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(fetchAllSuggestionsWhereTitleContainsStatement);
            ConnectionManagement.closeConnection(connection);
        }
        // return list of all suggestions
        return suggestions;
    }

    /*
    Fetches all suggestions (from specific user; user id given in method parameter) where suggestionTitle contains string given in method parameter
    */
    public List<SuggestionBean> fetchUsersAllSuggestionsWhereTitleContains(String suggestionTitleContains, int userId) {
        List<SuggestionBean> suggestions = new ArrayList<>();

        Connection connection = null;
        PreparedStatement fetchUsersAllSuggestionsWhereTitleContainsStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                System.out.println("Database connection failed!");
                return null;
            } else {
                System.out.println("Connection succesful!");
            }

            String suggestionTitleUppercase = suggestionTitleContains.toUpperCase();

            // SQL clause that fetches all suggestions
            String fetchUsersAllSuggestionsWhereTitleContainsSQL = "SELECT suggestions.suggestionId, suggestionTitle, suggestionDescription, suggestionCreationDate, suggestions.userId, suggestions.status, suggestionprocedure, username, procedureDescription, procedureCreationDate, procedures.userId AS procedureUserId FROM suggestions INNER JOIN users ON suggestions.userId = users.userId LEFT JOIN procedures ON suggestions.suggestionId = procedures.suggestionId WHERE UPPER(suggestionTitle) LIKE ? AND suggestions.userId = ? ORDER BY suggestions.status, suggestionCreationDate DESC";

            // prepare the sql statement for database
            fetchUsersAllSuggestionsWhereTitleContainsStatement = connection.prepareStatement(fetchUsersAllSuggestionsWhereTitleContainsSQL);

            fetchUsersAllSuggestionsWhereTitleContainsStatement.setString(1, '%' + suggestionTitleUppercase + '%');
            fetchUsersAllSuggestionsWhereTitleContainsStatement.setInt(2, userId);

            // execute the query and save the result to resultSet variable
            resultSet = fetchUsersAllSuggestionsWhereTitleContainsStatement.executeQuery();

            while (resultSet.next()) {
                // Luodaan olio ja lisätään listaan                    
                SuggestionBean suggestion = new SuggestionBean();
                ProcedureBean procedure = new ProcedureBean();

                suggestion.setUserID(resultSet.getInt("userId"));
                suggestion.setUsername(resultSet.getString("username"));
                suggestion.setId(resultSet.getInt("suggestionId"));
                suggestion.setTitle(resultSet.getString("suggestionTitle"));
                suggestion.setDescription(resultSet.getString("suggestionDescription"));
                suggestion.setCreationDate(resultSet.getString("suggestionCreationDate"));
                suggestion.setStatus(Status.valueOf(resultSet.getString("status")));
                suggestion.setProcedure(procedure);

                procedure.setSuggestionProcedure(ProcedureStatus.valueOf(resultSet.getString("suggestionprocedure")));
                procedure.setDescription(resultSet.getString("procedureDescription"));
                procedure.setDate(resultSet.getString("procedureCreationDate"));
                procedure.setUserId(resultSet.getInt("procedureUserId"));

                suggestions.add(suggestion);
            }

            System.out.println("Suggestions: " + suggestions);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(fetchUsersAllSuggestionsWhereTitleContainsStatement);
            ConnectionManagement.closeConnection(connection);
        }
        // return list of all suggestions
        return suggestions;
    }

    /*
    Fetches all suggestions that are made by specific user (userId defined in method parameter)
    */
    public List<SuggestionBean> fetchAllSuggestionsByUserId(int userID) {
        List<SuggestionBean> suggestions = new ArrayList<>();

        Connection connection = null;
        PreparedStatement fetchAllSuggestionsByUserIdStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                System.out.println("Database connection failed!");
                return null;
            } else {
                System.out.println("Connection succesful!");
            }

            // SQL clause that fetches all suggestions by userId
            String fetchAllSuggestionsByUserIdSQL = "SELECT suggestions.suggestionId, suggestionTitle, suggestionDescription, suggestionCreationDate, suggestions.userId, suggestions.status, suggestionprocedure, procedureDescription, procedureCreationDate, procedures.userId AS procedureUserId FROM suggestions LEFT JOIN procedures ON suggestions.suggestionId = procedures.suggestionId WHERE suggestions.userId=? ORDER BY status, suggestionCreationDate DESC";

            // prepare the sql statement for database
            fetchAllSuggestionsByUserIdStatement = connection.prepareStatement(fetchAllSuggestionsByUserIdSQL);
            fetchAllSuggestionsByUserIdStatement.setInt(1, userID);

            // execute the query and save the result to resultSet variable
            resultSet = fetchAllSuggestionsByUserIdStatement.executeQuery();

            while (resultSet.next()) {
                // create Object and add to list                  
                SuggestionBean suggestion = new SuggestionBean();
                ProcedureBean procedure = new ProcedureBean();

                suggestion.setId(resultSet.getInt("suggestionId"));
                suggestion.setTitle(resultSet.getString("suggestionTitle"));
                suggestion.setDescription(resultSet.getString("suggestionDescription"));
                suggestion.setCreationDate(resultSet.getString("suggestionCreationDate"));
                suggestion.setUserID(Integer.parseInt(resultSet.getString("userId")));
                suggestion.setStatus(Status.valueOf(resultSet.getString("status")));
                suggestion.setProcedure(procedure);

                procedure.setSuggestionProcedure(ProcedureStatus.valueOf(resultSet.getString("suggestionprocedure")));
                procedure.setDescription(resultSet.getString("procedureDescription"));
                procedure.setDate(resultSet.getString("procedureCreationDate"));
                procedure.setUserId(resultSet.getInt("procedureUserId"));

                suggestions.add(suggestion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(fetchAllSuggestionsByUserIdStatement);
            ConnectionManagement.closeConnection(connection);
        }
        // return list of all suggestions that the user has made
        return suggestions;
    }

    /*
    Fetches specific (userId given in method parameter) user's user information
    */
    public UserBean fetchUserById(int userId) {
        Connection connection = null;
        PreparedStatement fetchUserByIdStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                System.out.println("Database connection failed!");
                return null;
            } else {
                System.out.println("Connection succesful!");
            }

            // SQL clause that fetches users information by id
            String fetchUserById = "SELECT userId, firstname, lastname, email, username, phone, userCreationDate, groupId, status FROM users WHERE userId=?";

            // prepare the sql statement for database
            fetchUserByIdStatement = connection.prepareStatement(fetchUserById);

            fetchUserByIdStatement.setInt(1, userId);

            // execute the query and save the result to resultSet variable
            resultSet = fetchUserByIdStatement.executeQuery();

            if (resultSet.next()) {
                // get the user information from database and return UserBean with the data
                UserBean user = new UserBean();
                user.setUserID(resultSet.getInt("userId"));
                user.setFirstname(resultSet.getString("firstname"));
                user.setLastname(resultSet.getString("lastname"));
                user.setEmail(resultSet.getString("email"));
                user.setUsername(resultSet.getString("username"));
                user.setPhone(resultSet.getString("phone"));
                user.setCreationDate(resultSet.getString("userCreationDate"));
                user.setGroupID(resultSet.getInt("groupId"));
                user.setStatus(Status.valueOf(resultSet.getString("status")));

                return user;
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(fetchUserByIdStatement);
            ConnectionManagement.closeConnection(connection);
        }

        return null;
    }

    /*
    Fetches suggestion with specific suggestionId
    */
    public SuggestionBean fetchSuggestionById(int id) {
        Connection connection = null;
        PreparedStatement fetchSuggestionByIdStatement = null;
        ResultSet resultSet = null;

        try {

            // open connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                // Connection problem
                System.out.println("Database connection failed!");
                return null;
            } else {
                System.out.println("Connection succesful!");
            }

            // SQL clause that fetches suggestion by suggestionId
            String fetchSuggestionByIdSQL = "SELECT suggestions.suggestionId, suggestions.suggestionTitle, suggestionDescription, suggestionCreationDate, suggestions.userId, suggestions.status, suggestions.suggestionprocedure, procedureId, procedureDescription FROM suggestions LEFT JOIN procedures ON suggestions.suggestionId = procedures.suggestionId WHERE suggestions.suggestionId=?";

            // prepare the sql statement for database
            fetchSuggestionByIdStatement = connection.prepareStatement(fetchSuggestionByIdSQL);

            fetchSuggestionByIdStatement.setInt(1, id);

            // execute the query and save the result to resultSet variable
            resultSet = fetchSuggestionByIdStatement.executeQuery();

            if (resultSet.next()) {
                // get the suggestion information from database and return SuggestionBean with the data
                SuggestionBean suggestion = new SuggestionBean();
                ProcedureBean procedure = new ProcedureBean();

                suggestion.setId(resultSet.getInt("suggestionId"));
                suggestion.setTitle(resultSet.getString("suggestionTitle"));
                suggestion.setDescription(resultSet.getString("suggestionDescription"));
                suggestion.setCreationDate(resultSet.getString("suggestionCreationDate"));
                suggestion.setUserID(resultSet.getInt("userId"));
                suggestion.setStatus(Status.valueOf(resultSet.getString("status")));
                suggestion.setProcedure(procedure);

                procedure.setSuggestionProcedure(ProcedureStatus.valueOf(resultSet.getString("suggestionprocedure")));
                procedure.setDescription(resultSet.getString("procedureDescription"));
                if (resultSet.wasNull()) {
                    // in case of the last column (procedureDescription) that was read has value of SQL null
                    // then set the description of procedure to empty string to avoid null pointer exception
                    procedure.setDescription("");
                }

                return suggestion;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(fetchSuggestionByIdStatement);
            ConnectionManagement.closeConnection(connection);
        }
        return null;
    }

    /*
    Method that's used to update user's information
    */
    public boolean updateUser(UserBean user) {
        Connection connection = null;
        PreparedStatement updateUserStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                return false;
            }

            // SQL clause that updates users information that's id is same as given in parameter object
            String updateUser = "UPDATE users set firstname=?, lastname=?, email=?, username=?, phone=?, groupId=?, status=? WHERE userId=?";

            // prepare the sql statement for database
            updateUserStatement = connection.prepareStatement(updateUser);

            // bind the values from object for the addUserSQL statement
            updateUserStatement.setString(1, user.getFirstname());
            updateUserStatement.setString(2, user.getLastname());
            updateUserStatement.setString(3, user.getEmail());
            updateUserStatement.setString(4, user.getUsername());
            updateUserStatement.setString(5, user.getPhone());
            updateUserStatement.setInt(6, user.getGroupID());
            updateUserStatement.setString(7, user.getStatus().toString());
            updateUserStatement.setInt(8, user.getUserID());

            // execute the query and save the result to resultSet variable
            return updateUserStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Virhe päivityksessä: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(updateUserStatement);
            ConnectionManagement.closeConnection(connection);
        }
        return false;
    }

    /*
    Method that updates suggestions information
    */
    public boolean updateSuggestion(SuggestionBean suggestion) {
        Connection connection = null;
        PreparedStatement updateSuggestionStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                return false;
            }

            // SQL clause that updates suggestions information that's id is same as given in parameter object
            String updateSuggestionSQL = "UPDATE suggestions set suggestionTitle=?, suggestionDescription=?, status=? WHERE suggestionId=?";

            // prepare the sql statement for database
            updateSuggestionStatement = connection.prepareStatement(updateSuggestionSQL);

            // bind the values from object for the update  statement
            updateSuggestionStatement.setString(1, suggestion.getTitle());
            updateSuggestionStatement.setString(2, suggestion.getDescription());
            updateSuggestionStatement.setString(3, suggestion.getStatus().displayName());
            updateSuggestionStatement.setInt(4, suggestion.getId());

            // execute the query and save the result to resultSet variable
            return updateSuggestionStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Virhe päivityksessä: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(updateSuggestionStatement);
            ConnectionManagement.closeConnection(connection);
        }
        return false;
    }

    /*
    Method that checks if theres suggestion with specific id
    */
    public boolean suggestionExists(int suggestionId) {
        Connection connection = null;
        PreparedStatement suggestionExistsStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                return false;
            }

            // SQL clause that fetches suggestion id that's id is same as given in parameter
            String suggestionExistsSQL = "SELECT suggestionId FROM suggestions WHERE suggestionId=?";

            // prepare the sql statement for database
            suggestionExistsStatement = connection.prepareStatement(suggestionExistsSQL);

            // bind the values from object for the update  statement
            suggestionExistsStatement.setInt(1, suggestionId);

            // execute the query and save the result to resultSet variable
            resultSet = suggestionExistsStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(suggestionExistsStatement);
            ConnectionManagement.closeConnection(connection);
        }
        return false;
    }

    /**
     * Use this method when you want user to be only able to edit his/her own
     * suggestions
     *
     * @param suggestion
     * @return true if update was succesful. Else return false
     */
    public boolean updateSuggestionByUserId(SuggestionBean suggestion) {
        Connection connection = null;
        PreparedStatement updateSuggestionStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                return false;
            }

            // SQL clause that updates suggestions information that's id is same as given in parameter object
            String updateSuggestionSQL = "UPDATE suggestions set suggestionTitle=?, suggestionDescription=?, status=? WHERE suggestionId=? AND userId=?";

            // prepare the sql statement for database
            updateSuggestionStatement = connection.prepareStatement(updateSuggestionSQL);

            // bind the values from object for the update  statement
            updateSuggestionStatement.setString(1, suggestion.getTitle());
            updateSuggestionStatement.setString(2, suggestion.getDescription());
            updateSuggestionStatement.setString(3, suggestion.getStatus().toString());
            updateSuggestionStatement.setInt(4, suggestion.getId());
            updateSuggestionStatement.setInt(5, suggestion.getUserID());

            // execute the query and save the result to resultSet variable
            return updateSuggestionStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Virhe päivityksessä: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(updateSuggestionStatement);
            ConnectionManagement.closeConnection(connection);
        }
        return false;
    }

    /*
    method that updates specific suggestions suggestionprocedurestatus
    */
    public boolean updateSuggestionProcedureStatus(ProcedureStatus suggestionProcedure, int suggestionId) {
        Connection connection = null;
        PreparedStatement updateSuggestionProcedureStatement = null;

        try {
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                return false;
            }

            // SQL clause that updates suggestion suggestionprocedure that's suggestionID is same as id given in method parameter
            String updateSuggestionProcedureSQL = "UPDATE suggestions set suggestionprocedure=? WHERE suggestionId=?";

            // prepare the sql statement for database
            updateSuggestionProcedureStatement = connection.prepareStatement(updateSuggestionProcedureSQL);

            // bind the values from object for the update statement
            updateSuggestionProcedureStatement.setString(1, suggestionProcedure.toString());
            updateSuggestionProcedureStatement.setInt(2, suggestionId);

            // execute the update; return 1 if succesful else return 0
            return updateSuggestionProcedureStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionManagement.closeStatement(updateSuggestionProcedureStatement);
            ConnectionManagement.closeConnection(connection);
        }
    }

    /*
    method that adds procedure and if there's duplicate key, then updates procedure information
    */
    public boolean addProcedure(ProcedureBean procedure) {
        Connection connection = null;
        PreparedStatement addProcedureStatement = null;

        try {
            // connection to database
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            // if connection fails
            if (connection == null) {
                return false;
            }

            // declare SQL-clause for adding new procedure. If there is duplicate unique key, description of procedure will be updated
            // status of the suggestion procedure is handled in SuggestionProcedureController-servlet
            String addProcedureSQL = "INSERT INTO procedures (procedureDescription, procedureCreationDate, userId, suggestionId) VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE procedureDescription=?";

            // prepare the sql statement for database
            addProcedureStatement = connection.prepareStatement(addProcedureSQL);

            // bind the values from object for the addSuggestionSQL statement
            addProcedureStatement.setString(1, procedure.getDescription());
            addProcedureStatement.setString(2, procedure.getDate());
            addProcedureStatement.setInt(3, procedure.getUserId());
            addProcedureStatement.setInt(4, procedure.getSuggestionId());
            addProcedureStatement.setString(5, procedure.getDescription());

            // if procedure is succesfully added or updated, then return 1 else return 0
            return addProcedureStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // close all the connections and resources
            ConnectionManagement.closeStatement(addProcedureStatement);
            ConnectionManagement.closeConnection(connection);
        }
    }

    /*
    changes the users (with specific userId) status to poistettu
    */
    public boolean deactivateUserByID(int userID) {
        Connection connection = null;
        PreparedStatement deactivateUserStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                return false;
            }

            // SQL clause that updates users information that's id is same as given in parameter object
            String deactivateUserSQL = "UPDATE users set status=? WHERE userId=?";

            // prepare the sql statement for database
            deactivateUserStatement = connection.prepareStatement(deactivateUserSQL);

            // bind the values from object for the addUserSQL statement
            deactivateUserStatement.setString(1, Status.Deleted.displayName());
            deactivateUserStatement.setInt(2, userID);

            // execute the query and save the result to resultSet variable
            return deactivateUserStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Virhe päivityksessä: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(deactivateUserStatement);
            ConnectionManagement.closeConnection(connection);
        }
        return false;
    }

    /*
    changes the suggestion (with specific suggestionId) status to poistettu
    */
    public boolean deactivateSuggestionById(int suggestionID) {
        Connection connection = null;
        PreparedStatement deactivateSuggestionByIdStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                return false;
            }

            // SQL clause that updates suggestion information that's id is same as given in parameter object
            String deactivateSuggestionByIdSQL = "UPDATE suggestions set status=? WHERE suggestionId=?";

            // prepare the sql statement for database
            deactivateSuggestionByIdStatement = connection.prepareStatement(deactivateSuggestionByIdSQL);

            // bind the values from object for the addUserSQL statement
            deactivateSuggestionByIdStatement.setString(1, Status.Deleted.displayName());
            deactivateSuggestionByIdStatement.setInt(2, suggestionID);

            // execute the query and save the result to resultSet variable
            return deactivateSuggestionByIdStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Virhe päivityksessä: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(deactivateSuggestionByIdStatement);
            ConnectionManagement.closeConnection(connection);
        }
        return false;
    }

    /*
    method to allow standard user deactivate his/her own suggestion
    checks that suggestion userId is same as the userId that came in parameter
    */
    public boolean deactivateSuggestionByIdAndUserId(int suggestionID, int userId) {
        Connection connection = null;
        PreparedStatement deactivateSuggestionByIdAndUserIdStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionManagement.openConnection(driver, url, username, password);

            if (connection == null) {
                return false;
            }

            // SQL clause that updates suggestion information that's id is same as given in parameter object
            String deactivateSuggestionByIdAndUserIdSQL = "UPDATE suggestions set status=? WHERE suggestionId=? AND userId=?";

            // prepare the sql statement for database
            deactivateSuggestionByIdAndUserIdStatement = connection.prepareStatement(deactivateSuggestionByIdAndUserIdSQL);

            // bind the values from object for the addUserSQL statement
            deactivateSuggestionByIdAndUserIdStatement.setString(1, Status.Deleted.displayName());
            deactivateSuggestionByIdAndUserIdStatement.setInt(2, suggestionID);
            deactivateSuggestionByIdAndUserIdStatement.setInt(3, userId);

            // execute the query and save the result to resultSet variable
            return deactivateSuggestionByIdAndUserIdStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Virhe päivityksessä: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManagement.closeResultSet(resultSet);
            ConnectionManagement.closeStatement(deactivateSuggestionByIdAndUserIdStatement);
            ConnectionManagement.closeConnection(connection);
        }
        return false;
    }

    private void readProperties() throws IOException {
        // Get the inputStream
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("file/config.properties");

        Properties properties = new Properties();

        // load the inputStream using the Properties
        properties.load(inputStream);
        // get the value of the property
        String driverProp = properties.getProperty("driver");
        String urlProp = properties.getProperty("url");
        String usernameProp = properties.getProperty("username");
        String passwordProp = properties.getProperty("password");

        this.driver = driverProp;
        this.url = urlProp;
        this.username = usernameProp;
        this.password = passwordProp;
    }
}
