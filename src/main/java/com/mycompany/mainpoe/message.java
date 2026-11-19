/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainpoe;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Arrays;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

 
    // THE FIVE ARRAYS REQUIRED FOR PART 3
   

    public static message[] sentMessages = new message[0];
    public static message[] disregardedMessages = new message[0];
    public static message[] storedMessages = new message[0];

    public static String[] messageHashes = new String[0];
    public static String[] messageIDs = new String[0];

    private static final String FILE_NAME = "stored_messages.json";

    
    // CONSTRUCTOR
   

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

    public boolean checkMessageID() {

        return messageID.length() <= 10;
    }

    // ======================================
    // CHECK RECIPIENT CELL NUMBER
    // ======================================

    public String checkRecipientCell() {

        if (recipient.matches("^\\+27[0-9]{9}$")) {

            return "Cell phone number successfully captured.";

        } else {

            return "Cell phone number is incorrectly formatted or does not "
                    + "contain an international code. Please correct the "
                    + "number and try again.";
        }
    }

    // ======================================
    // CHECK MESSAGE LENGTH
    // ======================================

    public String checkMessageLength() {

        if (message.length() <= 250) {

            return "Message ready to send.";

        } else {

            int extraCharacters = message.length() - 250;

            return "Message exceeds 250 characters by "
                    + extraCharacters + "; please reduce the size.";
        }
    }

    // ======================================
    // CREATE MESSAGE HASH
    // ======================================

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

    // ======================================
    // ADD MESSAGE TO THE CORRECT ARRAY
    // ======================================

    public static void addMessage(message newMessage, int option) {

        if (newMessage == null) {
            System.out.println("Message cannot be empty.");
            return;
        }

        if (option < 1 || option > 3) {
            System.out.println("Invalid message option.");
            return;
        }

        if (option == 1) {

            sentMessages = Arrays.copyOf(
                    sentMessages, sentMessages.length + 1
            );

            sentMessages[sentMessages.length - 1] = newMessage;

        } else if (option == 2) {

            disregardedMessages = Arrays.copyOf(
                    disregardedMessages, disregardedMessages.length + 1
            );

            disregardedMessages[disregardedMessages.length - 1] = newMessage;

        } else if (option == 3) {

            storedMessages = Arrays.copyOf(
                    storedMessages, storedMessages.length + 1
            );

            storedMessages[storedMessages.length - 1] = newMessage;

            saveStoredMessages();
        }

        // Add message ID to parallel array
        messageIDs = Arrays.copyOf(
                messageIDs, messageIDs.length + 1
        );

        messageIDs[messageIDs.length - 1] = newMessage.messageID;

        // Add message hash to parallel array
        messageHashes = Arrays.copyOf(
                messageHashes, messageHashes.length + 1
        );

        messageHashes[messageHashes.length - 1] = newMessage.messageHash;
    }

    // ======================================
    // SEND, DISREGARD OR STORE MESSAGE
    // ======================================

    public String SentMessage() {

        Scanner input = new Scanner(System.in);

        System.out.println("Choose what to do with the message:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        int option = input.nextInt();

        return SentMessage(option);
    }

    public String SentMessage(int option) {

        if (option == 1) {

            addMessage(this, 1);

            totalMessagesSent++;

            return "Message successfully sent.";

        } else if (option == 2) {

            addMessage(this, 2);

            return "Message disregarded.";

        } else if (option == 3) {

            addMessage(this, 3);

            return "Message successfully stored.";

        } else {

            return "Invalid option. Please try again.";
        }
    }

    // ======================================
    // DISPLAY MESSAGE DETAILS
    // ======================================

    public String printMessages() {

        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    // ======================================
    // RETURN TOTAL MESSAGES SENT
    // ======================================

    public int returnTotalMessagess() {

        return totalMessagesSent;
    }

    // ======================================
    // SAVE STORED MESSAGES TO JSON
    // ======================================

    public static void saveStoredMessages() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(FILE_NAME)) {

            gson.toJson(storedMessages, writer);

            System.out.println("Stored messages saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving messages: " + e.getMessage());
        }
    }

    // ======================================
    // REBUILD PARALLEL ARRAYS
    // ======================================

    private static void rebuildParallelArrays() {

        messageIDs = new String[0];
        messageHashes = new String[0];

        for (message m : sentMessages) {
            addToParallelArrays(m);
        }

        for (message m : disregardedMessages) {
            addToParallelArrays(m);
        }

        for (message m : storedMessages) {
            addToParallelArrays(m);
        }
    }

    private static void addToParallelArrays(message m) {

        messageIDs = Arrays.copyOf(
                messageIDs, messageIDs.length + 1
        );

        messageIDs[messageIDs.length - 1] = m.messageID;

        messageHashes = Arrays.copyOf(
                messageHashes, messageHashes.length + 1
        );

        messageHashes[messageHashes.length - 1] = m.messageHash;
    }

    // ======================================
    // LOAD STORED MESSAGES FROM JSON
    // ======================================

    public static void loadStoredMessages() {

        File file = new File(FILE_NAME);

        if (!file.exists() || file.length() == 0) {

            storedMessages = new message[0];

            rebuildParallelArrays();

            System.out.println("There are no stored messages.");
            return;
        }

        Gson gson = new Gson();

        try (Scanner reader = new Scanner(file)) {

            StringBuilder json = new StringBuilder();

            while (reader.hasNextLine()) {
                json.append(reader.nextLine());
            }

            message[] loadedMessages =
                    gson.fromJson(json.toString(), message[].class);

            if (loadedMessages == null) {

                storedMessages = new message[0];

            } else {

                storedMessages = loadedMessages;
            }

            rebuildParallelArrays();

            System.out.println("Stored messages loaded successfully.");

        } catch (Exception e) {

            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    // ======================================
    // DISPLAY STORED SENDERS AND RECIPIENTS
    // ======================================

    public static void displayStoredSendersAndRecipients(String sender) {

        if (storedMessages.length == 0) {

            System.out.println("There are no stored messages.");
            return;
        }

        for (message m : storedMessages) {

            System.out.println("Sender: " + sender);
            System.out.println("Recipient: " + m.recipient);
            System.out.println("-------------------------");
        }
    }

    // ======================================
    // FIND LONGEST STORED MESSAGE
    // ======================================

    public static String longestStoredMessage() {

        if (storedMessages.length == 0) {

            return "There are no stored messages.";
        }

        message longest = storedMessages[0];

        for (message m : storedMessages) {

            if (m.message.length() > longest.message.length()) {

                longest = m;
            }
        }

        return longest.message;
    }

    // ======================================
    // SEARCH MESSAGE USING MESSAGE ID
    // ======================================

    public static String searchByMessageID(String searchID) {

        for (int i = 0; i < messageIDs.length; i++) {

            if (messageIDs[i].equals(searchID)) {

                for (message m : storedMessages) {

                    if (m.messageID.equals(searchID)) {

                        return "Recipient: " + m.recipient
                                + "\nMessage: " + m.message;
                    }
                }
            }
        }

        return "Message ID not found.";
    }

    // ======================================
    // SEARCH MESSAGES BY RECIPIENT
    // ======================================

    public static void searchByRecipient(String searchRecipient) {

        boolean found = false;

        for (message m : storedMessages) {

            if (m.recipient.equalsIgnoreCase(searchRecipient)) {

                System.out.println("Message: " + m.message);

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No stored messages found for this recipient."
            );
        }
    }

    // ======================================
    // DELETE MESSAGE USING MESSAGE HASH
    // ======================================

    public static String deleteByHash(String searchHash) {

        for (int i = 0; i < storedMessages.length; i++) {

            if (storedMessages[i].messageHash.equals(searchHash)) {

                String deletedMessage = storedMessages[i].message;
                String deletedID = storedMessages[i].messageID;

                message[] updatedMessages =
                        new message[storedMessages.length - 1];

                int position = 0;

                for (int j = 0; j < storedMessages.length; j++) {

                    if (j != i) {

                        updatedMessages[position] = storedMessages[j];
                        position++;
                    }
                }

                storedMessages = updatedMessages;

                // Remove matching hash
                for (int j = 0; j < messageHashes.length; j++) {

                    if (messageHashes[j].equals(searchHash)) {

                        messageHashes = removeStringAt(messageHashes, j);
                        break;
                    }
                }

                // Remove matching ID
                for (int j = 0; j < messageIDs.length; j++) {

                    if (messageIDs[j].equals(deletedID)) {

                        messageIDs = removeStringAt(messageIDs, j);
                        break;
                    }
                }

                saveStoredMessages();

                return "Message: \"" + deletedMessage
                        + "\" successfully deleted.";
            }
        }

        return "Message hash not found.";
    }

    // Helper method to remove an item from a String array
    private static String[] removeStringAt(String[] array, int index) {

        String[] updatedArray = new String[array.length - 1];

        System.arraycopy(array, 0, updatedArray, 0, index);

        System.arraycopy(
                array, index + 1,
                updatedArray, index,
                array.length - index - 1
        );

        return updatedArray;
    }

    // ======================================
    // DISPLAY STORED MESSAGE REPORT
    // ======================================

    public static void displayStoredReport() {

        if (storedMessages.length == 0) {

            System.out.println("There are no stored messages.");
            return;
        }

        System.out.println(
                "========== STORED MESSAGE REPORT =========="
        );

        for (message m : storedMessages) {

            System.out.println("Message ID: " + m.messageID);
            System.out.println("Message Hash: " + m.messageHash);
            System.out.println("Recipient: " + m.recipient);
            System.out.println("Message: " + m.message);

            System.out.println("-------------------------------------------");
        }
    }

    

    public static void resetTotals() {

        messageCounter = 0;
        totalMessagesSent = 0;

        sentMessages = new message[0];
        disregardedMessages = new message[0];
        storedMessages = new message[0];

        messageHashes = new String[0];
        messageIDs = new String[0];
    }
}
