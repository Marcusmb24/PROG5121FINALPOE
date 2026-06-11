package com.mycompany.assignmentpoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    
    @BeforeEach
    public void setupRubricEnvironment() {
        AssignmentPOE.cleanSystemEnvironment();
        
        Message m1 = new Message(0, "+27834557896", "Did you get the cake?", "Sent");
        m1.setMessageId("0123456789");
        AssignmentPOE.logMessageRecord(m1, "Sent");
        
        Message m2 = new Message(1, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        m2.setMessageId("0838884567");
        AssignmentPOE.logMessageRecord(m2, "Stored");
        
        Message m3 = new Message(2, "+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        AssignmentPOE.logMessageRecord(m3, "Disregard");
        
        Message m4 = new Message(3, "0838884567", "It is dinner time!", "Sent");
        m4.setMessageId("0838884567");
        AssignmentPOE.logMessageRecord(m4, "Sent");
    }

    @Test
    public void testCheckRecipientCell_Valid() {
        Message instance = new Message(0, "+2718693002", "Hi Mike, can you join us for dinner tonight?");
        String expResult = "Cell phone number successfully captured.";
        String result = instance.checkRecipientCell();
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckRecipientCell_Invalid() {
        Message instance = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        String expResult = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        String result = instance.checkRecipientCell();
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateMessageLength_Valid() {
        Message instance = new Message(0, "+2718693002", "Hi Mike, can you join us for dinner tonight?");
        String expResult = "Message ready to send.";
        String result = instance.validateMessageLength();
        assertEquals(expResult, result);
    }

    @Test
    public void testCreateMessageHash() {
        Message instance = new Message(0, "+2718693002", "Hi Mike, can you join us for dinner tonight?");
        String generatedId = instance.getMessageId();
        String idPrefix = generatedId.substring(0, 2);
        String expectedHash = idPrefix + ":0:HITONIGHT";
        String result = instance.createMessageHash();
        assertEquals(expectedHash, result);
    }

    @Test
    public void testSentMessagesArrayPopulation() {
        Message[] sentLog = AssignmentPOE.getSentMessagesLog();
        int activeSentSize = AssignmentPOE.getSentCount();
        assertEquals(2, activeSentSize);
        assertEquals("Did you get the cake?", sentLog[0].getMessageText());
        assertEquals("It is dinner time!", sentLog[1].getMessageText());
    }

    @Test
    public void testDisplayLongestMessage() {
        Message[] storedLog = AssignmentPOE.getStoredMessagesLog();
        int activeStoredSize = AssignmentPOE.getStoredCount();
        assertTrue(activeStoredSize > 0);
        Message longest = storedLog[0];
        for (int i = 1; i < activeStoredSize; i++) {
            if (storedLog[i].getMessageText().length() > longest.getMessageText().length()) {
                longest = storedLog[i];
            }
        }
        String expectedLongestText = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expectedLongestText, longest.getMessageText());
    }

    @Test
    public void testSearchForMessageID() {
        String targetSearchId = "0838884567";
        Message match = null;
        for(int i = 0; i < AssignmentPOE.getSentCount(); i++) {
            if(AssignmentPOE.getSentMessagesLog()[i].getMessageId().equals(targetSearchId)) {
                match = AssignmentPOE.getSentMessagesLog()[i];
                break;
            }
        }
        assertNotNull(match);
        assertEquals("It is dinner time!", match.getMessageText());
    }

    @Test
    public void testDeleteMessageUsingHash() {
        Message[] storedLog = AssignmentPOE.getStoredMessagesLog();
        String targetDeleteHash = storedLog[0].createMessageHash(); 
        boolean executionOutcome = AssignmentPOE.deleteMessageByHashGlobally(targetDeleteHash);
        assertTrue(executionOutcome);
    }
}