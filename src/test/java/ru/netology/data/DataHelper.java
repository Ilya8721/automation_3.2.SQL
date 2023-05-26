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

    public static AuthInfo getAuthInfo2(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }
}