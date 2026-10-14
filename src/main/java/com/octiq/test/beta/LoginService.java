package com.octiq.test.beta;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginService {

    private final Map<String, User> usersByEmail = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public User register(String email, String password) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("A valid email address is required.");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        String normalized = email.trim().toLowerCase();
        if (usersByEmail.containsKey(normalized)) {
            throw new IllegalStateException("An account already exists for " + normalized + ".");
        }
        String salt = newSalt();
        User user = new User(normalized, salt, hash(password, salt));
        usersByEmail.put(normalized, user);
        return user;
    }

    public Optional<User> authenticate(String email, String password) {
        if (email == null || password == null) {
            return Optional.empty();
        }
        User user = usersByEmail.get(email.trim().toLowerCase());
        if (user == null) {
            return Optional.empty();
        }
        String candidate = hash(password, user.salt());
        return MessageDigest.isEqual(
                candidate.getBytes(StandardCharsets.UTF_8),
                user.passwordHash().getBytes(StandardCharsets.UTF_8))
                ? Optional.of(user)
                : Optional.empty();
    }

    private String newSalt() {
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hash(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] hashed = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available.", e);
        }
    }
}
