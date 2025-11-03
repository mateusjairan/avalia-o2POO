package com.example;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Database {

    private static final String JDBC_URL = "jdbc:h2:mem:./simple_crud_db;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static void initialize() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            InputStream inputStream = Database.class.getClassLoader().getResourceAsStream("schema.sql");
            if (inputStream == null) {
                System.err.println("Could not find schema.sql");
                return;
            }

            try (Scanner scanner = new Scanner(inputStream).useDelimiter(";")) {
                while (scanner.hasNext()) {
                    String sqlStatement = scanner.next().trim();
                    if (!sqlStatement.isEmpty()) {
                        statement.execute(sqlStatement);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database initialization failed.");
            e.printStackTrace();
        }
    }
}
