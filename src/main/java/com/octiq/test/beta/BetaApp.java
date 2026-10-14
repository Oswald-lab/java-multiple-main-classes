package com.octiq.test.beta;

public class BetaApp {
    public static void main(String[] args) {
        System.out.println("=== BetaApp | octiq test suite | beta module ===");
        System.out.println("BetaApp is up and running (package com.octiq.test.beta).");

        LoginService loginService = new LoginService();
        loginService.register("demo@octiq.test", "password123");
        boolean authenticated = loginService
                .authenticate("demo@octiq.test", "password123")
                .isPresent();
        System.out.println("Login for demo@octiq.test: "
                + (authenticated ? "success" : "failed"));
    }
}
