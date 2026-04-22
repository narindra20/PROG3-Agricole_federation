package hei.school.agricole.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/agricultural_federation";

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}