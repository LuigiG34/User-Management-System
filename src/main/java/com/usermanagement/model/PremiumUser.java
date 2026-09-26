package com.usermanagement.model;

public class PremiumUser extends User {
    private Integer loyaltyLevel;

    public PremiumUser(Long id, String name, String email, Integer loyaltyLevel) {
        super(id, name, email);
        this.loyaltyLevel = loyaltyLevel;
    }

    public Integer getLoyaltyLevel() {
        return loyaltyLevel;
    }

    public void setLoyaltyLevel(Integer loyaltyLevel) {
        this.loyaltyLevel = loyaltyLevel;
    }

    @Override
    public String getAccessLevel() {
        return "PREMIUM"; // Access level for premium users
    }
}
