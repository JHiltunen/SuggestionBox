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
public class DeleteUserController extends HttpServlet {

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
        int userId = Integer.parseInt(request.getParameter("userId"));

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("groupId") == null) {
            response.sendRedirect(request.getContextPath());
        } else {
            // make sure that the user belongs to correct group
            // only admin users can delete users
            int groupId = Integer.parseInt(request.getParameter("groupId"));

            if (groupId == 1) {
                request.getRequestDispatcher("UserController").forward(request, response);
            } else if (groupId == 2) {
                request.getRequestDispatcher("ControlGroupController").forward(request, response);
            } else if (groupId == 3) {
                request.setAttribute("userId", userId);
                request.getRequestDispatcher("/WEB-INF/admin/deleteuser.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath());
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
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        int userId = Integer.parseInt(request.getParameter("userId"));

        // make sure that user who tries to delete another user, belongs to Admin group
        if (groupId == 1) {
            request.getRequestDispatcher("UserController").forward(request, response);
        } else if (groupId == 2) {
            request.getRequestDispatcher("ControlGroupController").forward(request, response);
        } else if (groupId == 3) {
            databaseHandler.deactivateUserByID(userId);
            request.getRequestDispatcher("AdminController").forward(request, response);
        } else {
            request.getRequestDispatcher(request.getContextPath());
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
