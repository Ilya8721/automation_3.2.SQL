package ru.netology.tests;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import java.sql.DriverManager;

import static com.codeborne.selenide.Selenide.open;

public class DbInteractionDbUtilsTest {
    @BeforeEach
    @SneakyThrows
    void setUp() {

        var runner = new QueryRunner();
        var deleteAuthorizationDataSQL = "TRUNCATE auth_codes;";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {
            runner.update(conn, deleteAuthorizationDataSQL);
        }
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

        var idSQL = "SELECT id FROM users WHERE login = ?;";
        var authCodeSQL = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1;";
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            var id = runner.query(conn, idSQL, new ScalarHandler<>(),name);
            var codeObject = runner.query(conn, authCodeSQL, new ScalarHandler<>(), id);
            String code = codeObject.toString();
            verificationPage.validVerify(code);
        }
    }

    @Test
    @DisplayName("Should throw out an error message")
    @SneakyThrows
    void shouldThrowOutAnErrorMessage() {
        String name = DataHelper.getAuthInfo1().getLogin();
        String code;

        for (int count = 1; count <= 4; count++) {
            open("http://localhost:9999");
            var loginPage = new LoginPage();
            var authInfo = DataHelper.getAuthInfo1();
            var verificationPage = loginPage.validLogin(authInfo);
            var idSQL = "SELECT id FROM users WHERE login = ?;";
            var authCodeSQL = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC LIMIT 1;";
            var runner = new QueryRunner();
            try (
                    var conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/app", "app", "pass"
                    );
            ) {
                var id = runner.query(conn, idSQL, new ScalarHandler<>(),name);
                var codeObject = runner.query(conn, authCodeSQL, new ScalarHandler<>(), id);
                code = codeObject.toString();
            }
            if (count < 4) {
                verificationPage.validVerify(code);
            } else if (count == 4) {
                verificationPage.shouldReturnAnErrorMessage(code);
            }
        }
    }
}