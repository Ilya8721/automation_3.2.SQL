package ru.netology.tests;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import java.sql.DriverManager;

import static com.codeborne.selenide.Selenide.open;

public class DbInteractionDbUtilsTest {
//    @BeforeEach
//    @SneakyThrows
//    void setUp() {
//        var faker = new Faker();
//        var runner = new QueryRunner();
//        var dataSQL = "INSERT INTO users(login, password) VALUES (?, ?);";
//
//        try (
//                var conn = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/app", "app", "pass"
//                );
//
//        ) {
//            // обычная вставка
//            runner.update(conn, dataSQL, faker.name().username(), "pass");
//            runner.update(conn, dataSQL, faker.name().username(), "pass");
//        }
//    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should log in to your personal account")
    @SneakyThrows
    void shouldLogInToYourPersonalAccount() {
        String code;

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo1();
        var verificationPage = loginPage.validLogin(authInfo);

        var idSQL = "SELECT id FROM users WHERE login = ?;";
        var authCodeSQL = "SELECT code FROM auth_codes WHERE user_id = ?;";
        var clearDateSQL = "DELETE FROM auth_codes WHERE user_id = ?;";
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            var id = runner.query(conn, idSQL, new ScalarHandler<>(),"vasya");
            System.out.println(id);
            var codeObject = runner.query(conn, authCodeSQL, new ScalarHandler<>(), id);
            code = codeObject.toString();
            System.out.println(code);
//            runner.execute(conn, clearDateSQL, id);
        }
        verificationPage.validVerify(code);
    }
}