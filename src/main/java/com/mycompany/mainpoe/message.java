/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mainpoe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
/**
 *
 * @author Student
 */
public class message {

    
    public static final String RECIPIENT_OK =
            "Cell phone number successfully captured.";
    public static final String LENGTH_OK = "Message ready to send.";

    public String messageID;
    public int messageNumber;
    public String recipient;
    public String message;
    public String messageHash;

    private static int messageCounter = 0;
    private static int totalMessagesSent = 0;

    
    public static message[] sentMessages = new message[0];
    public static message[] disregardedMessages = new message[0];
    public static message[] storedMessages = new message[0];
    public static String[] messageHashes = new String[0];
    public static String[] messageIDs = new String[0];

    
    private static String storageFile = "stored_messages.json";

    // ======================================
    // CONSTRUCTORS
    // ======================================

    
    public message(String recipient, String message) {
        this(generateMessageID(), recipient, message);
    }

    
    public message(String messageID, String recipient, String message) {
        this.messageID = messageID;
        this.messageNumber = messageCounter;
        messageCounter++;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    private static String generateMessageID() {
        Random random = new Random();
        long id = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        return String.valueOf(id);
    }

    public static void setStorageFile(String fileName) {
        storageFile = fileName;
    }

    // ======================================
    // VALIDATION
    // ======================================

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient.matches("^\\+27[0-9]{9}$")) {
            return RECIPIENT_OK;
        }
        return "Cell phone number is incorrectly formatted or does not "
                + "contain an international code. Please correct the "
                + "number and try again.";
    }

    public String checkMessageLength() {
        if (message.length() <= 250) {
            return LENGTH_OK;
        }
        int extraCharacters = message.length() - 250;
        return "Message exceeds 250 characters by "
                + extraCharacters + "; please reduce the size.";
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
    // ARRAY HELPERS (arrays are grown / shrunk manually)
    // ======================================

    private static message[] appendMessage(message[] array, message m) {
        message[] updated = Arrays.copyOf(array, array.length + 1);
        updated[array.length] = m;
        return updated;
    }

    private static String[] appendString(String[] array, String value) {
        String[] updated = Arrays.copyOf(array, array.length + 1);
        updated[array.length] = value;
        return updated;
    }

    private static message[] removeMessageAt(message[] array, int index) {
        message[] updated = new message[array.length - 1];
        System.arraycopy(array, 0, updated, 0, index);
        System.arraycopy(array, index + 1, updated, index,
                array.length - index - 1);
        return updated;
    }

   
    private static String[] removeValue(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                String[] updated = new String[array.length - 1];
                System.arraycopy(array, 0, updated, 0, i);
                System.arraycopy(array, i + 1, updated, i,
                        array.length - i - 1);
                return updated;
            }
        }
        return array;
    }

    private static void addToParallelArrays(message m) {
        messageIDs = appendString(messageIDs, m.messageID);
        messageHashes = appendString(messageHashes, m.messageHash);
    }

    // ======================================
    // ADD MESSAGE TO THE CORRECT ARRAY
    // ======================================

    public static void addMessage(message newMessage, int option) {

        if (newMessage == null) {
            System.out.println("Message cannot be empty.");
            return;
        }

        if (option == 1) {
            sentMessages = appendMessage(sentMessages, newMessage);
        } else if (option == 2) {
            disregardedMessages =
                    appendMessage(disregardedMessages, newMessage);
        } else if (option == 3) {
            storedMessages = appendMessage(storedMessages, newMessage);
            saveStoredMessages();
        } else {
            System.out.println("Invalid message option.");
            return;
        }

        addToParallelArrays(newMessage);
    }

    // ======================================
    // SEND, DISREGARD OR STORE MESSAGE
    // ======================================

    public String SentMessage(int option) {

        if (option == 1) {
            addMessage(this, 1);
            totalMessagesSent++;
            return "Message successfully sent.";
        } else if (option == 2) {
            addMessage(this, 2);
            return "Press 0 to delete the message.";
        } else if (option == 3) {
            addMessage(this, 3);
            return "Message successfully stored.";
        }
        return "Invalid option. Please try again.";
    }

    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    // ======================================
    // SAVE STORED MESSAGES TO JSON
    // ======================================

    public static void saveStoredMessages() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(storageFile)) {
            gson.toJson(storedMessages, writer);
            System.out.println("Stored messages saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving messages: " + e.getMessage());
        }
    }

    // ======================================
    // LOAD STORED MESSAGES FROM JSON
    // ======================================
    public static void loadStoredMessages() {

        File file = new File(storageFile);

        if (!file.exists() || file.length() == 0) {
            storedMessages = new message[0];
            rebuildParallelArrays();
            System.out.println("There are no stored messages.");
            return;
        }

        try (Reader reader = new FileReader(file)) {

            message[] loaded = new Gson().fromJson(reader, message[].class);
            storedMessages = (loaded == null) ? new message[0] : loaded;

            
            for (message m : storedMessages) {
                messageCounter = Math.max(messageCounter,
                        m.messageNumber + 1);
            }

            rebuildParallelArrays();
            System.out.println("Stored messages loaded successfully.");

        } catch (Exception e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

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

    // ======================================
    // SENT MESSAGES
    // ======================================

    public static ArrayList<String> getSentMessageTexts() {
        ArrayList<String> texts = new ArrayList<>();
        for (message m : sentMessages) {
            texts.add(m.message);
        }
        return texts;
    }

    // ======================================
    // STORED: SENDER AND RECIPIENT
    // ======================================

    public static String displaySenderAndRecipient(String sender) {

        if (storedMessages.length == 0) {
            return "There are no stored messages.";
        }

        StringBuilder text = new StringBuilder();
        for (int i = 0; i < storedMessages.length; i++) {
            if (i > 0) {
                text.append("\n");
            }
            text.append("Sender: ").append(sender)
                .append(" | Recipient: ")
                .append(storedMessages[i].recipient);
        }
        return text.toString();
    }

    // ======================================
    // STORED: LONGEST MESSAGE
    // ======================================

    public static message findLongestMessage() {

        if (storedMessages.length == 0) {
            return null;
        }

        message longest = storedMessages[0];
        for (message m : storedMessages) {
            if (m.message.length() > longest.message.length()) {
                longest = m;
            }
        }
        return longest;
    }

    public static String getLongestMessage() {
        message longest = findLongestMessage();
        if (longest == null) {
            return "There are no stored messages.";
        }
        return longest.message;
    }

    // ======================================
    // SEARCH BY MESSAGE ID (sent + stored messages)
    // ======================================

    public static message findByMessageID(String searchID) {

        for (int i = 0; i < messageIDs.length; i++) {
            if (messageIDs[i].equals(searchID)) {

                for (message m : sentMessages) {
                    if (m.messageID.equals(searchID)) {
                        return m;
                    }
                }
                for (message m : storedMessages) {
                    if (m.messageID.equals(searchID)) {
                        return m;
                    }
                }
            }
        }
        return null;
    }

    public static String searchByMessageID(String searchID) {
        message found = findByMessageID(searchID);
        if (found == null) {
            return "Message ID not found.";
        }
        return found.message;
    }

    // ======================================
    // SEARCH BY RECIPIENT (sent + stored messages)
    // ======================================

    public static ArrayList<String> searchByRecipient(String searchRecipient) {

        ArrayList<String> results = new ArrayList<>();

        for (message m : sentMessages) {
            if (m.recipient.equals(searchRecipient)) {
                results.add(m.message);
            }
        }
        for (message m : storedMessages) {
            if (m.recipient.equals(searchRecipient)) {
                results.add(m.message);
            }
        }
        return results;
    }

    // ======================================
    // DELETE USING MESSAGE HASH (stored messages)
    // ======================================

    public static String deleteByMessageHash(String searchHash) {

        for (int i = 0; i < storedMessages.length; i++) {

            if (storedMessages[i].messageHash.equals(searchHash)) {

                String deletedText = storedMessages[i].message;
                String deletedID = storedMessages[i].messageID;

                storedMessages = removeMessageAt(storedMessages, i);
                messageHashes = removeValue(messageHashes, searchHash);
                messageIDs = removeValue(messageIDs, deletedID);

                saveStoredMessages();

                return "Message: \"" + deletedText
                        + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // ======================================
    // REPORTS
    // ======================================

    private static String describeAll(message[] group) {

        if (group.length == 0) {
            return "None.\n";
        }

        StringBuilder text = new StringBuilder();
        for (message m : group) {
            text.append(m.printMessages())
                .append("\n-------------------------------------------\n");
        }
        return text.toString();
    }

    // Report of the stored messages (menu option)
    public static String generateStoredReport() {
        return "========== STORED MESSAGE REPORT ==========\n"
                + describeAll(storedMessages);
    }

    
    public static String generateReport() {
        return """
               ========== MESSAGE REPORT ==========
               --- SENT MESSAGES ---
               """ + describeAll(sentMessages)
                + "--- STORED MESSAGES ---\n" + describeAll(storedMessages);
    }

    public static void displayStoredReport() {
        System.out.println(generateStoredReport());
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