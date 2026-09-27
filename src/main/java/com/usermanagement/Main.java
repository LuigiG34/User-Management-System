package com.usermanagement;

import com.usermanagement.repository.InMemoryUserRepository;
import com.usermanagement.model.PremiumUser;
import com.usermanagement.model.User;
import com.usermanagement.notification.ConsoleNotificationSender;
import com.usermanagement.service.UserService;
import com.usermanagement.dto.UserDto;
import com.usermanagement.exception.DuplicateEmailException;
import com.usermanagement.exception.UserNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the User Management System!");

        // Init InMemoryUserRepository and ConsoleNotificationSender
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        ConsoleNotificationSender notificationSender = new ConsoleNotificationSender();

        // Init UserService
        UserService userService = new UserService(notificationSender, userRepository);

        // Create 8 users
        User user1 = new User(1L, "John Doe", "johndoe@gmail.com");
        User user2 = new User(2L, "Jane Doe", "janedoe@gmail.com");
        User user3 = new User(3L, "Patric Smith", "patric@standard.com");
        User user4 = new User(4L, "Patricia Smith", "patricia.smith@yahoo.com");
        User user5 = new User(5L, "Jake Peters", "j.peters@outlook.com");
        User user6 = new User(6L, "Maria Peters", "m.peters@outlook.com");
        PremiumUser premiumUser1 = new PremiumUser(7L, "Nicolas Prem", "nicolas@premium.com", 3);
        PremiumUser premiumUser2 = new PremiumUser(8L, "Francesco Ium", "francesco@gmail.com", 5);

        // Save them in repository
        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);
        userService.createUser(user4);
        userService.createUser(user5);
        userService.createUser(user6);
        userService.createUser(premiumUser1);
        userService.createUser(premiumUser2);

        // Deactivate 2 accounts
        userService.deactivateUser(user3.getId());
        userService.deactivateUser(user4.getId());

        // Ban 1 account
        userService.banUser(user1.getId());

        // Get a user with his ID
        User userById = userService.getUserById(user6.getId());
        System.out.println("Fetched: "+userById.getName()+" / "+userById.getEmail());

        // Recuperate active users as DTOs
        List<UserDto> users = userService.formatUsersToDtos();
        System.out.println("Fetched all users as DTOs: " + users);

        // Display all emails and then only the "@gmail" ones seperatly
        Set<String> emails = userService.getAllEmails();
        System.out.println("Fetched all user emails: " + emails);
        
        Set<String> gmailEmails = userService.getAllEmailsByDomain("gmail.com");
        System.out.println("Fetched all Gmail user emails: " + gmailEmails);

        // Count the active users and present them in email alphabetical order
        long countActiveUsers = userService.countActiveUsers();
        List<UserDto> alphabeticalActiveUsers = users.stream()
                        .sorted(Comparator.comparing(user -> user.email()))
                        .toList();
        System.out.println("Amount of active user : " + countActiveUsers);
        System.out.println("Sorted by email address : " + alphabeticalActiveUsers);

        // Send notification to an active user
        userService.sendNotificationToUser(userById.getId(), "You are an active user.");

        // Create a user with the same email to trigger Exception
        try {
            User userSameEmailException = new User(9L, "John Doe", "johndoe@gmail.com");
            userService.createUser(userSameEmailException);
        } catch (DuplicateEmailException e) {
            System.out.println(e.getMessage());
        }

        // Search for a user without a real ID to trigger Exception
        try {
            User userByIdException = userService.getUserById(28L);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Delete a user and make sure he disappears from repository
        try {
            userService.deleteUserById(5L);
            System.out.println(userService.getUserById(5L));
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}