/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.servlets;

import com.jhiltunen.entity.DatabaseHandler;
import com.jhiltunen.entity.Status;
import com.jhiltunen.entity.SuggestionBean;
import com.jhiltunen.entity.UserBean;
import java.io.IOException;
import java.util.ArrayList;
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
public class EditSuggestionController extends HttpServlet {

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

        HttpSession session = request.getSession(false);

        // make sure that use is logged in
        if (session == null || session.getAttribute("groupId") == null) {
            response.sendRedirect(request.getContextPath());
        } else {
            Integer groupId = (Integer) session.getAttribute("groupId");
            Integer userId = (Integer) session.getAttribute("userId");
            Integer suggestionId = Integer.parseInt(request.getParameter("suggestionId"));

            SuggestionBean suggestion = databaseHandler.fetchSuggestionById(suggestionId);

            // make sure that user belongs to correct group
            // only users that belong to normal User group or Admin group can edit users
            if (groupId == 1) {
                if (suggestion == null) {
                    request.getRequestDispatcher("UserController").forward(request, response);
                } else {
                    if (userId == suggestion.getUserID()) {
                        // make sure that user (who belongs to normal User group) is only allowed to edit his/her own suggestions
                        request.setAttribute("suggestion", suggestion);
                        request.getRequestDispatcher("/WEB-INF/user/editsuggestion.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("UserController").forward(request, response);
                    }
                }
            } else if (groupId == 2) {
                request.getRequestDispatcher("ControlGroupController").forward(request, response);
            } else if (groupId == 3) {
                if (suggestion == null) {
                    request.getRequestDispatcher("AdminController").forward(request, response);
                } else {
                    // admin users can edit all suggestions
                    request.setAttribute("suggestion", suggestion);
                    request.getRequestDispatcher("/WEB-INF/admin/editsuggestion.jsp").forward(request, response);
                }
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

        int userIdFromSession = (int) session.getAttribute("userId");
        int groupId = (int) session.getAttribute("groupId");

        int suggestionId = Integer.parseInt(request.getParameter("suggestionId"));
        String suggestionTitle = request.getParameter("suggestionTitle");
        String suggestionDescription = request.getParameter("suggestionDescription");
        String suggestionCreationDate = request.getParameter("suggestionCreationDate");
        int userId = Integer.parseInt(request.getParameter("userId"));
        String suggestionStatus = request.getParameter("status");

        SuggestionBean suggestion = new SuggestionBean();

        suggestion.setId(suggestionId);
        suggestion.setTitle(suggestionTitle);
        suggestion.setDescription(suggestionDescription);
        suggestion.setCreationDate(suggestionCreationDate);
        suggestion.setStatus(Status.valueOf(suggestionStatus));

        // check for errors before adding anything to database
        List<String> errors = new ArrayList<>();

        if (suggestion.getTitle().isEmpty()) {
            errors.add("Please fill in Suggestion title");
        }

        if (suggestion.getDescription().isEmpty()) {
            errors.add("Please fill in Suggestion description");
        }

        if (groupId == 1) {
            // in case of user edits his/her own suggestion
            // get the userId from HttpSession and set it as the userId of the suggestion
            // this is done to prevent user able to edit any user's suggestion
            suggestion.setUserID(userIdFromSession);
            if (errors.isEmpty() && databaseHandler.updateSuggestionByUserId(suggestion)) {
                request.getRequestDispatcher("UserController").forward(request, response);
            } else {
                // updating suggestion fails
                request.setAttribute("errors", errors);
                suggestion = databaseHandler.fetchSuggestionById(suggestion.getId());

                request.setAttribute("suggestion", suggestion);
                request.getRequestDispatcher("WEB-INF/user/editsuggestion.jsp").forward(request, response);
            }
        } else if (groupId == 3) {
            // admin user edits suggestions
            // admin users are able to edit any suggestion
            // get user id from the form
            suggestion.setUserID(userId);
            if (errors.isEmpty() && databaseHandler.updateSuggestion(suggestion)) {
                request.getRequestDispatcher("AdminController").forward(request, response);
            } else {
                // updating suggestion fails
                request.setAttribute("errors", errors);
                suggestion = databaseHandler.fetchSuggestionById(suggestion.getId());

                request.setAttribute("suggestion", suggestion);
                request.getRequestDispatcher("WEB-INF/admin/editsuggestion.jsp").forward(request, response);
            }
        }

    }
}
