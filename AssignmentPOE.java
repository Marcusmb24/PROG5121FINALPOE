package com.mycompany.assignmentpoe;

import java.util.Scanner;

public class AssignmentPOE {
    
    private static Message[] sentMessagesLog = new Message[100];
    private static Message[] disregardedMessagesLog = new Message[100];
    private static Message[] storedMessagesLog = new Message[100];
    private static String[] messageHashesLog = new String[100];
    private static String[] messageIdsLog = new String[100];
    
    private static int sentCount = 0;
    private static int disregardCount = 0;
    private static int storedCount = 0;
    private static int totalRecordsCount = 0;
    
    public static void SignUp() {
        System.out.println("\n=== SIGN UP PROCESS ===");
    }
    
    public static void SignIn() {
        System.out.println("\n=== SIGN IN PROCESS ===");
    }
    
    public static void logMessageRecord(Message msg, String status) {
        if (totalRecordsCount >= 100) return;
        
        messageIdsLog[totalRecordsCount] = msg.getMessageId();
        messageHashesLog[totalRecordsCount] = msg.createMessageHash();
        totalRecordsCount++;
        
        if (status.equalsIgnoreCase("Sent")) {
            sentMessagesLog[sentCount] = msg;
            sentCount++;
        } else if (status.equalsIgnoreCase("Disregard")) {
            disregardedMessagesLog[disregardCount] = msg;
            disregardCount++;
        } else if (status.equalsIgnoreCase("Stored")) {
            storedMessagesLog[storedCount] = msg;
            storedCount++;
        }
    }
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Login login = new Login(); 
        
        String firstname, lastname, username, password, phone;
        
        System.out.print("Enter First Name: ");
        firstname = input.nextLine();
        
        System.out.print("Enter Last Name: ");
        lastname = input.nextLine();
        
        System.out.print("Enter Username: ");
        username = input.nextLine();
        
        System.out.print("Enter Password: ");
        password = input.nextLine();
        
        System.out.print("Enter Phone Number (+27): ");
        phone = input.nextLine();
        
        boolean validatePhone = login.checkCellPhoneNumber(phone);
        boolean validateUsername = login.checkUserName(username);
        boolean validatePassword = login.checkPasswordComplexity(password);
        
        if (validateUsername) {
            System.out.println("Username successfully captured.");
        } else {
            System.out.println("Username is not correctly formatted...");
        }
        
        if (validatePassword) {
            System.out.println("Password successfully captured.");
        } else {
            System.out.println("Password is not correctly formatted...");
        }
        
        if (validatePhone) {
            System.out.println("Cell phone number successfully added.");
        } else {
            System.out.println("Cell phone number incorrectly formatted.");
        }
        
        SignUp();
        System.out.println(login.registerUser(username, password, phone));
        
        SignIn();
        System.out.print("Enter username to login: ");
        String loginUsername = input.nextLine();
        
        System.out.print("Enter password to login: ");
        String loginPassword = input.nextLine();
        
        String status = login.returnLoginStatus(loginUsername, loginPassword);
        System.out.println(status);
        
