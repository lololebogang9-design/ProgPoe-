/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mainpoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {
    
    Login member = new Login(
            "Lebo",
            "Student",
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976"
    );
            
    @Test 
    public void testValidUsername() {

        assertTrue(member.checkUserName());
    }
    
    @Test
    public void testInvalidUsername() {

        Login invalidUsername = new Login(
                "Lebo",
                "Student",
                "kyle !!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(invalidUsername.checkUserName());
    }

    
    // PASSWORD TESTS

    @Test
    public void testValidPassword() {

        assertTrue(member.checkPasswordComplexity());
    }

    @Test
    public void testInvalidPassword() {

        Login invalidPassword = new Login(
                "Lebo",
                "Student",
                "kyl_1",
                "password",
                "+27838968976"
        );

        assertFalse(invalidPassword.checkPasswordComplexity());
    }

   
    // CELL PHONE TESTS

    @Test
    public void testValidCellPhoneNumber() {
        Login validCell = new Login(
            "Lebo",
            "Student",
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976");

        assertTrue(validCell.checkCellPhoneNumber());
    }

    @Test
    public void testInvalidCellPhoneNumber() {

        Login invalidCell = new Login(
                "Lebo",
                "Student",
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553"
        );

        assertFalse(invalidCell.checkCellPhoneNumber());
    }

    // LOGIN TESTS

    @Test
    public void testSuccessfulLogin() {

        assertTrue(
                member.loginUser("kyl_1", "Ch&&sec@ke99!")
        );
    }

    @Test
    public void testFailedLogin() {

        assertFalse(
                member.loginUser("wrong", "password")
        );
    }

    
    // REGISTER USER TESTS

    @Test
    public void testSuccessfulRegistration() {

        assertEquals(
                "User registered successfully.",
                member.registerUser()
        );
    }

    @Test
    public void testInvalidUsernameRegistration() {

        Login invalidUsername = new Login(
                "Lebo",
                "Student",
                "kyle !!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertEquals(
                "Username is not correctly formatted; please ensure "
                + "that your username contains an underscore and is no "
                + "more than five characters in length.",
                invalidUsername.registerUser()
        );
    }

    @Test
    public void testInvalidPasswordRegistration() {

        Login invalidPassword = new Login(
                "Lebo",
                "Student",
                "kyl_1",
                "password",
                "+27838968976"
        );

        assertEquals(
                "Password is not correctly formatted; please ensure "
                + "that the password contains at least eight characters, "
                + "a capital letter, a number, and a special character.",
                invalidPassword.registerUser()
        );
    }

    @Test
    public void testInvalidCellPhoneRegistration() {

        Login invalidCell = new Login(
                "Lebo",
                "Student",
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553"
        );

        assertEquals(
                "Cell phone Number incorrectly formatted or does not "
                + "contain international code.",
                invalidCell.registerUser()
        );
    }

    
    // LOGIN STATUS TESTS
    

    @Test
    public void testSuccessfulLoginStatus() {

        boolean result =
                member.loginUser("kyl_1", "Ch&&sec@ke99!");

        assertEquals(
                "Welcome Lebo Student, it is great to see you again.",
                member.returnLoginStatus(result)
        );
    }

    @Test
    public void testFailedLoginStatus() {

        boolean result =
                member.loginUser("wrong", "wrong");

        assertEquals(
                "Username or password incorrect, please try again.",
                member.returnLoginStatus(result)
        );
    }
}
    