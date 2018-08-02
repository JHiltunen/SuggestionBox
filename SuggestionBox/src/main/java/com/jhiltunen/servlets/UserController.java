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
        
        HttpSession session = request.getSession(false);
        
        Integer userId = (Integer) session.getAttribute("userId");
        
        UserBean user = databaseHandler.fetchUserById(userId);
        
        session.setAttribute("userId", user.getUserID());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("userStatus", user.getStatus());
        session.setAttribute("groupId", user.getGroupID());
        
        int usersAllSuggestions = databaseHandler.countUsersAllSuggestions(userId);
        int usersAcceptedSuggestions = databaseHandler.countUsersAcceptedSuggestions(userId);
        int usersRejectedSuggestions = databaseHandler.countUsersRejectedSuggestions(userId);
        int usersWaitingDecisionSuggestions = databaseHandler.countUsersRejectedSuggestions(userId);
        int usersNoProcedureSuggestions = databaseHandler.countUsersNoProcedureSuggestions(userId);
        
        List<SuggestionBean> usersSuggestions = databaseHandler.fetchAllSuggestionsByUserId(userId);
        
        request.setAttribute("usersAllSuggestions", usersAllSuggestions);
        request.setAttribute("usersAcceptedSuggestions", usersAcceptedSuggestions);
        request.setAttribute("usersRejectedSuggestions", usersRejectedSuggestions);
        request.setAttribute("usersWaitingDecisionSuggestions", usersWaitingDecisionSuggestions);
        request.setAttribute("usersNoProcedureSuggestions", usersNoProcedureSuggestions);
        
        String suggestionTitle = request.getParameter("title");
        
        if (!(suggestionTitle == null)) {
            request.setAttribute("usersSuggestions", databaseHandler.fetchUsersAllSuggestionsWhereTitleContains(suggestionTitle, userId));
        } else {
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
