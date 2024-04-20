package io.example.customframework.business.domain;

public class User {
    private String userId;
    private String name;

    private User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public static User of(String userId, String name) {
        return new User(userId, name);
    }

    public String getUserId() {
        return userId;
    }
}
