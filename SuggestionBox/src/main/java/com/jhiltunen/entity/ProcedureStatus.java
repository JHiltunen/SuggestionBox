/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.entity;

/**
 *
 * @author s1601378
 */
public enum ProcedureStatus {
    NOPROCEDURE("No procedure"),
    WAITINGDECISION("Waiting for the decision"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected");

    private String displayName;

    ProcedureStatus(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
