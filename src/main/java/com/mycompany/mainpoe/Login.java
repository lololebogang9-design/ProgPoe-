/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainpoe;

/**
 *
 * @author Student
 */
public class Login {
    
    // Attributes
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public String cellPhoneNumber;

    // Constructor
    public Login(String firstName, String lastName, String username,
            String password, String cellPhoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Checking if the username is correctly formatted
    public boolean checkUserName() {

        return username.contains("_") && username.length() <= 5;
    }

    // Checking if the password meets the complexity requirements
    public boolean checkPasswordComplexity() {

        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    // Checking if the cell phone number is correctly formatted
    public boolean checkCellPhoneNumber() {

        
        return cellPhoneNumber.startsWith("+27") && 
                cellPhoneNumber.length()==12;
    }

    // Registering the user
    public String registerUser() {

        if (!checkUserName()) {

            return "Username is not correctly formatted; please ensure that "
                    + "your username contains an underscore and is no more "
                    + "than five characters in length.";

        } else if (!checkPasswordComplexity()) {

            return "Password is not correctly formatted; please ensure that "
                    + "the password contains at least eight characters, a "
                    + "capital letter, a number, and a special character.";

        } else if (!checkCellPhoneNumber()) {

            return "Cell phone Number incorrectly formatted or does not "
                    + "contain international code.";

        } else {

            return "User registered successfully.";
        }
    }

    // Checking if the login details are correct
    public boolean loginUser(String enteredUsername, String enteredPassword) {

        return enteredUsername.equals(username)
                && enteredPassword.equals(password);
    }
    

    public String returnLoginStatus(boolean loginSuccessful) {

        if (loginSuccessful) {

            return "Welcome " + firstName + " " + lastName
                    + ", it is great to see you again.";

        } else {

            return "Username or password incorrect, please try again.";
        }
    }
}
    

