/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhiltunen.entity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author s1601378
 */
public class ProcedureBeanTest {
    
    private ProcedureBean procedure;
    private ProcedureBean procedure2;
    
    public ProcedureBeanTest() {
    }
    
    @Before
    public void setUp() {
        procedure = new ProcedureBean();
        procedure2 = new ProcedureBean();
    }

    /**
     * Test of setDescription method, of class ProcedureBean. Test that setter method only allows procedure descripotions with maximum number of 1000 characters
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription allows only descriptions with maximum of 1000 characters");
        
        String descriptionWith1001Characters = "";
        String descriptionWith1000Characters = "";
        
        procedure.setDescription(descriptionWith1001Characters);
        procedure2.setDescription(descriptionWith1000Characters);
        
        assertEquals("", procedure.getDescription());
        assertEquals(descriptionWith1000Characters, procedure2.getDescription());
    }

    /**
     * Test of setDate method, of class ProcedureBean. Test that setter method only allows date with maximum number of 10 characters
     */
    @Test
    public void testSetDate() {
        System.out.println("setDate allows only dates with maximum number of 10 characters");
        String creationDate10Characters = "2018-09-05";
        String creationDate11Characters = "2018-09-050";
        
        procedure.setDate(creationDate11Characters);
        procedure2.setDate(creationDate10Characters);
        
        assertEquals("", procedure.getDate());
        assertEquals(creationDate10Characters, procedure2.getDate());
    }
}
