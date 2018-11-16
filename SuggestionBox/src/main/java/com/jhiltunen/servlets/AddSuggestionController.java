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
import javax.servlet.http.HttpSession;

/**
 *
 * @author s1601378
 */
public class AddSuggestionController extends HttpServlet {

    private DatabaseHandler databaseHandler = new DatabaseHandler();

    // get the current date
    private Date date = new Date();

    // create new SimpleDateFormat
    // because the creation date of the suggestion needs to be changed to the following pattern
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

        // make sure that user is logged in
        if (session == null || session.getAttribute("groupId") == null) {
            response.sendRedirect(request.getContextPath());
        } else {
            Integer groupId = (Integer) session.getAttribute("groupId");

            // make sure that user belongs to correct group
            // only users that belong to User group are allowed to make new suggestions
            if (groupId == 1) {
                request.getRequestDispatcher("/WEB-INF/user/newsuggestion.jsp").forward(request, response);
            } else if (groupId == 2) {
                request.getRequestDispatcher("ControlGroupController").forward(request, response);
            } else if (groupId == 3) {
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
        String suggestionName = request.getParameter("suggestionTitle");
        String suggestionDescription = request.getParameter("suggestionDescription");

        HttpSession session = request.getSession();

        Integer userId = (Integer) session.getAttribute("userId");

        // create object from the information user filled in to the form
        SuggestionBean suggestionBean = new SuggestionBean();

        ProcedureBean procedure = new ProcedureBean();
        procedure.setSuggestionProcedure(ProcedureStatus.NOPROCEDURE);

        suggestionBean.setTitle(suggestionName);
        suggestionBean.setDescription(suggestionDescription);
        suggestionBean.setCreationDate(sdf.format(date));
        suggestionBean.setUserID(userId);
        suggestionBean.setProcedure(procedure);

        List<String> errors = new ArrayList<>();

        if (suggestionBean.getTitle().isEmpty()) {
            errors.add("Suggestion name is invalid or missing");
        }

        if (suggestionBean.getDescription().isEmpty()) {
            errors.add("Suggestion description is invalid or missing");
        }

        if (errors.isEmpty() && databaseHandler.addSuggestion(suggestionBean)) {
            request.getRequestDispatcher("UserController").forward(request, response);
        } else {
            // Adding suggestion fails
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/user/newsuggestion.jsp").forward(request, response);
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
