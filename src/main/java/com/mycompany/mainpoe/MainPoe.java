/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mainpoe;
import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author Studentlebogang
 * 
 */
public class MainPoe {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);


        // --- REGISTRATION ----------
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
        System.out.println("Please enter your South African cell phone number:");
        String cellPhoneNumber = input.nextLine();

        Login login = new Login(firstName, lastName, username,
                password, cellPhoneNumber);

        System.out.println();
        System.out.println("======================================");
        System.out.println("          REGISTRATION");
        System.out.println("======================================");

        String registrationResult = login.registerUser();
        System.out.println(registrationResult);

        
        if (!registrationResult.equals("User registered successfully.")) {
            System.out.println("Please restart and register again.");
            input.close();
            return;
        }

        // ----------  LOGIN ----------
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

        System.out.println();
        System.out.println(login.returnLoginStatus(loginSuccessful));

        
        if (loginSuccessful) {
            runQuickChat(input, login.firstName + " " + login.lastName);
        } else {
            System.out.println(
                    "You must log in successfully before using QuickChat.");
        }

        input.close();
    }

    // ======================================
    // QUICKCHAT MAIN MENU
    // ======================================

    private static void runQuickChat(Scanner input, String sender) {

        System.out.println();
        System.out.println("================================");
        System.out.println("            QUICKCHAT");
        System.out.println("================================");
        System.out.println("Welcome to QuickChat.");

       
        message.loadStoredMessages();

        int limit = readInt(input,
                "How many messages would you like to enter?");
        while (limit < 0) {
            limit = readInt(input, "Please enter zero or a positive number:");
        }

        int messagesEntered = 0;
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("================================");
            System.out.println("              MENU");
            System.out.println("================================");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Stored Messages");
            System.out.println("4. Quit");

            int choice = readInt(input, "Choose an option:");

            switch (choice) {
                case 1:
                    messagesEntered =
                            sendMessage(input, messagesEntered, limit);
                    break;
                case 2:
                    showSentMessages();
                    break;
                case 3:
                    storedMessagesMenu(input, sender);
                    break;
                case 4:
                    System.out.println("Thank you for using QuickChat.");
                    running = false;
                    break;
                default:
                    System.out.println(
                            "Invalid option. Please select 1, 2, 3 or 4.");
            }
        }
    }

    // ======================================
    // OPTION 1: SEND MESSAGE
    // ======================================

    private static int sendMessage(Scanner input, int entered, int limit) {

        if (entered >= limit) {
            System.out.println("You have reached your message limit.");
            return entered;
        }

        System.out.println("Enter recipient cell number:");
        String recipient = input.nextLine();
        System.out.println("Enter your message:");
        String text = input.nextLine();

        message newMessage = new message(recipient, text);

        String recipientStatus = newMessage.checkRecipientCell();
        String lengthStatus = newMessage.checkMessageLength();

        System.out.println(recipientStatus);
        System.out.println(lengthStatus);

        if (!recipientStatus.equals(message.RECIPIENT_OK)
                || !lengthStatus.equals(message.LENGTH_OK)) {
            System.out.println("Message was not sent. Please check "
                    + "the recipient number and message.");
            return entered;
        }

        System.out.println("Message ID generated: " + newMessage.messageID);
        System.out.println("Message Hash: " + newMessage.messageHash);
        System.out.println("Choose what to do with the message:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        int option = readInt(input, "Enter 1, 2 or 3:");
        System.out.println(newMessage.SentMessage(option));

        if (option == 1) {
            System.out.println();
            System.out.println("Message Details:");
            System.out.println(newMessage.printMessages());
        }

        System.out.println("Total messages sent: "
                + newMessage.returnTotalMessages());

        
        if (option >= 1 && option <= 3) {
            return entered + 1;
        }
        return entered;
    }

    // ======================================
    // OPTION 2: SHOW RECENTLY SENT MESSAGES
    // ======================================

    private static void showSentMessages() {

        System.out.println("Recently sent messages:");

        if (message.sentMessages.length == 0) {
            System.out.println("No recently sent messages.");
            return;
        }

        for (message m : message.sentMessages) {
            System.out.println(m.printMessages());
            System.out.println("-------------------------");
        }
    }

    // ======================================
    // OPTION 3: STORED MESSAGES MENU
    // ======================================

    private static void storedMessagesMenu(Scanner input, String sender) {

        boolean inMenu = true;

        while (inMenu) {

            System.out.println();
            System.out.println("====== STORED MESSAGES ======");
            System.out.println("1. Display senders and recipients");
            System.out.println("2. Display longest stored message");
            System.out.println("3. Search by message ID");
            System.out.println("4. Search by recipient");
            System.out.println("5. Delete message using hash");
            System.out.println("6. Display stored message report");
            System.out.println("7. Back to main menu");

            int option = readInt(input, "Choose an option:");

            switch (option) {
                case 1:
                    System.out.println(
                            message.displaySenderAndRecipient(sender));
                    break;

                case 2:
                    System.out.println(message.getLongestMessage());
                    break;

                case 3:
                    System.out.println("Enter message ID:");
                    message found =
                            message.findByMessageID(input.nextLine().trim());
                    if (found == null) {
                        System.out.println("Message ID not found.");
                    } else {
                        System.out.println("Recipient: " + found.recipient);
                        System.out.println("Message: " + found.message);
                    }
                    break;

                case 4:
                    System.out.println("Enter recipient number:");
                    ArrayList<String> results =
                            message.searchByRecipient(input.nextLine().trim());
                    if (results.isEmpty()) {
                        System.out.println(
                                "No messages found for this recipient.");
                    } else {
                        for (String text : results) {
                            System.out.println("Message: " + text);
                        }
                    }
                    break;

                case 5:
                    System.out.println("Enter message hash:");
                    System.out.println(message.deleteByMessageHash(
                            input.nextLine().trim().toUpperCase()));
                    break;

                case 6:
                    message.displayStoredReport();
                    break;

                case 7:
                    inMenu = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ======================================
    // SAFE INTEGER INPUT (stops crashes on letters)
    // ======================================

    private static int readInt(Scanner input, String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
}

    


    
