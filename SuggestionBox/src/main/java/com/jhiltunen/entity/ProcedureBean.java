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
        if (description.length() <= 1000) {
            this.description = description;
        } else {
            this.description = "";
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        // date is in format 2018-01-05
        // so it has 10 characters
        if (date.length() == 10) {
            this.date = date;
        } else {
            this.date = "";
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
