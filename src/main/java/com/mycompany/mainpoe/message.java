/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainpoe;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
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

    String fileName = "stored_message_" + messageID + ".json";

    try (FileWriter writer = new FileWriter(fileName)) {

        writer.write("{\n");
        writer.write("  \"messageID\": \"" + escapeJSON(messageID) + "\",\n");
        writer.write("  \"messageNumber\": " + messageNumber + ",\n");
        writer.write("  \"messageHash\": \"" + escapeJSON(messageHash) + "\",\n");
        writer.write("  \"recipient\": \"" + escapeJSON(recipient) + "\",\n");
        writer.write("  \"message\": \"" + escapeJSON(message) + "\"\n");
        writer.write("}\n");

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