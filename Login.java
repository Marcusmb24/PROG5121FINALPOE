package com.mycompany.assignmentpoe;

public class Login {
    
    // Constructor
    public Login() {
    }
    
    // Validate Username
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }
    
    // Validate Password Complexity
    public boolean checkPasswordComplexity(String password) {
        String capital = ".*[A-Z].*"; 
        String small = ".*[a-z].*"; 
        String special = ".*[!@#$%^&*(),.?\":{}|<>].*"; 
        String digit = ".*\\d.*"; 
        
        return password.length() >= 8 &&
               password.matches(capital) &&
               password.matches(small) &&
               password.matches(digit) &&
               password.matches (special);
        }
    
    // Validate South African Cell Phone Number
    public boolean checkCellPhoneNumber(String phone) {
        // Prevent runtime errors
        if (phone == null || phone.length() != 12) {
            return false;
        }
        
        // Check international code
        if (!phone.startsWith("+27")) {
            return false;
        }
        
        // Ensure remaining characters are digits
        String remainingDigits = phone.substring(3);
        if (!remainingDigits.matches("\\d{9}")) {
            return false;
        }
        
        // Check network prefix (6–8)
        char fourthDigit = phone.charAt(3);
        return fourthDigit >= '6' && fourthDigit <= '8';
    }
    
    // Register User
    public String registerUser(String username, String password, String phone) {
        boolean validatePhone = checkCellPhoneNumber(phone);
        boolean validateUsername = checkUserName(username);
        boolean validatePassword = checkPasswordComplexity(password);
        
        if (validatePhone && validateUsername && validatePassword) {
            return "User is successfully registered.";
        } else {
            return "User registration failed!!!!!";
        }
    }
    
    // Login User
    public boolean loginUser(String username, String password) {
        return checkUserName(username) && checkPasswordComplexity(password);
    }
    
    // Return Login Status
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "A successful login";
        } else {
            return "A failed login";
        }
    }
}