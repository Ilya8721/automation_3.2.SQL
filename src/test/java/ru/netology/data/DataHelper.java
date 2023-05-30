package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo1() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthInfo2() {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        String code;
    }
}