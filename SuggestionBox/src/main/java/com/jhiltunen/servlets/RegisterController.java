/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.servlets;

import com.jhiltunen.entity.DatabaseHandler;
import com.jhiltunen.entity.UserBean;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author s1601378
 */
public class RegisterController extends HttpServlet {

    // source: https://en.wikipedia.org/wiki/Telephone_numbers_in_Finland
    private final String phoneRegex = "^((90[0-9]{3})?0|\\+358\\s?)(?!(100|20(0|2(0|[2-3])|9[8-9])|300|600|700|708|75(00[0-3]|(1|2)\\d{2}|30[0-2]|32[0-2]|75[0-2]|98[0-2])))(4|50|10[1-9]|20(1|2(1|[4-9])|[3-9])|29|30[1-9]|71|73|75(00[3-9]|30[3-9]|32[3-9]|53[3-9]|83[3-9])|2|3|5|6|8|9|1[3-9])\\s?(\\d\\s?){4,19}\\d$";

    private DatabaseHandler databaseHandler = new DatabaseHandler();

    // get the current date
    private Date date = new Date();

    // create new SimpleDateFormat
    // because the creation date of the user needs to be changed to following pattern
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
        response.sendRedirect("register.jsp");
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

        // get the information that was filled to the registration form
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        Date creationDate = date;

        UserBean user = new UserBean();

        user.setFirstname(firstname);

        user.setLastname(lastname);

        user.setEmail(email);

        user.setUsername(username);

        user.setPassword(password);

        user.setPhone(phone);

        // format the creation date to match MySQl syntax
        user.setCreationDate(sdf.format(creationDate));

        // as a default user belongs to normal User group
        user.setGroupID(1);
        
        // check for errors before adding to database
        List<String> errors = new ArrayList<>();

        if (user.getFirstname().isEmpty()) {
            errors.add("Firstname is invalid or missing");
        }

        if (user.getLastname().isEmpty()) {
            errors.add("Lastname is invalid or missing");
        }

        if (user.getEmail().isEmpty()) {
            errors.add("Email is invalid or missing");
        }

        if (user.getUsername().isEmpty()) {
            errors.add("Username is invalid or missing");
        }

        if (user.getPassword().isEmpty()) {
            errors.add("Password is invalid or missing");
        }

        if (user.getPhone().isEmpty()) {
            errors.add("Phonenumber is invalid or missing");
        }

        if (!user.getPhone().matches(phoneRegex)) {
            // validation for Finnish phone numbers
            errors.add("Invalid phone number");
        }

        if (!databaseHandler.emailAvailable(user.getEmail())) {
            errors.add("Email is already in use!");
        }

        if (!databaseHandler.usernameAvailable(user.getUsername())) {
            errors.add("Username is already in use!");
        }

        if (errors.isEmpty() && databaseHandler.addUser(user)) {
            // the new user was added succesfully to database
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            // Adding user fails
            request.setAttribute("errors", errors);
            request.setAttribute("user", user);
            request.getRequestDispatcher("register.jsp").forward(request, response);
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
