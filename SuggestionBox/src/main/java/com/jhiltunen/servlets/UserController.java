/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.servlets;

import com.jhiltunen.entity.DatabaseHandler;
import com.jhiltunen.entity.SuggestionBean;
import com.jhiltunen.entity.UserBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author s1601378
 */
public class UserController extends HttpServlet {

    private DatabaseHandler databaseHandler = new DatabaseHandler();
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // do this for both GET and POST requests
        
        HttpSession session = request.getSession(false);
        
        // get the userId from the session
        Integer userId = (Integer) session.getAttribute("userId");
        
        // fetch information aobut the user that's logged in
        UserBean user = databaseHandler.fetchUserById(userId);
        
        // set the user information (from database) to session so it can be shown on home page
        session.setAttribute("userId", user.getUserID());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("userStatus", user.getStatus());
        session.setAttribute("groupId", user.getGroupID());
        
        // fetch number of all (user's own) suggestions and fetch number of (user's own) suggestions with each procedure
        int countOfUsersAllSuggestions = databaseHandler.countUsersAllSuggestions(userId);
        int countOfUsersAcceptedSuggestions = databaseHandler.countUsersAcceptedSuggestions(userId);
        int countOfUsersRejectedSuggestions = databaseHandler.countUsersRejectedSuggestions(userId);
        int countOfUsersAWaitingDecisionSuggestions = databaseHandler.countUsersAWaitingDecisionSuggestions(userId);
        int countOfUsersNoProcedureSuggestions = databaseHandler.countUsersNoProcedureSuggestions(userId);
        
        List<SuggestionBean> usersSuggestions = databaseHandler.fetchAllSuggestionsByUserId(userId);
        
        // set attributes for how many suggestion there are in total and how many suggestions have specific procedure
        request.setAttribute("usersAllSuggestions", countOfUsersAllSuggestions);
        request.setAttribute("usersAcceptedSuggestions", countOfUsersAcceptedSuggestions);
        request.setAttribute("usersRejectedSuggestions", countOfUsersRejectedSuggestions);
        request.setAttribute("usersAWaitingDecisionSuggestions", countOfUsersAWaitingDecisionSuggestions);
        request.setAttribute("usersNoProcedureSuggestions", countOfUsersNoProcedureSuggestions);
        
        String suggestionTitle = request.getParameter("title"); // this variable is used when user searches suggestions
        
        if (!(suggestionTitle == null)) {
            // the title prameter isn't null -> user searched suggestion
            // fetch only suggestions that has the search keyword in suggestion title
            request.setAttribute("usersSuggestions", databaseHandler.fetchUsersAllSuggestionsWhereTitleContains(suggestionTitle, userId));
        } else {
            // fetch all suggestions
            request.setAttribute("usersSuggestions", databaseHandler.fetchAllSuggestionsByUserId(userId));
        }
        
        request.getRequestDispatcher("/WEB-INF/user/index.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
