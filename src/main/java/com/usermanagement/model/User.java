package com.usermanagement.model;

public class User {
    private Long id;
    private String name;
    private String email;
    private UserStatus status;

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = UserStatus.ACTIVE; // Default status is ACTIVE
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    // Other methods
    public Boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    public String getAccessLevel() {
        return "STANDARD"; // Default access level for regular users
    }
}
