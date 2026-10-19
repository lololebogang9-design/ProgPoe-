/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mainpoe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class messageTest {
    
    public messageTest() {
    }

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
}
    