        if (status.equals("A successful login")) {
            System.out.println("\nWelcome to QuickChat.");
            
            System.out.print("How many messages do you wish to enter? ");
            int maxMessages = input.nextInt();
            input.nextLine(); 
            
            int currentMessageCount = 0; 
            int menuChoice = 0;
            
            while (menuChoice != 5) {
                System.out.println("\n--- QuickChat Menu ---");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Stored Messages Menu Options");
                System.out.println("4) Run Assignment Rubric Test Suite Simulation");
                System.out.println("5) Quit");
                System.out.print("Select an option: ");
                
                menuChoice = input.nextInt();
                input.nextLine(); 
                
                if (menuChoice == 1) {
                    if (currentMessageCount >= maxMessages) {
                        System.out.println("Reached the maximum set limit of " + maxMessages + " messages.");
                        continue;
                    }
                    
                    System.out.println("\n--- Creating Message " + (currentMessageCount + 1) + " ---");
                    System.out.print("Enter Recipient Cell Number: ");
                    String recipient = input.nextLine();
                    
                    System.out.print("Enter Short Message: ");
                    String msgText = input.nextLine();
                    
                    Message tempMsg = new Message(currentMessageCount, recipient, msgText);
                    System.out.println(tempMsg.checkRecipientCell());
                    
                    String lengthFeedback = tempMsg.validateMessageLength();
                    System.out.println(lengthFeedback);
                    
                    if (lengthFeedback.startsWith("Message exceeds")) {
                        continue; 
                    }
                    
                    System.out.println("Message ID generated: " + tempMsg.getMessageId());
                    System.out.println("Message Hash: " + tempMsg.createMessageHash());
                    
                    System.out.println("\nWhat would you like to do with this message?");
                    System.out.println("1 - Send Message");
                    System.out.println("2 - Disregard Message");
                    System.out.println("3 - Store Message to send later");
                    System.out.print("Choice: ");
                    int actionChoice = input.nextInt();
                    input.nextLine(); 
                    
                    String actionFeedback = tempMsg.SentMessage(actionChoice);
                    System.out.println(actionFeedback);
                    
                    if (actionChoice == 1) {
                        logMessageRecord(tempMsg, "Sent");
                        System.out.println("\n=== Full Details of Sent Message ===");
                        System.out.println(tempMsg.printMessages());
                    } else if (actionChoice == 2) {
                        logMessageRecord(tempMsg, "Disregard");
                        System.out.println("Message deleted.");
                    } else if (actionChoice == 3) {
                        logMessageRecord(tempMsg, "Stored");
                    }
                    
                    currentMessageCount++;
                    
                } else if (menuChoice == 2) {
                    System.out.println("\n=== Recently Sent Messages Log ===");
                    if (sentCount == 0) {
                        System.out.println("No messages have been sent yet.");
                    } else {
                        for (int i = 0; i < sentCount; i++) {
                            System.out.println(sentMessagesLog[i].printMessages());
                            System.out.println("--------------------------------");
                        }
                    }
                } else if (menuChoice == 3) {
                    handleStoredMessagesMenu(input);
                } else if (menuChoice == 4) {
                    simulateRubricTestData();
                } else if (menuChoice == 5) {
                    System.out.println("Exiting QuickChat...");
                } else {
                    System.out.println("Invalid selection. Please try again.");
                }
            }
            
            System.out.println("\n==========================================");
            System.out.println("Total number of messages successfully sent: " + Message.returnTotalMessages());
            System.out.println("==========================================");
            
        } else {
            System.out.println("Access to QuickChat Denied.");
        }
        
