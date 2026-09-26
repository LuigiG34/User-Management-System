package com.usermanagement.notification;

public interface NotificationSender {
    public void sendNotification(String message, String recipientEmail);
}
