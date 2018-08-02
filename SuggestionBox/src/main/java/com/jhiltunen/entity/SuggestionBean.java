/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.entity;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author s1601378
 */
public class SuggestionBean implements Serializable {

    private int userID;
    private String username;
    private int id;
    private String title;
    private String description;
    private String creationDate;
    private Status status;
    private ProcedureBean procedure;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        try {
            // convert string to date object
            SimpleDateFormat mysqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = mysqlDateFormat.parse(creationDate);
            
            // convert date object to string with format 1.1.1990
            SimpleDateFormat finnishDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String result = finnishDateFormat.format(date);
            
            this.creationDate = result;
        } catch (ParseException ex) {
            
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ProcedureBean getProcedure() {
        return procedure;
    }

    public void setProcedure(ProcedureBean procedure) {
        this.procedure = procedure;
    }
}
