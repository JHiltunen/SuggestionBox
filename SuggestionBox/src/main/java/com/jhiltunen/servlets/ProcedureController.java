/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.servlets;

import com.jhiltunen.entity.DatabaseHandler;
import com.jhiltunen.entity.ProcedureBean;
import com.jhiltunen.entity.ProcedureStatus;
import com.jhiltunen.entity.SuggestionBean;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ProcedureController extends HttpServlet {

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

        HttpSession session = request.getSession(false);

        if (session == null) {
            // test if session is still valid
            response.sendRedirect(request.getContextPath());
        } else {
            // get attributes from session
            Integer groupId = (Integer) session.getAttribute("groupId");

            if (groupId == 1) {
                // if the user belongs to normal "User" group
                // then forward to UserController
                request.getRequestDispatcher("UserController").forward(request, response);
            } else if (groupId == 2) {
                // if the user belongs to "Controlgroup" group
                // then allow the user add/edit the procedure
                Integer suggestionId = Integer.parseInt(request.getParameter("suggestionId"));

                SuggestionBean suggestion = databaseHandler.fetchSuggestionById(suggestionId);

                request.setAttribute("suggestion", suggestion);
                request.getRequestDispatcher("/WEB-INF/controlgroup/addsuggestionprocedure.jsp").forward(request, response);
            } else if (groupId == 3) {
                // if the user belongs to "Admin" group
                // then forward to AdminController
                request.getRequestDispatcher("AdminController").forward(request, response);
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

        Integer userId = (Integer) session.getAttribute("userId");

        Integer suggestionID = Integer.parseInt(request.getParameter("suggestionID"));

        ProcedureStatus suggestionProcedure = ProcedureStatus.valueOf(request.getParameter("procedure"));
        String suggestionProcedureDescription = request.getParameter("procedureDescription");

        // create the procedure object
        ProcedureBean procedure = new ProcedureBean();
        procedure.setSuggestionProcedure(suggestionProcedure);
        procedure.setDescription(suggestionProcedureDescription);
        procedure.setDate(sdf.format(date));
        procedure.setUserId(userId);
        procedure.setSuggestionId(suggestionID);

        List<String> errors = new ArrayList<>();

        if (suggestionProcedure == null) {
            errors.add("Error with procedure");
        }

        if (procedure.getDescription().isEmpty()) {
            errors.add("Please fill in the procedure description!");
        }

        if (errors.isEmpty() && databaseHandler.addProcedure(procedure) && databaseHandler.updateSuggestionProcedureStatus(procedure.getSuggestionProcedure(), suggestionID)) {
            request.getRequestDispatcher("ControlGroupController").forward(request, response);
            System.out.println("Lisättiin");
        } else {
            SuggestionBean suggestion = databaseHandler.fetchSuggestionById(suggestionID);

            request.setAttribute("suggestion", suggestion);

            request.setAttribute("errors", errors);
            request.getRequestDispatcher("WEB-INF/controlgroup/addsuggestionprocedure.jsp?id=" + suggestionID).forward(request, response);
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
