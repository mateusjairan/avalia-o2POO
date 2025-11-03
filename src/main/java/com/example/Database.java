package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // Note: Em uma aplicação real, estes dados deveriam vir de um arquivo de configuração.
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/simple_crud_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado.", e);
        }
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}
