/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainpoe;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 *
 * @author Student
 */
public class MessageManager {
    
    // The five arrays required for Part 3
    public static message[] sentMessages = new message[0];
    public static message[] disregardedMessages = new message[0];
    public static message[] storedMessages = new message[0];

    public static String[] messageHashes = new String[0];
    public static String[] messageIDs = new String[0];

    private static final String FILE_NAME = "stored_messages.json";

    // Add a message to the correct array
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

        // Add the message ID to the parallel array
        messageIDs = Arrays.copyOf(
            messageIDs, messageIDs.length + 1
        );

        messageIDs[messageIDs.length - 1] = newMessage.messageID;

        // Add the message hash to the parallel array
        messageHashes = Arrays.copyOf(
            messageHashes, messageHashes.length + 1
        );

        messageHashes[messageHashes.length - 1] = newMessage.messageHash;
    }

    // Save all stored messages to one JSON file
    public static void saveStoredMessages() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(FILE_NAME)) {

            gson.toJson(storedMessages, writer);

            System.out.println("Stored messages saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving messages: " + e.getMessage());
        }
    }

    // Rebuild the ID and hash arrays without duplicates
    private static void rebuildParallelArrays() {

        messageIDs = new String[0];
        messageHashes = new String[0];

        // Add IDs and hashes from sent messages
        for (message m : sentMessages) {
            addToParallelArrays(m);
        }

        // Add IDs and hashes from disregarded messages
        for (message m : disregardedMessages) {
            addToParallelArrays(m);
        }

        // Add IDs and hashes from stored messages
        for (message m : storedMessages) {
            addToParallelArrays(m);
        }
    }

    // Helper method to populate the parallel arrays
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

    // Read stored messages from the JSON file into an array
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

            // Rebuild the parallel arrays after loading JSON
            rebuildParallelArrays();

            System.out.println("Stored messages loaded successfully.");

            // Display the loaded messages to the user
            displayStoredReport();

        } catch (Exception e) {

            System.out.println(
                "Error reading JSON file: " + e.getMessage()
            );
        }
    }

    // Display sender and recipient of stored messages
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

    // Find the longest stored message
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

    // Search for a message using its ID
    public static String searchByMessageID(String searchID) {

        // First search the parallel ID array
        for (int i = 0; i < messageIDs.length; i++) {

            if (messageIDs[i].equals(searchID)) {

                // Find the matching stored message
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

    // Search for all stored messages belonging to a recipient
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

    // Delete a stored message using its hash
    public static String deleteByHash(String searchHash) {

        for (int i = 0; i < storedMessages.length; i++) {

            if (storedMessages[i].messageHash.equals(searchHash)) {

                String deletedMessage = storedMessages[i].message;
                String deletedID = storedMessages[i].messageID;

                // Remove the message from the stored array
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

                // Remove the matching hash from the parallel array
                int hashIndex = -1;

                for (int j = 0; j < messageHashes.length; j++) {

                    if (messageHashes[j].equals(searchHash)) {

                        hashIndex = j;
                        break;
                    }
                }

                if (hashIndex != -1) {

                    String[] updatedHashes =
                        new String[messageHashes.length - 1];

                    System.arraycopy(
                        messageHashes, 0,
                        updatedHashes, 0, hashIndex
                    );

                    System.arraycopy(
                        messageHashes, hashIndex + 1,
                        updatedHashes, hashIndex,
                        messageHashes.length - hashIndex - 1
                    );

                    messageHashes = updatedHashes;
                }

                // Remove the matching ID from the parallel array
                int idIndex = -1;

                for (int j = 0; j < messageIDs.length; j++) {

                    if (messageIDs[j].equals(deletedID)) {

                        idIndex = j;
                        break;
                    }
                }

                if (idIndex != -1) {

                    String[] updatedIDs =
                        new String[messageIDs.length - 1];

                    System.arraycopy(
                        messageIDs, 0,
                        updatedIDs, 0, idIndex
                    );

                    System.arraycopy(
                        messageIDs, idIndex + 1,
                        updatedIDs, idIndex,
                        messageIDs.length - idIndex - 1
                    );

                    messageIDs = updatedIDs;
                }

                // Save the updated stored messages to JSON
                saveStoredMessages();

                return "Message: \"" + deletedMessage
                    + "\" successfully deleted.";
            }
        }

        return "Message hash not found.";
    }

    // Display the full report of stored messages
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

            System.out.println(
                "-------------------------------------------"
            );
        }
    }
}


