/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainpoe;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
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
package com.mycompany.mainpoe;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
/**
 *
 * @author Student
 */
public class message {
    
   public String messageID;
    public int messageNumber;
    public String recipient;
    public String message;
    public String messageHash;

    private static int messageCounter = 0;
    private static int totalMessagesSent = 0;
    private static String sentMessages =""; 

    // Constructor
    public message(String recipient, String message) {

        Random random = new Random();

        long id = 1000000000L
                + (long) (random.nextDouble() * 9000000000L);

        this.messageID = String.valueOf(id);
        this.messageNumber = messageCounter;
        messageCounter++;

        this.recipient = recipient;
        this.message = message;

        this.messageHash = createMessageHash();
    }

    // Check that the message ID has no more than 10 characters
    public boolean checkMessageID() {

        return messageID.length() <= 10;
    }

    // Check the South African international cell number
    public String checkRecipientCell() {

        if (recipient.matches("^\\+27[0-9]{9}$")) {

            return "Cell phone number successfully captured.";

        } else {

            return "Cell phone number is incorrectly formatted or does not "
                    + "contain an international code. Please correct the "
                    + "number and try again.";
        }
    }

    // Check if the message is within the character limit
    public String checkMessageLength() {

        if (message.length() <= 250) {

            return "Message ready to send.";

        } else {

            int extraCharacters = message.length() - 250;

            return "Message exceeds 250 characters by "
                    + extraCharacters + "; please reduce the size.";
        }
    }

    // Create the message hash
    public String createMessageHash() {
        String firstTwoDigits = messageID.substring(0, 2);
         String trimmedMessage = message.trim();
          int firstSpace = trimmedMessage.indexOf(" ");
          int lastSpace = trimmedMessage.lastIndexOf(" ");
          
          String firstWord;
          String lastWord;
          
           if (firstSpace == -1) {
        firstWord = trimmedMessage;
        lastWord = trimmedMessage;
    } else {
        firstWord = trimmedMessage.substring(0, firstSpace);
        lastWord = trimmedMessage.substring(lastSpace + 1);
    }
        firstWord = firstWord.replaceAll("[^a-zA-Z0-9]", "");
    lastWord = lastWord.replaceAll("[^a-zA-Z0-9]", "");

    return (firstTwoDigits + ":" + messageNumber + ":"
            + firstWord + lastWord).toUpperCase();

       
    }
    

    // Allow the user to send, disregard or store the message
    public String SentMessage() {

        Scanner input = new Scanner(System.in);

        System.out.println("Choose what to do with the message:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        int option = input.nextInt();

        return SentMessage(option);
    }

    // This version makes the method easier to test
    public String SentMessage(int option) {

        if (option == 1) {

            totalMessagesSent++;

            return "Message successfully sent.";

        } else if (option == 2) {

            return "Press 0 to delete the message.";

        } else if (option == 3) {

            storeMessage();

            return "Message successfully stored.";

        } else {

            return "Invalid option. Please try again.";
        }
    }

    // Display the full details of the message
    public String printMessages() {

        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    // Return the total number of messages successfully sent
    public int returnTotalMessagess() {

        return totalMessagesSent;
    }

    // Store the message in a JSON file without using arrays
    
public boolean storeMessage() {

    String fileName = "stored_messages_" + ".json";
    String existingMessages = "";

    try {
        File file = new File(fileName);

        if (file.exists() && file.length() > 0) {
            existingMessages = new String(
                Files.readAllBytes(Paths.get(fileName))
            ).trim();
        }

        String newMessage =
                "  {\n"
                + "    \"messageID\": \"" + escapeJSON(messageID) + "\",\n"
                + "    \"messageNumber\": " + messageNumber + ",\n"
                + "    \"messageHash\": \"" + escapeJSON(messageHash) + "\",\n"
                + "    \"recipient\": \"" + escapeJSON(recipient) + "\",\n"
                + "    \"message\": \"" + escapeJSON(message) + "\"\n"
                + "  }";

        if (existingMessages.isEmpty()
                || existingMessages.equals("[]")) {

            existingMessages = "[\n" + newMessage + "\n]";

        } else if (existingMessages.startsWith("[")
                && existingMessages.endsWith("]")) {

            existingMessages = existingMessages.substring(
                    0, existingMessages.length() - 1
            );

            existingMessages += ",\n" + newMessage + "\n]";

        } else {
            System.out.println(
                    "The JSON file has an invalid format."
            );
            return false;
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(existingMessages);
        }

        System.out.println("Message stored in " + fileName);
        return true;

    } catch (IOException e) {
        System.out.println("Error storing message: " + e.getMessage());
        return false;
    }


    }

    private String escapeJSON(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // For unit tests
    public static void resetTotals() {
        messageCounter = 0;
        totalMessagesSent = 0;
        sentMessages = "";
    }
}
