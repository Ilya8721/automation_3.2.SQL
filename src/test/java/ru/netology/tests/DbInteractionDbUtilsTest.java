package ru.netology.tests;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class DbInteractionDbUtilsTest {
    @BeforeEach
    void setUp() {
        SQLHelper.cleanBase();
    }


    @Test
    @DisplayName("Should log in to your personal account")
    @SneakyThrows
    void shouldLogInToYourPersonalAccount() {
        String name = DataHelper.getAuthInfo1().getLogin();
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo1();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = SQLHelper.getVerificationCode(name);
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should throw out an error message")
    @SneakyThrows
    void shouldThrowOutAnErrorMessage() {
        String name = DataHelper.getAuthInfo1().getLogin();

        for (int count = 1; count <= 4; count++) {
            open("http://localhost:9999");
            var loginPage = new LoginPage();
            var authInfo = DataHelper.getAuthInfo1();
            var verificationPage = loginPage.validLogin(authInfo);
            var verificationCode = SQLHelper.getVerificationCode(name);
            if (count < 4) {
                verificationPage.validVerify(verificationCode.getCode());
            } else if (count == 4) {
                verificationPage.shouldReturnAnErrorMessage(verificationCode.getCode());
            }
        }
    }
}