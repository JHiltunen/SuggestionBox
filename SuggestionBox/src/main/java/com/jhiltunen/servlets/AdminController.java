/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.servlets;

import com.jhiltunen.entity.DatabaseHandler;
import com.jhiltunen.entity.UserBean;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author s1601378
 */
public class AdminController extends HttpServlet {

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
        
        HttpSession session = request.getSession();

        // get the userId from the session
        int userId = (int) session.getAttribute("userId");

        // fetch information aobut the user that's logged in
        UserBean user = databaseHandler.fetchUserById(userId);

        // set the user information (from database) to session so it can be shown on home page
        session.setAttribute("userId", user.getUserID());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("userStatus", user.getStatus());
        session.setAttribute("groupId", user.getGroupID());

        // fetch number of all suggestions and fetch number of suggestions with each procedure
        int countOfAllSuggestions = databaseHandler.countAllSuggestions();
        int countOfAllAcceptedSuggestions = databaseHandler.countAllAcceptedSuggestions();
        int countOfAllRejectedSuggestions = databaseHandler.countAllRejectedSuggestions();
        int countOfAllWatitingDecisionSuggestions = databaseHandler.countAllAWaitingDecisionSuggestions();
        int countOfAllNoProcedureSuggestions = databaseHandler.countAllNoProcedureSuggestions();

        // set attributes for how many suggestion there are in total and how many suggestions have specific procedure
        request.setAttribute("countOfAllSuggestions", countOfAllSuggestions);
        request.setAttribute("countOfAllAcceptedSuggestions", countOfAllAcceptedSuggestions);
        request.setAttribute("countOfAllRejectedSuggestions", countOfAllRejectedSuggestions);
        request.setAttribute("countOfAllWaitingDecisionSuggestions", countOfAllWatitingDecisionSuggestions);
        request.setAttribute("countOfAllNoProcedureSuggestions", countOfAllNoProcedureSuggestions);

        String suggestionTitle =  request.getParameter("title"); // this variable is used when user searches suggestions
        String name = request.getParameter("name"); // this variable is used when user searches users

        // both parameters are null -> user didn't press search button on suggestiontitle or name forms
        if ((suggestionTitle == null) && (name == null)) {
            request.setAttribute("suggestionsList", databaseHandler.fetchAllSuggestions());
            request.setAttribute("usersList", databaseHandler.fetchAllusers());
        } else {
            if (suggestionTitle == null) {
                // if user searched with name the title attribute is going to be null
                request.setAttribute("suggestionsList", databaseHandler.fetchAllSuggestions());
                request.setAttribute("usersList", databaseHandler.searchUserByName(name));
            } else if (name == null) {
                // if user searched with title the name attribute is going to be null
                request.setAttribute("suggestionsList", databaseHandler.fetchAllSuggestionsWhereTitleContains(suggestionTitle));
                request.setAttribute("usersList", databaseHandler.fetchAllusers());
            }
        }

        request.getRequestDispatcher("WEB-INF/admin/index.jsp").forward(request, response);
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
