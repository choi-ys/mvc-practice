package io.example.customframework.business.repo;

import io.example.customframework.business.domain.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepo {
    private static Map<String, User> userMap = new HashMap<>();

    public static void save(User user) {
        userMap.put(user.getUserId(), user);
    }

    public static User findById(String userId) {
        return userMap.get(userId);
    }

    public static Collection<User> findAll() {
        return userMap.values();
    }
}
