/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.servlets;

import com.jhiltunen.entity.DatabaseHandler;
import com.jhiltunen.entity.UserBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author s1601378
 */
public class ControlGroupController extends HttpServlet {

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
        
        HttpSession session = request.getSession();
        
        int userId = (int) session.getAttribute("userId");
        
        UserBean user = databaseHandler.fetchUserById(userId);
        
        session.setAttribute("userId", user.getUserID());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("userStatus", user.getStatus());
        session.setAttribute("groupId", user.getGroupID());
        
        int allSuggestions = databaseHandler.countAllSuggestions();
        int allAcceptedSuggestions = databaseHandler.countAllAcceptedSuggestions();
        int allRejectedSuggestions = databaseHandler.countAllRejectedSuggestions();
        int allWatitingDecisionSuggestions = databaseHandler.countAllWaitingDecisionSuggestions();
        int allNoProcedureSuggestions = databaseHandler.countAllNoProcedureSuggestions();
        
        request.setAttribute("countOfAllSuggestions", allSuggestions);
        request.setAttribute("countOfAllAcceptedSuggestions", allAcceptedSuggestions);
        request.setAttribute("countOfAllRejectedSuggestions", allRejectedSuggestions);
        request.setAttribute("countOfAllWaitingDecisionSuggestions", allWatitingDecisionSuggestions);
        request.setAttribute("countOfAllNoProcedureSuggestions", allNoProcedureSuggestions);
        
        String suggestionTitle = request.getParameter("title");
        
        if (!(suggestionTitle == null)) {
            request.setAttribute("suggestionsList", databaseHandler.fetchAllSuggestionsWhereTitleContains(suggestionTitle));
        } else {
            request.setAttribute("suggestionsList", databaseHandler.fetchAllSuggestions());
        }

        request.getRequestDispatcher("WEB-INF/controlgroup/index.jsp").forward(request, response);
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
