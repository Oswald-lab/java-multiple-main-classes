package com.octiq.test.beta;

public record User(String email, String salt, String passwordHash) {
}
