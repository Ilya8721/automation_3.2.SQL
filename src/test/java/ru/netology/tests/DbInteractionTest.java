package ru.netology;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;

public class DbInteractionTest {

//    @BeforeEach
//    @SneakyThrows
//    void setUp() {
//        var faker = new Faker();
//        var dataSQL = "INSERT INTO users(login, password) VALUES (?, ?);";
//
//        try (
//                var conn = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:3306/app", "app", "pass"
//                );
//                var dataStmt = conn.prepareStatement(dataSQL);
//        ) {
//            dataStmt.setString(1, faker.name().username());
//            dataStmt.setString(2, "password");
//            dataStmt.executeUpdate();
//            dataStmt.setString(1, faker.name().username());
//            dataStmt.setString(2, "password");
//            dataStmt.executeUpdate();
//        }
//    }

    @Test
    @SneakyThrows
    void stubTest() {
        var idSQL = "SELECT id FROM users WHERE login = 'vasya';";
        var authCodeSQL = "SELECT code FROM auth_codes WHERE user_id = ?;";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                var idStmt = conn.prepareStatement(idSQL);
                var authCodeStmt = conn.prepareStatement(authCodeSQL);
        ) {
            try (var id = idStmt.executeQuery(idSQL)) {

                    System.out.println(id);
                }
            }

//            cardsStmt.setInt(1, 1);
//            try (var rs = cardsStmt.executeQuery()) {
//                while (rs.next()) {
//                    var id = rs.getInt("id");
//                    var number = rs.getString("number");
//                    var balanceInKopecks = rs.getInt("balance_in_kopecks");
//                    // TODO: сложить всё в список
//                    System.out.println(id + " " + number + " " + balanceInKopecks);
//                }
//            }
//        }
    }
}