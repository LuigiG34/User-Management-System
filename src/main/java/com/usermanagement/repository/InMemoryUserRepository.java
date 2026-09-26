package com.usermanagement.repository;

import com.usermanagement.model.User;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    
    private Map<Long, User> users;

    public InMemoryUserRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public void save(User user) {
        this.users.put(user.getId(), user);
    }

    @Override
    public User findById(Long id) {
        return this.users.get(id);
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
        this.users.remove(id);
    }
}