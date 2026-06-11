package com.mycompany.assignmentpoe;

public class Message {
    
    private String messageId;
    private String recipientNumber;
    private String messageText;
    private int messageNumber; 
    private String messageStatus; 
    
    private static int totalMessagesSent = 0;

    public Message(int messageNumber, String recipientNumber, String messageText, String messageStatus) {
        this.messageNumber = messageNumber;
        this.recipientNumber = recipientNumber;
        this.messageText = messageText;
        this.messageStatus = messageStatus;
        this.messageId = generateRandomID();
    }
    
    public Message(int messageNumber, String recipientNumber, String messageText) {
        this(messageNumber, recipientNumber, messageText, "Sent");
    }

    private String generateRandomID() {
        java.util.Random rand = new java.util.Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public boolean checkMessageID() {
        return this.messageId != null && this.messageId.length() <= 10;
    }

    public String checkRecipientCell() {
        if (this.recipientNumber == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        
        boolean isValidLength = this.recipientNumber.length() <= 10 || this.recipientNumber.startsWith("+27");
        boolean startsWithValidCode = this.recipientNumber.startsWith("0") || this.recipientNumber.startsWith("+27");

        if (isValidLength && startsWithValidCode) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }
    
    public String validateMessageLength() {
        if (this.messageText == null) {
            return "Message ready to send.";
        }
        
        if (this.messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int overage = this.messageText.length() - 250;
            return "Message exceeds 250 characters by " + overage + "; please reduce the size.";
        }
    }

    public String createMessageHash() {
        if (this.messageId == null || this.messageId.length() < 2 || this.messageText == null || this.messageText.trim().isEmpty()) {
            return "INVALID_HASH";
        }

        String firstTwoID = this.messageId.substring(0, 2);
        String trimmed = this.messageText.trim();
        String[] words = trimmed.split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        firstWord = firstWord.replaceAll("[^a-zA-Z0-9]", "");
        lastWord = lastWord.replaceAll("[^a-zA-Z0-9]", "");

        String hash = firstTwoID + ":" + this.messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    public String SentMessage(int choice) {
        if (choice == 1) {
            totalMessagesSent++;
            this.messageStatus = "Sent";
            return "Message successfully sent.";
        } else if (choice == 2) {
            this.messageStatus = "Disregard";
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            this.messageStatus = "Stored";
            return "Message successfully stored.";
        }
        return "Invalid selection.";
    }

    public String printMessages() {
        return "Message ID: " + this.messageId + "\n" +
               "Message Hash: " + createMessageHash() + "\n" +
               "Recipient: " + this.recipientNumber + "\n" +
               "Message: " + this.messageText;
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
    
    public String getMessageId() { return messageId; }
    public void setMessageId(String id) { this.messageId = id; }
    public String getRecipientNumber() { return recipientNumber; }
    public String getMessageText() { return messageText; }
    public String getMessageStatus() { return messageStatus; }
    public int getMessageNumber() { return messageNumber; }
}