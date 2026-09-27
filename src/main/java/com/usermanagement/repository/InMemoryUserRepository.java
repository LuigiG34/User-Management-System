package com.usermanagement.repository;

import com.usermanagement.model.User;
import com.usermanagement.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

public class InMemoryUserRepository implements UserRepository {
    
    private Map<Long, User> users;
    private Set<String> emails;

    public InMemoryUserRepository() {
        this.users = new HashMap<>();
        this.emails = new HashSet<>();
    }

    @Override
    public void save(User user) {
        this.users.put(user.getId(), user);

        // Remove the email from the set of emails (in case the user is being updated and the email has changed)
        this.emails.remove(user.getEmail());

        // Add the email to the set of emails
        this.emails.add(user.getEmail());
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Map<Long, User> findAll() {
        return this.users;
    }

    @Override
    public User findByEmail(String email) {
        return this.users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        User user = this.users.get(id);

        if(user == null) {
            throw new UserNotFoundException("User not found");
        }

        // Remove the email from the set of emails
        this.emails.remove(user.getEmail());

        // Remove user
        this.users.remove(id);
    }

    public Set<String> getEmails() {
        return this.emails;
    }
}