/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author s1601378
 */
public class UserBean implements Serializable {
    
    private int userID;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String phone;
    private String creationDate;
    private int groupID;
    private Status status;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if (firstname.length() <= 30) {
            this.firstname = firstname;
        }
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        if (lastname.length() <= 30) {
            this.lastname = lastname;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.length() <= 60) {
            this.email = email;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username.length() <= 10) {
            this.username = username;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() <= 60) {
            this.password = password;
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone.length() <= 15) {
            this.phone = phone;
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

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }    
}
