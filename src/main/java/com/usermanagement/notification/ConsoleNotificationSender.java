package com.usermanagement.notification;

public class ConsoleNotificationSender implements NotificationSender {

    @Override
    public void sendNotification(String message, String recipientEmail) {
        System.out.println("Notification sent to " + recipientEmail + ": " + message);
    }
    
}
