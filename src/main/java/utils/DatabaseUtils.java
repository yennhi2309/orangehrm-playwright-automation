package utils;

import java.sql.*;

public class DatabaseUtils {
    private static Connection connection;
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                        ConfigReader.getDBUrl(),
                        ConfigReader.getDBUsername(),
                        ConfigReader.getDBPassword()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to DB: " + e.getMessage());
        }
        return connection;
    }

    public static ResultSet executeQuery(String sql) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Query failed: " + e.getMessage());
        }
    }

    public static int executeUpdate(String sql) {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Update failed: " + e.getMessage());
        }
    }

    public static void executeDelete(String sql) {
        executeUpdate(sql);
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot close connection: " + e.getMessage());
        }
    }
}
