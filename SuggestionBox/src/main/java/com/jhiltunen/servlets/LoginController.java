/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.servlets;

import com.jhiltunen.entity.DatabaseHandler;
import com.jhiltunen.entity.UserBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author s1601378
 */
public class LoginController extends HttpServlet {

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
        // user tries to acces directly from url
        HttpSession session = request.getSession();
        
        if (session == null) {
            response.sendRedirect("index.jsp");
        } else {
            Integer groupId = (Integer) session.getAttribute("groupId");
            
            if (groupId == 1) {
                // user
                request.getRequestDispatcher("UserController").forward(request, response);
            } else if (groupId == 2) {
                // control group
                request.getRequestDispatcher("ControlGroupController").forward(request, response);
            } else if (groupId == 3) {
                // admin
                request.getRequestDispatcher("AdminController").forward(request, response);
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

        // get the username and password that user filled in the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ArrayList<String> errors = new ArrayList<>();

        if (username.isEmpty()) {
            errors.add("Please fill in your username");
        }

        if (password.isEmpty()) {
            errors.add("Please fill in your password");
        }

        if (errors.isEmpty()) {
            System.out.println("Ei tyhjiä kenttiä loginControllerissa");
            UserBean userWithCredentials = new UserBean();

            userWithCredentials.setUsername(username);
            userWithCredentials.setPassword(password);

            UserBean user = databaseHandler.verifyLogin(userWithCredentials);

            if (user != null) {
                // DatabaseHandler verifyLogin method returned UserBean (and not null) -> forward the user to correct landing page
                switch (user.getGroupID()) {

                    case 1:
                        // forward to user's front page
                        forwardUser(request, response, user);
                        break;
                    case 2:
                        // forward to control group's front page
                        forwardControlGroup(request, response, user);
                        break;
                    case 3:
                        // forward to admin's front page
                        forwardAdmin(request, response, user);
                }

            } else {
                // there was no user with credentials that user filled in
                // -> redirect to verifyLogin page with error
                request.setAttribute("errors", "Wrong username or password!");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } else {
            // some of the form fields are empty
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

    }
    
    private void forwardUser(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        request.getSession().setAttribute("userId", user.getUserID());
        request.getSession().setAttribute("username", user.getUsername());
        request.getSession().setAttribute("userStatus", user.getStatus());
        request.getSession().setAttribute("groupId", user.getGroupID());
        request.getRequestDispatcher("UserController").forward(request, response);
    }

    private void forwardControlGroup(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        request.getSession().setAttribute("userId", user.getUserID());
        request.getSession().setAttribute("username", user.getUsername());
        request.getSession().setAttribute("userStatus", user.getStatus());
        request.getSession().setAttribute("groupId", user.getGroupID());
        request.getRequestDispatcher("ControlGroupController").forward(request, response);
    }
    
    private void forwardAdmin(HttpServletRequest request, HttpServletResponse response, UserBean user) throws ServletException, IOException {
        request.getSession().setAttribute("userId", user.getUserID());
        request.getSession().setAttribute("username", user.getUsername());
        request.getSession().setAttribute("userStatus", user.getStatus());
        request.getSession().setAttribute("groupId", user.getGroupID());
        
        request.getRequestDispatcher("AdminController").forward(request, response);
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