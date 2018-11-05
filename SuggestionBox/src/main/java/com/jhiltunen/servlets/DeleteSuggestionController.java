/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.servlets;

import com.jhiltunen.entity.DatabaseHandler;
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
public class DeleteSuggestionController extends HttpServlet {

    private DatabaseHandler databaseHandler = new DatabaseHandler();

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
        HttpSession session = request.getSession();

        // make sure that user is logged in
        if (session == null || session.getAttribute("groupId") == null) {
            response.sendRedirect(request.getContextPath());
        } else {
            int groupId = (Integer) session.getAttribute("groupId");

            Integer suggestionId = Integer.parseInt(request.getParameter("suggestionId"));

            // make sure that user belongs to correct group
            // only users that belong to normal User group or Admin group can delete suggestions
            if (groupId == 1) {
                request.setAttribute("suggestionId", suggestionId);
                request.getRequestDispatcher("/WEB-INF/user/deletesuggestion.jsp").forward(request, response);
            } else if (groupId == 2) {
                request.getRequestDispatcher("ControlGroupController").forward(request, response);
            } else if (groupId == 3) {
                request.setAttribute("suggestionId", suggestionId);
                request.getRequestDispatcher("/WEB-INF/admin/deletesuggestion.jsp").forward(request, response);
            }
        }
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

        HttpSession session = request.getSession();

        int suggestionID = Integer.parseInt(request.getParameter("suggestionID"));
        
        Integer userId = (Integer) session.getAttribute("userId");
        int groupId = (Integer) session.getAttribute("groupId");

        if (groupId == 1) {
            // allow user only to deactivate his/her own suggestions
            databaseHandler.deactivateSuggestionByIdAndUserId(suggestionID, userId);
            request.getRequestDispatcher("UserController").forward(request, response);
        } else if (groupId == 3) {
            // admin users can deactivate suggestions made by any user
            databaseHandler.deactivateSuggestionById(suggestionID);
            request.getRequestDispatcher("AdminController").forward(request, response);
        }
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
