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

        Scanner input = new Scanner(System.in);

        // ======================================
        // PART 1: REGISTRATION
        // ======================================

        System.out.println("======================================");
        System.out.println("        REGISTRATION AND LOGIN");
        System.out.println("======================================");

        System.out.println("Please enter your first name:");
        String firstName = input.nextLine();

        System.out.println("Please enter your last name:");
        String lastName = input.nextLine();

        System.out.println("Please enter your username:");
        String username = input.nextLine();

        System.out.println("Please enter your password:");
        String password = input.nextLine();

        System.out.println(
                "Please enter your South African cell phone number:"
        );
        String cellPhoneNumber = input.nextLine();

        // Create Login object
        Login login = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        // ======================================
        // PART 1: VALIDATE REGISTRATION DETAILS
        // ======================================

        System.out.println();
        System.out.println("======================================");
        System.out.println("          REGISTRATION");
        System.out.println("======================================");

        boolean usernameValid = login.checkUserName();
        boolean passwordValid = login.checkPasswordComplexity();
        boolean cellPhoneValid = login.checkCellPhoneNumber();

        if (usernameValid) {
            System.out.println("Username successfully captured.");
        } else {
            System.out.println(
                    "Username is not correctly formatted; please ensure "
                    + "that your username contains an underscore and "
                    + "is no more than five characters in length."
            );
        }

        if (passwordValid) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println(
                    "Password is not correctly formatted; please ensure "
                    + "that the password contains at least eight "
                    + "characters, a capital letter, a number, "
                    + "and a special character."
            );
        }

        if (cellPhoneValid) {
            System.out.println("Cell phone number successfully added.");
        } else {
            System.out.println(
                    "Cell phone number incorrectly formatted "
                    + "or does not contain the international code."
            );
        }

        System.out.println();
        System.out.println(login.registerUser());

        // ======================================
        // PART 1: LOGIN
        // ======================================

        System.out.println();
        System.out.println("======================================");
        System.out.println("              LOGIN");
        System.out.println("======================================");

        System.out.println("Please enter your username:");
        String loginUsername = input.nextLine();

        System.out.println("Please enter your password:");
        String loginPassword = input.nextLine();

        boolean loginSuccessful =
                login.loginUser(loginUsername, loginPassword);

        String loginStatus =
                login.returnLoginStatus(loginSuccessful);

        System.out.println();
        System.out.println(loginStatus);

        // ======================================
        // PART 2: QUICKCHAT
        // Only continue if login is successful
        // ======================================

        if (loginSuccessful) {

            System.out.println();
            System.out.println("================================");
            System.out.println("       QUICKCHAT");
            System.out.println("================================");

            System.out.println("Welcome to QuickChat.");

            System.out.println(
                    "How many messages would you like to enter?"
            );

            int numberOfMessages = input.nextInt();
            input.nextLine();

            // Prevent a negative message limit
            while (numberOfMessages < 0) {
                System.out.println(
                        "Please enter zero or a positive number:"
                );
                numberOfMessages = input.nextInt();
                input.nextLine();
            }

            int messageAttempts = 0;

            // ======================================
            // QUICKCHAT MENU
            // ======================================

            while (true) {

                System.out.println();
                System.out.println("================================");
                System.out.println("              MENU");
                System.out.println("================================");
                System.out.println("1. Send Messages");
                System.out.println("2. Show recently sent messages");
                System.out.println("3. Quit");
                System.out.println("Choose an option:");

                int choice = input.nextInt();
                input.nextLine();

                // ======================================
                // OPTION 1: SEND MESSAGE
                // ======================================

                if (choice == 1) {

                    if (messageAttempts >= numberOfMessages) {

                        System.out.println(
                                "You have reached your message limit."
                        );

                    } else {

                        System.out.println(
                                "Enter recipient cell number:"
                        );
                        String recipient = input.nextLine();

                        System.out.println("Enter your message:");
                        String messageText = input.nextLine();

                        // Create message object
                        message newMessage = new message(
                                recipient,
                                messageText
                        );

                        messageAttempts++;

                        // Validate recipient and message length
                        String recipientStatus =
                                newMessage.checkRecipientCell();

                        String messageStatus =
                                newMessage.checkMessageLength();

                        System.out.println(recipientStatus);
                        System.out.println(messageStatus);

                        if (recipientStatus.equals(
                                "Cell phone number successfully captured.")
                                && messageStatus.equals(
                                "Message ready to send.")) {

                            System.out.println(
                                    "Message ID generated: "
                                    + newMessage.messageID
                            );

                            System.out.println(
                                    "Message Hash: "
                                    + newMessage.messageHash
                            );

                            System.out.println(
                                    "Choose what to do with the message:"
                            );
                            System.out.println("1. Send Message");
                            System.out.println("2. Disregard Message");
                            System.out.println(
                                    "3. Store Message to send later"
                            );

                            int messageOption = input.nextInt();
                            input.nextLine();

                            System.out.println(
                                    newMessage.SentMessage(messageOption)
                            );

                            if (messageOption == 1) {
                                System.out.println();
                                System.out.println("Message Details:");
                                System.out.println(
                                        newMessage.printMessages()
                                );
                            }

                        } else {

                            System.out.println(
                                    "Message was not sent. Please check "
                                    + "the recipient number and message."
                            );
                        }

                        System.out.println(
                                "Total messages sent: "
                                + newMessage.returnTotalMessagess()
                        );
                    }

                // ======================================
                // OPTION 2: DISPLAY MESSAGES
                // ======================================

                } else if (choice == 2) {

                    System.out.println("Coming Soon.");

                // ======================================
                // OPTION 3: QUIT
                // ======================================

                } else if (choice == 3) {

                    System.out.println(
                            "Thank you for using QuickChat."
                    );
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
    
