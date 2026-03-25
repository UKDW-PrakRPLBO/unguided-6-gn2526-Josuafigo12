package org.rplbo.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    // Path ke file database sqlite
    private static final String DB_URL = "jdbc:sqlite:asylum.db";
    private static Connection connection;

    private DBConnectionManager() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Register driver (opsional untuk versi JDBC terbaru)
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Koneksi Database Gagal: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}