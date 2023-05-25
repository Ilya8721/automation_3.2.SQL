package ru.netology;

        import com.github.javafaker.Faker;
        import lombok.SneakyThrows;
        import org.apache.commons.dbutils.QueryRunner;
        import org.apache.commons.dbutils.handlers.BeanHandler;
        import org.apache.commons.dbutils.handlers.BeanListHandler;
        import org.apache.commons.dbutils.handlers.ScalarHandler;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

        import java.sql.DriverManager;

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

    @Test
    @SneakyThrows
    void stubTest() {
        var idSQL = "SELECT id FROM users WHERE login = ?;";
        var authCodeSQL = "SELECT code FROM auth_codes WHERE user_id = ?;";
        var runner = new QueryRunner();

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            var id = runner.query(conn, idSQL, new ScalarHandler<>(),"vasya");
            System.out.println(id);
            var code = runner.query(conn, authCodeSQL, new ScalarHandler<>(), id);
            System.out.println(code);
//            var all = runner.query(conn, usersSQL, new BeanListHandler<>(User.class));
//            System.out.println(all);
        }
    }
}