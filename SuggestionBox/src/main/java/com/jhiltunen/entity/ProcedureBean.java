/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author s1601378
 */
public class ProcedureBean {

    private int procedureId;
    private ProcedureStatus suggestionProcedure;
    private String description;
    private String date;
    private int userId;
    private int suggestionId;

    public int getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public ProcedureStatus getSuggestionProcedure() {
        return suggestionProcedure;
    }

    public void setSuggestionProcedure(ProcedureStatus suggestionProcedure) {
        this.suggestionProcedure = suggestionProcedure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if (date == null) {
            this.date = date;
        } else {
            try {
                // convert string to date object
                SimpleDateFormat mysqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date mysqlDate = mysqlDateFormat.parse(date);

                // convert date object to string with format 1.1.1990
                SimpleDateFormat finnishDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                String result = finnishDateFormat.format(mysqlDate);

                this.date = result;
            } catch (ParseException ex) {

            }
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(int suggestionId) {
        this.suggestionId = suggestionId;
    }
}