        input.close();
    }
    
    private static void handleStoredMessagesMenu(Scanner input) {
        System.out.println("\n==== STORED MESSAGES SUB-MENU ====");
        System.out.println("a) Display sender and recipient of all stored messages");
        System.out.println("b) Display the longest stored message");
        System.out.println("c) Search for a message ID and display tracking details");
        System.out.println("d) Search for all messages stored for a particular recipient");
        System.out.println("e) Delete a message using the unique message hash");
        System.out.println("f) Display a report listing full details of all stored messages");
        System.out.print("Select sub-option (a-f): ");
        String subChoice = input.nextLine().trim().toLowerCase();
        
        switch(subChoice) {
            case "a":
                System.out.println("\n--- Sender & Recipient of Stored Messages ---");
                if (storedCount == 0) {
                    System.out.println("No stored messages available.");
                } else {
                    for (int i = 0; i < storedCount; i++) {
                        System.out.println("Record #" + (i+1) + " -> Sender: Application System | Recipient: " + storedMessagesLog[i].getRecipientNumber());
                    }
                }
                break;
                
            case "b":
                System.out.println("\n--- Longest Stored Message ---");
                if (storedCount == 0) {
                    System.out.println("No stored messages captured to check size.");
                } else {
                    Message longest = storedMessagesLog[0];
                    for (int i = 1; i < storedCount; i++) {
                        if (storedMessagesLog[i].getMessageText().length() > longest.getMessageText().length()) {
                            longest = storedMessagesLog[i];
                        }
                    }
                    System.out.println("Longest Stored Content: \"" + longest.getMessageText() + "\"");
                }
                break;
                
            case "c":
                System.out.print("Enter target Message ID to find: ");
                String searchId = input.nextLine().trim();
                boolean idFound = false;
                for (int i = 0; i < totalRecordsCount; i++) {
                    if (messageIdsLog[i] != null && messageIdsLog[i].equals(searchId)) {
                        Message match = findMessageByIdGlobally(searchId);
                        if (match != null) {
                            System.out.println("\nMatch Located! Target Recipient: " + match.getRecipientNumber() + " | Text Content: \"" + match.getMessageText() + "\"");
                            idFound = true;
                            break;
                        }
                    }
                }
                if (!idFound) System.out.println("Message ID not found in system storage logs.");
                break;
                
            case "d":
                System.out.print("Enter search Recipient Cell Number: ");
                String searchRecipient = input.nextLine().trim();
                boolean recipientFound = false;
                System.out.println("\n--- Filtered Log Records for: " + searchRecipient + " ---");
                for (int i = 0; i < totalRecordsCount; i++) {
                    Message m = findMessageByIndexGlobally(i);
                    if (m != null && m.getRecipientNumber().equals(searchRecipient)) {
                        System.out.println("Status: [" + m.getMessageStatus() + "] | Hash: " + m.createMessageHash() + " | Content: " + m.getMessageText());
                        recipientFound = true;
                    }
                }
                if (!recipientFound) System.out.println("No tracked log data found associated with that recipient code.");
                break;
                
            case "e":
                System.out.print("Enter exact unique Message Hash to purge: ");
                String targetHash = input.nextLine().trim().toUpperCase();
                boolean deleted = deleteMessageByHashGlobally(targetHash);
                if (deleted) {
                    System.out.println("Message: \"" + targetHash + "\" successfully deleted.");
                } else {
                    System.out.println("Target message hash was not found or could not be verified.");
                }
                break;
                
            case "f":
                System.out.println("\n=== ALL REGISTERED STORED MESSAGES REPORT ===");
                if (storedCount == 0) {
                    System.out.println("No files logged into stored tracking arrays.");
                } else {
                    for (int i = 0; i < storedCount; i++) {
                        System.out.println("Hash: " + storedMessagesLog[i].createMessageHash());
                        System.out.println("Recipient: " + storedMessagesLog[i].getRecipientNumber());
                        System.out.println("Message Content: " + storedMessagesLog[i].getMessageText());
                        System.out.println("========================================");
                    }
                }
                break;
                
            default:
                System.out.println("Invalid selection parameter context option.");
        }
    }
    
    private static void simulateRubricTestData() {
        System.out.println("\nPopulating system logs with official assignment rubric test scenarios...");
        
        Message m1 = new Message(0, "+27834557896", "Did you get the cake?", "Sent");
        m1.setMessageId("0123456789"); 
        logMessageRecord(m1, "Sent");
        
        Message m2 = new Message(1, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        m2.setMessageId("0838884567");
        logMessageRecord(m2, "Stored");
        
        Message m3 = new Message(2, "+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        logMessageRecord(m3, "Disregard");
        
        Message m4 = new Message(3, "0838884567", "It is dinner time!", "Sent");
        m4.setMessageId("0838884567");
        logMessageRecord(m4, "Sent");
        
        Message m5 = new Message(4, "+27838884567", "Ok, I am leaving without you.", "Stored");
        logMessageRecord(m5, "Stored");
        
        System.out.println("Parallel verification structures populated successfully! Enter Menu Option 3 to manage.");
    }
    
    private static Message findMessageByIdGlobally(String id) {
        for (int i = 0; i < sentCount; i++) if (sentMessagesLog[i].getMessageId().equals(id)) return sentMessagesLog[i];
        for (int i = 0; i < storedCount; i++) if (storedMessagesLog[i].getMessageId().equals(id)) return storedMessagesLog[i];
        for (int i = 0; i < disregardCount; i++) if (disregardedMessagesLog[i].getMessageId().equals(id)) return disregardedMessagesLog[i];
        return null;
    }
    
    private static Message findMessageByIndexGlobally(String hash) {
        for (int i = 0; i < sentCount; i++) if (sentMessagesLog[i].createMessageHash().equalsIgnoreCase(hash)) return sentMessagesLog[i];
        for (int i = 0; i < storedCount; i++) if (storedMessagesLog[i].createMessageHash().equalsIgnoreCase(hash)) return storedMessagesLog[i];
        for (int i = 0; i < disregardCount; i++) if (disregardedMessagesLog[i].createMessageHash().equalsIgnoreCase(hash)) return disregardedMessagesLog[i];
        return null;
    }
    
    private static Message findMessageByIndexGlobally(int masterIdx) {
        if (masterIdx < 0 || masterIdx >= totalRecordsCount) return null;
        String targetId = messageIdsLog[masterIdx];
        return findMessageByIdGlobally(targetId);
    }
    
    public static boolean deleteMessageByHashGlobally(String hash) {
        for (int i = 0; i < storedCount; i++) {
            if (storedMessagesLog[i].createMessageHash().equalsIgnoreCase(hash)) {
                storedMessagesLog[i] = null;
                for (int j = i; j < storedCount - 1; j++) {
                    storedMessagesLog[j] = storedMessagesLog[j + 1];
                }
                storedMessagesLog[storedCount - 1] = null;
                storedCount--;
                return true;
            }
        }
        return false;
    }
    
    public static int getSentCount() { return sentCount; }
    public static Message[] getSentMessagesLog() { return sentMessagesLog; }
    public static int getStoredCount() { return storedCount; }
    public static Message[] getStoredMessagesLog() { return storedMessagesLog; }
    public static void cleanSystemEnvironment() {
        sentMessagesLog = new Message[100];
        disregardedMessagesLog = new Message[100];
        storedMessagesLog = new Message[100];
        messageHashesLog = new String[100];
        messageIdsLog = new String[100];
        sentCount = 0; disregardCount = 0; storedCount = 0; totalRecordsCount = 0;
    }
}