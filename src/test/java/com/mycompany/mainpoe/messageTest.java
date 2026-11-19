/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mainpoe;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Student
 */
public class messageTest {
   

    private static final String TEST_FILE = "test_stored_messages.json";

    public messageTest() {
    }

    // Loads the five POE test messages before every test and uses a
    // separate JSON file so the real stored_messages.json is not touched.
    @BeforeEach
    public void setUp() {
        message.resetTotals();
        message.setStorageFile(TEST_FILE);

        message m1 = new message("1000000001", "+27834557896",
                "Did you get the cake?");
        message m2 = new message("1000000002", "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        message m3 = new message("1000000003", "+27834484567",
                "Yohoooo, I am at your gate.");
        message m4 = new message("0838884567", "0838884567",
                "It is dinner time!");
        message m5 = new message("1000000005", "+27838884567",
                "Ok, I am leaving without you.");

        m1.SentMessage(1);   // Sent
        m2.SentMessage(3);   // Stored
        m3.SentMessage(2);   // Disregard
        m4.SentMessage(1);   // Sent
        m5.SentMessage(3);   // Stored
    }

    @AfterEach
    public void tearDown() {
        new File(TEST_FILE).delete();
        message.resetTotals();
    }

    // ======================================
    // PART 1 AND 2 TESTS (unchanged)
    // ======================================

    @Test
    public void testMessageID() {

        message message = new message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertTrue(message.checkMessageID());
        assertEquals(10, message.messageID.length());
    }

    @Test
    public void testValidRecipient() {

        message message = new message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testInvalidRecipient() {

        message message = new message(
                "08575975889",
                "Hi Keegan, did you receive the payment?"
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not "
                + "contain an international code. Please correct the "
                + "number and try again.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testValidMessageLength() {

        message message = new message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageExceeds250Characters() {

        String longMessage = "";

        for (int i = 0; i < 251; i++) {
            longMessage = longMessage + "a";
        }

        message message = new message(
                "+27718693002",
                longMessage
        );

        assertEquals(
                "Message exceeds 250 characters by 1; please reduce the size.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageHash() {

        message message = new message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                message.messageID.substring(0, 2) + ":"
                + message.messageNumber + ":HITONIGHT",
                message.createMessageHash()
        );
    }

    @Test
    public void testSendMessage() {

        message message = new message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Message successfully sent.",
                message.SentMessage(1)
        );
    }

    @Test
    public void testDisregardMessage() {

        message message = new message(
                "+27718693002",
                "Hi Keegan, did you receive the payment?"
        );

        assertEquals(
                "Press 0 to delete the message.",
                message.SentMessage(2)
        );
    }

    @Test
    public void testStoreMessage() {

        message message = new message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Message successfully stored.",
                message.SentMessage(3)
        );
    }

    // ======================================
    // PART 3 TESTS (required by the brief)
    // ======================================

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(
                "Did you get the cake?",
                "It is dinner time!"
        ));

        assertEquals(expected, message.getSentMessageTexts());
    }

    @Test
    public void testDisplayLongestMessage() {
        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                message.getLongestMessage()
        );
    }

    @Test
    public void testSearchForMessageID() {
        assertEquals(
                "It is dinner time!",
                message.searchByMessageID("0838884567")
        );
    }

    @Test
    public void testSearchMessagesForRecipient() {
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(
                "Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."
        ));

        assertEquals(
                expected,
                message.searchByRecipient("+27838884567")
        );
    }

    @Test
    public void testDeleteMessageUsingHash() {
        String hashOfMessage2 = message.messageHashes[1];

        assertEquals(
                "Message: \"Where are you? You are late! I have asked "
                + "you to be on time.\" successfully deleted.",
                message.deleteByMessageHash(hashOfMessage2)
        );

        assertEquals(4, message.messageHashes.length);
        assertEquals(1, message.storedMessages.length);
    }

    @Test
    public void testDisplayReport() {
        String report = message.generateReport();

        assertTrue(report.contains("Message Hash: 10:0:DIDCAKE"));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));

        assertTrue(report.contains("Message Hash: 08:3:ITTIME"));
        assertTrue(report.contains("Recipient: 0838884567"));
        assertTrue(report.contains("Message: It is dinner time!"));

        assertFalse(report.contains("Yohoooo"));
    }

    // ======================================
    // PART 3 EXTRA TESTS
    // ======================================

    @Test
    public void testArraysPopulatedWithCorrectCounts() {
        assertEquals(2, message.sentMessages.length);
        assertEquals(2, message.storedMessages.length);
        assertEquals(1, message.disregardedMessages.length);
        assertEquals(5, message.messageHashes.length);
        assertEquals(5, message.messageIDs.length);
    }

    @Test
    public void testLongestMessageDetails() {
        message longest = message.findLongestMessage();

        assertEquals("+27838884567", longest.recipient);
        assertEquals("1000000002", longest.messageID);
    }

    @Test
    public void testSearchForMessageIDNotFound() {
        assertEquals(
                "Message ID not found.",
                message.searchByMessageID("9999999999")
        );
    }

    @Test
    public void testSearchRecipientWithNoMessages() {
        assertEquals(
                0,
                message.searchByRecipient("+27111111111").size()
        );
    }

    @Test
    public void testDisregardedMessageNotReturnedForRecipient() {
        assertEquals(
                0,
                message.searchByRecipient("+27834484567").size()
        );
    }

    @Test
    public void testDeleteMessageHashNotFound() {
        assertEquals(
                "Message hash not found.",
                message.deleteByMessageHash("00:0:NOTHING")
        );
    }

    @Test
    public void testSenderAndRecipientOfStoredMessages() {
        assertEquals(
                "Sender: Test User | Recipient: +27838884567\n"
                + "Sender: Test User | Recipient: +27838884567",
                message.displaySenderAndRecipient("Test User")
        );
    }

    @Test
    public void testStoredMessagesReadBackFromJson() {
        message.loadStoredMessages();   // reads the JSON file into the array

        assertEquals(2, message.storedMessages.length);
        assertEquals("+27838884567", message.storedMessages[0].recipient);
        assertEquals("Ok, I am leaving without you.",
                message.storedMessages[1].message);
        assertEquals(5, message.messageHashes.length);
    }
}
    

