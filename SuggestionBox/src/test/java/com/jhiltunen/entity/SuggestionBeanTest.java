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
public class SuggestionBeanTest {
    
    private SuggestionBean suggestion;
    private SuggestionBean suggestion2;
    
    public SuggestionBeanTest() {
    }
    
    @Before
    public void setUp() {
        suggestion = new SuggestionBean();
        suggestion2 = new SuggestionBean();
    }

    /**
     * Test of setTitle method, of class SuggestionBean. Test that setter method only allows titles with maximum number of 255 characters
     */
    @Test
    public void testSetTitle() {
        System.out.println("setTitle allows only titles with maximum number of 255 characters");
        
        String titleWith255Characters = "Sed suscipit ac purus quis dictum. Phasellus rutrum elit leo, nec mattis ligula laoreet ut. Praesent eros purus, convallis in tortor ac, eleifend placerat magna. Duis porttitor erat quam, in gravida eros tincidunt nec. Cras et nibh magna. Ut sodales, just";
        String titleWith256Characters = "Sed suscipit ac purus quis dictum. Phasellus rutrum elit leo, nec mattis ligula laoreet ut. Praesent eros purus, convallis in tortor ac, eleifend placerat magna. Duis porttitor erat quam, in gravida eros tincidunt nec. Cras et nibh magna. Ut sodales, justä";
        
        suggestion.setTitle(titleWith256Characters);
        suggestion2.setTitle(titleWith255Characters);
        
        assertEquals("", suggestion.getTitle());
        assertEquals(titleWith255Characters, suggestion2.getTitle());
    }

    /**
     * Test of setCreationDate method, of class SuggestionBean. Test that setter method only allows creationdate with maximum number of 10 characters
     */
    @Test
    public void testSetCreationDate() {
        System.out.println("setCreationDate allows only creationdates with maximum number of 10 characters");
        String creationDate10Characters = "2018-09-05";
        String creationDate11Characters = "2018-09-050";
        
        suggestion.setCreationDate(creationDate11Characters);
        suggestion2.setCreationDate(creationDate10Characters);
        
        assertEquals("", suggestion.getCreationDate());
        assertEquals(creationDate10Characters, suggestion2.getCreationDate());
    }
}
