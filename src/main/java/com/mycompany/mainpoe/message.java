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
    
    private static int messageCounter 0; 
    private static int totalMessageSent = 0;
    private static String sentMessages = "";
    
    public message (String recipient, String message){
        Random random = new Random();
        long id = 1000000000L + (long)
                (random.nextDouble() * 9000000000L);
        this.messageID = String.valueOf(id);
        this.messageNumber = messageCounter; 
        messageCounter++;
        this.recipient = recipient;
        this.message = message;
        
        this messageHash = cresateMessageHash();
        
    }
    public boolean checkMessageID() {
        return messageID.length() <= 10;
        
    }
    public String checkRecipientCell() {
        if(recipient.matches("^\\+27[0-9](9)$")) {
            return "Cell phone number successfully captured. ";
        } else {
            return "Cell phone number is incorectlly formated or does not "
                    + "contain an international code. Please correct the "
                    + "number and try again.";
            
        }
    }
    public String checkMessageLength() {
        if (message.length()<=250){
            return "Message ready to send.";
        }else{
            int extraCharacters = message.length() - 250;
            
            return "Message exceeds 250 characters be"
                    + extraCharacters +"Please reduce the size. ";
                    
        } 
        
    }
    public String createMessageHash(){
        String firstTwoDigits = messageID.substring(0, 2);
        String trimmedMessage = message.trim();
         int firstSpace = trimmedMessage.indexOf("");
                 int lastSpace = trimmedMessage.lastIndexOf("");
                         
                   String firstWord;
                   String lastWord;
                   
                   if (firstSpace == -1){
                       firstWord = trimmedMessage;
                       lastWord = trimmedMessage; 
                   } else {
                       firstWord = trimmedMessage.substring(0, firstSpace);
                       lastWord = trimmedMessage.substring(lastSpace + 1);
                   }
                   firstWord = firstWord.replaceAll("[^a-zA-Z0-9]", "");
                   lastWord = lastWord.replaceAll("[^a-zA-Z0-9"," ");
                   
                return (firstTwoDigits + ":" messageNumber + ":"
                        + firstWord + lastWord).toUpperCase();
                        
                
    }
    
    public String sentMessage() {
        Scanner input =new Scanner(System.in);
        System.out.println("Choose what to do with the message:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send Later");
        
        int option = input.nextInt();
        return sentMessage(option);
        
        
    }
    public String sentMessage(int option ){
        if (option== 1){
            totalMessagesSent ++; 
            return "Message successfully sent";
            
        } else if (option ==2){
            return "Press 0 to delete the message.";
            
        }else if (option ==3 ){
            storeMessage();
            return "Message successfully Stored.";
            
        }else {
            return "Invalid option. Please try again.";
            
        }
        
    }
    public String printMessage(){
        return "message ID:"+ messageID
                + "\nMessage Hash:"+ messageHash
                +"\nMessage:"+ message;
    }
    
}
