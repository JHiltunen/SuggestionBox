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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author s1601378
 */
public class EditUserController extends HttpServlet {

    // source: https://en.wikipedia.org/wiki/Telephone_numbers_in_Finland
    private final String phoneRegex = "^((90[0-9]{3})?0|\\+358\\s?)(?!(100|20(0|2(0|[2-3])|9[8-9])|300|600|700|708|75(00[0-3]|(1|2)\\d{2}|30[0-2]|32[0-2]|75[0-2]|98[0-2])))(4|50|10[1-9]|20(1|2(1|[4-9])|[3-9])|29|30[1-9]|71|73|75(00[3-9]|30[3-9]|32[3-9]|53[3-9]|83[3-9])|2|3|5|6|8|9|1[3-9])\\s?(\\d\\s?){4,19}\\d$";

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

        UserBean user = databaseHandler.fetchUserById(userId);

        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/admin/edituser.jsp").forward(request, response);
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

        // get the user information from the user edit form        
        int userId = Integer.parseInt(request.getParameter("userId"));
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        String status = request.getParameter("status");

        UserBean user = new UserBean();

        user.setUserID(userId);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setUsername(username);
        user.setPhone(phone);
        user.setGroupID(groupId);
        user.setStatus(Status.valueOf(status));

        // check for errors before adding to database
        List<String> errors = new ArrayList<>();

        if (user.getFirstname().isEmpty()) {
            errors.add("Please fill in your first name");
        }

        if (user.getLastname().isEmpty()) {
            errors.add("Please fill in your last name");
        }

        if (user.getEmail().isEmpty()) {
            errors.add("Please fill in your email");
        }

        if (user.getUsername().isEmpty()) {
            errors.add("Please fill in your username");
        }

        if (user.getPhone().isEmpty()) {
            errors.add("Please fill in your phonenumber");
        }

        if (!user.getPhone().matches(phoneRegex)) {
            errors.add("Invalid phone number");
        }

        if (user.getGroupID() == 0) {
            errors.add("Please fill in group id");
        }

        if (user.getStatus().displayName().isEmpty()) {
            errors.add("Please choose the correct status for the user");
        }
        
        if (errors.isEmpty() && databaseHandler.updateUser(user)) {
            // Changes succesfully saved to database
            request.getRequestDispatcher("AdminController").forward(request, response);
        } else {
            // Editing user fails
            request.setAttribute("errors", errors);
            user = databaseHandler.fetchUserById(user.getUserID());

            request.setAttribute("user", user);
            request.getRequestDispatcher("WEB-INF/admin/edituser.jsp").forward(request, response);
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