/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.entity;

import java.io.Serializable;

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
        if (username.length() <= 10) {
            this.username = username;
        }
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
        if (title.length() <= 255) {
            this.title = title;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.length() <= 1600000) {
            this.description = description;
        }
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        // creationdate is in format 2018-01-05
        // so it has 10 characters
        if (creationDate.length() == 10) {
            this.creationDate = creationDate;
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
