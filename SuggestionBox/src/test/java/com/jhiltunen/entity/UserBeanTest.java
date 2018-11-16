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
public class UserBeanTest {
    
    private UserBean user;
    private UserBean user2;
    
    public UserBeanTest() {
    }

    @Before
    public void setUp() {
        user = new UserBean();
        user2 = new UserBean();
    }
    
    /**
     * Test of setFirstname method, of class UserBean. Test that setter only allows firtsnames with maximum number of 30 characters
     */
    @Test
    public void testSetFirstname() {
        System.out.println("setFirstname allows only firstnames with maximum length of 30 characters");
        String firstnameWith31Letters = "Loremipsumdolorsitametconsectet";
        String firstnameWith30Letters = "Loremipsumdolorsitametconsecte";
        
        user.setFirstname(firstnameWith31Letters);
        user2.setFirstname(firstnameWith30Letters);
        
        assertEquals("", user.getFirstname());
        assertEquals(firstnameWith30Letters, user2.getFirstname());
    }

    /**
     * Test of setLastname method, of class UserBean. Test that setter only allows lastnames with maximum number of 30 characters
     */
    @Test
    public void testSetLastname() {
        System.out.println("setLastname allows only lastnames with maximum number of 30 characters");
        String lastnameWith31Letters = "Loremipsumdolorsitametconsectet";
        String lastnameWith30Letters = "Loremipsumdolorsitametconsecte";
        
        user.setLastname(lastnameWith31Letters);
        user2.setLastname(lastnameWith30Letters);
        
        assertEquals("", user.getLastname());
        assertEquals(lastnameWith30Letters, user2.getLastname());
    }

    /**
     * Test of setEmail method, of class UserBean. Test that setter method only allows emails with maximum number of 60 characters
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail allows only emails with maximum number of 60 characters");
        String emailWith60Characters = "testemailaddress.testersoftwares@testingsoftwares.tested.com";
        String emailWith62Characters = "testingmailaddress.testersoftwares@testingsoftwares.tested.com";
        
        user.setEmail(emailWith62Characters);
        user2.setEmail(emailWith60Characters);
        
        assertEquals("", user.getEmail());
        assertEquals(emailWith60Characters, user2.getEmail());
    }

    /**
     * Test of setUsername method, of class UserBean. Test that setter method only allows usernames with maximum number of 10 characters
     */
    @Test
    public void testSetUsername() {
        System.out.println("setUsername allows only usernames with maximum number of 10 characters");
        String usernameWith10Characters = "mistylakes";
        String usernameWith11Characters = "testinguser";
        
        user.setUsername(usernameWith11Characters);
        user2.setUsername(usernameWith10Characters);
        
        assertEquals("", user.getUsername());
        assertEquals(usernameWith10Characters, user2.getUsername());
    }

    /**
     * Test of setPassword method, of class UserBean. Test that setter method only allows passwords with maximum number of 60 characters
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword allows only passwors with maximum number of 60 characters");
        String passwordWith60Characters = "b:cDF%daeBUkHD,#k?YL9U@?jc[J=h2pp+8]DGk\"f!M[(^:VRX]GC3!Tr(3)";
        String passwordWith61Characters = "b:cDF%daeBUkHD,#k?YL9U@?jc[J=h2pp+8]DGk\"f!M[(^:VRX]GC3!Tr(3)?";
        
        user.setPassword(passwordWith61Characters);
        user2.setPassword(passwordWith60Characters);
        
        assertEquals("", user.getPassword());
        assertEquals(passwordWith60Characters, user2.getPassword());
    }

    /**
     * Test of setPhone method, of class UserBean. Test that setter method only allows phonenumbers with maximum number of 15 characters
     */
    @Test
    public void testSetPhone() {
        System.out.println("setPhone allows only phonenumbers with maximum number of 15 characters");
        String phoneWith15Characters = "+35800201456397";
        String phoneWith16Characters = "+358002014563978";
        
        user.setPhone(phoneWith16Characters);
        user2.setPhone(phoneWith15Characters);
        
        assertEquals("", user.getPhone());
        assertEquals(phoneWith15Characters, user2.getPhone());
    }

    /**
     * Test of setCreationDate method, of class UserBean. Test that setter method only allows creationdate with maximum number of 10 characters
     */
    @Test
    public void testSetCreationDate() {
        System.out.println("setCreationDate allows only creationdates with maximum number of 10 characters");
        String creationDate10Characters = "2018-09-05";
        String creationDate11Characters = "2018-09-050";
        
        user.setCreationDate(creationDate11Characters);
        user2.setCreationDate(creationDate10Characters);
        
        assertEquals("", user.getCreationDate());
        assertEquals(creationDate10Characters, user2.getCreationDate());
    }    
}
