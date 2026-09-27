package com.usermanagement.service;

import com.usermanagement.exception.DuplicateEmailException;
import com.usermanagement.exception.UserNotFoundException;
import com.usermanagement.model.User;
import com.usermanagement.model.UserStatus;
import com.usermanagement.dto.UserDto;
import com.usermanagement.repository.InMemoryUserRepository;
import com.usermanagement.notification.NotificationSender;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

public class UserService {
    
    private NotificationSender notificationSender;
    private InMemoryUserRepository userRepository;

    public UserService(NotificationSender notificationSender, InMemoryUserRepository userRepository) {
        this.notificationSender = notificationSender;
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        // Check if email exists in the repository emails Set, if it does, throw an exception
        if (this.userRepository.getEmails().contains(user.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }
        this.userRepository.save(user);
    }

    public User getUserById(Long id) {
        return this.userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException("User not found")
            );
    }

    public User getUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        return user;
    }

    public void deactivateUser(Long id) {
        User user = this.userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException("User not found")
            );

        user.setStatus(UserStatus.INACTIVE);
        this.userRepository.save(user);
    }

    public void banUser(Long id) {
        User user = this.userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException("User not found")
            );

        user.setStatus(UserStatus.BANNED);
        this.userRepository.save(user);
    }

    public Set<String> getAllEmails() {
        return this.userRepository.getEmails();
    }

    public Set<String> getAllEmailsByDomain(String domain) {
        return this.userRepository.getEmails().stream()
                .filter(email -> email.endsWith("@" + domain))
                .collect(Collectors.toSet());
    }

    public long countActiveUsers() {
        return this.userRepository.findAll().values().stream()
                .filter(user -> user.getStatus() == UserStatus.ACTIVE)
                .count();
    }

    public void sendNotificationToUser(Long id, String message) {
        User user = this.userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException("User not found")
            );

        this.notificationSender.sendNotification(message, user.getEmail());
    }

    public List<UserDto> formatUsersToDtos() {
        return this.userRepository.findAll().values().stream()
                .filter(user -> user.getStatus() == UserStatus.ACTIVE)
                .map(user -> new UserDto(user.getId(), user.getEmail(), user.getName()))
                .collect(Collectors.toList());
    }
}
