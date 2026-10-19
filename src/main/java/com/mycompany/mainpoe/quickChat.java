/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainpoe;
import java.util.Scanner;
/**
 *
 * @author Student
 */
public class quickChat {
      public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("       QUICKCHAT LOGIN");
        System.out.println("================================");

        // Registration details from Part 1
        System.out.println("Please enter your first name:");
        String firstName = input.nextLine();

        System.out.println("Please enter your last name:");
        String lastName = input.nextLine();

        System.out.println("Please enter your username:");
        String username = input.nextLine();

        System.out.println("Please enter your password:");
        String password = input.nextLine();

        System.out.println("Please enter your cell phone number:");
        String cellPhone = input.nextLine();

        Login login = new Login(
                firstName, lastName, username, password, cellPhone
        );
        
        
        

        System.out.println(login.registerUser());

        // Login
        System.out.println("\nPlease log in.");

        System.out.println("Enter your username:");
        String enteredUsername = input.nextLine();

        System.out.println("Enter your password:");
        String enteredPassword = input.nextLine();

        boolean loginSuccessful =
                login.loginUser(enteredUsername, enteredPassword);

        System.out.println(login.returnLoginStatus(loginSuccessful));

        if (loginSuccessful) {

            System.out.println("\nWelcome to QuickChat.");

            System.out.println("How many messages would you like to enter?");
            int numberOfMessages = input.nextInt();
            input.nextLine();

            int messageAttempts = 0;

            while (true) {

                System.out.println("\n================================");
                System.out.println("              MENU");
                System.out.println("================================");
                System.out.println("1. Send Messages");
                System.out.println("2. Show recently sent messages");
                System.out.println("3. Quit");
                System.out.println("Choose an option:");

                int choice = input.nextInt();
                input.nextLine();

                if (choice == 1) {

                    if (messageAttempts >= numberOfMessages) {

                        System.out.println(
                                "You have reached your message limit."
                        );

                    } else {

                        System.out.println("Enter recipient cell number:");
                        String recipient = input.nextLine();

                        System.out.println("Enter your message:");
                        String messageText = input.nextLine();

                        message message = new message(
                                recipient, messageText
                        );

                        messageAttempts++;

                        System.out.println(message.checkRecipientCell());
                        System.out.println(message.checkMessageLength());

                        if (message.checkRecipientCell().equals(
                                "Cell phone number successfully captured.")
                                && message.checkMessageLength().equals(
                                "Message ready to send.")) {

                            System.out.println(
                                    "Message ID generated: "
                                    + message.messageID
                            );

                            System.out.println(
                                    "Message Hash: " + message.messageHash
                            );

                      System.out.println("Choose what to do with the message:");
                      System.out.println("1. Send Message");
                      System.out.println("2. Disregard Message");
                      System.out.println("3. Store Message to send later");
                      int messageOption = input.nextInt();
                      input.nextLine();
                      
                      System.out.println(message.SentMessage(messageOption));
                      if (messageOption == 1) {
                          System.out.println("\nMessage Details:");
                          System.out.println(message.printMessages());
}

                        } else {

                            System.out.println(
                                    "Message was not sent. Please check "
                                    + "the recipient number and message."
                            );
                        }

                        System.out.println(
                                "Total messages sent: "
                                + message.returnTotalMessagess()
                        );
                    }

                } else if (choice == 2) {

                    System.out.println("Coming Soon.");

                } else if (choice == 3) {

                    System.out.println("Thank you for using QuickChat.");
                    break;

                } else {

                    System.out.println(
                            "Invalid option. Please select 1, 2 or 3."
                    );
                }
            }

        } else {

            System.out.println(
                    "You must log in successfully before using QuickChat."
            );
        }

        input.close();
       
    }
}

