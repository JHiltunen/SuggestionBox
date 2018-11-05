/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.servlets;

import com.jhiltunen.entity.DatabaseHandler;
import com.jhiltunen.entity.Status;
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
public class ProfileController extends HttpServlet {

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
        HttpSession session = request.getSession(false);

        // make sure that user is logged in
        if (session == null || session.getAttribute("groupId") == null) {
            response.sendRedirect(request.getContextPath());
        } else {
            DatabaseHandler databaseHandler = new DatabaseHandler();
            UserBean user = databaseHandler.fetchUserById((int) session.getAttribute("userId"));
            
            // forward user to the correct page
            if ((int) session.getAttribute("groupId") == 1) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/user/profile.jsp").forward(request, response);
            } else if ((int) session.getAttribute("groupId") == 2) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/controlgroup/profile.jsp").forward(request, response);
            } else if ((int) session.getAttribute("groupId") == 3) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/WEB-INF/admin/profile.jsp").forward(request, response);
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

        HttpSession session = request.getSession();
        int groupId = (int) session.getAttribute("groupId");

        DatabaseHandler databaseHandler = new DatabaseHandler();

        UserBean user = new UserBean();

        user.setUserID(Integer.parseInt(request.getParameter("userId")));
        user.setFirstname(request.getParameter("firstname"));
        user.setLastname(request.getParameter("lastname"));
        user.setEmail(request.getParameter("email"));
        user.setUsername(request.getParameter("username"));
        user.setPhone(request.getParameter("phone"));
        user.setGroupID(groupId);
        user.setStatus(Status.valueOf(request.getParameter("status")));

        databaseHandler.updateUser(user);

        if (groupId == 1) {
            request.getRequestDispatcher("UserController").forward(request, response);
        } else if (groupId == 2) {
            request.getRequestDispatcher("ControlGroupController").forward(request, response);
        } else if (groupId == 3) {
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
