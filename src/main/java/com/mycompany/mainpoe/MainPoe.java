/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mainpoe;
import java.util.Scanner;


/**
 *
 * @author Student
 */
public class MainPoe {

    public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        
        System.out.println("======================================");
            System.out.println("        REGISTRATION AND LOGIN");
            System.out.println("======================================");
            
            // Getting the user's information name
            System.out.println("Please enter your first name:");
            String firstName = input.nextLine();
            
            
            System.out.println("Please enter your last name:");
            String lastName = input.nextLine();
            
            
            System.out.println("Please enter your Username:");
            String username = input.nextLine();
            
            
            System.out.println("Please enter your Password:");
            String password = input.nextLine();
            
            
            System.out.println("Please enter your South African cell phone number:");
            String cellPhoneNumber = input.nextLine();
            
            // Creating an instance of the Login class
            Login login = new Login(
                    firstName,
                    lastName,
                    username,
                    password,
                    cellPhoneNumber
            );
            
            System.out.println();
            System.out.println("======================================");
            System.out.println("          REGISTRATION");
            System.out.println("======================================");
            
            // Checking the username
            if (login.checkUserName()) {
                
                System.out.println("Username Successfully captured.");
                
            } else {
                
                System.out.println("Username is not correctly formatted; "
                        + "please ensure that your username contains an "
                        + "underscore and is no more than five characters "
                        + "in length.");
            }
            
            // Checking the password
            if (login.checkPasswordComplexity()) {
                
                System.out.println("Password successfully captured.");
                
            } else {
                
                System.out.println("Password is not correctly formatted; "
                        + "please ensure that the password contains at least "
                        + "eight characters, a capital letter, a number, "
                        + "and a special character.");
            }
            
            
            if (login.checkCellPhoneNumber()) {
                
                System.out.println("Cell phone number successfully added.");
                
            } else {
                
                System.out.println("Cell phone Number incorrectly formatted "
                        + "or does not contain international code.");
            }
            
            // Register the user
            System.out.println();
            System.out.println(login.registerUser());
            
            // Login section
            System.out.println();
            System.out.println("======================================");
            System.out.println("              LOGIN");
            System.out.println("======================================");
            
            System.out.println("Please enter your Username:");
            String loginUsername = input.nextLine();
            
            System.out.println("Please enter your Password:");
            String loginPassword = input.nextLine();
            
            
            boolean loginSuccessful =
                    login.loginUser(loginUsername, loginPassword);
            
           
            String loginStatus =
                    login.returnLoginStatus(loginSuccessful);
            
            System.out.println();
            System.out.println(loginStatus);
        }
    }

    
