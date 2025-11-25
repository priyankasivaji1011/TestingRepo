package com.evry.bdd.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Utility class for managing database connections and executing SQL queries.
 */
public class DBConnectionUtil {

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                // Load all DB details from config.properties
                String host = ConfigReader.getProperty("db.host");
                String port = ConfigReader.getProperty("db.port");
                String dbName = ConfigReader.getProperty("db.name");
                String user = ConfigReader.getProperty("db.user");
                String pass = ConfigReader.getProperty("db.password");

                // Build SQL Server connection string
                String url = String.format(
                    "jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=false;",
                    host, port, dbName);

                // Explicitly load driver
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                } catch (ClassNotFoundException e) {
                    System.err.println("❌ SQL Server JDBC Driver not found. Add it to pom.xml");
                }

                // Open connection
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("✅ Connected to database: " + dbName);
            }
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed!");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Unexpected error while connecting to DB.");
            e.printStackTrace();
        }
        return connection;
    }

    // Close connection safely
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
                System.out.println("🔒 Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Execute INSERT, UPDATE, DELETE statements
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement ps = prepareStatement(sql, params)) {
            return ps.executeUpdate();
        }
    }

    // Execute SELECT statements
    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        PreparedStatement ps = prepareStatement(sql, params);
        return ps.executeQuery();
    }

    // Helper for parameterized queries
    private static PreparedStatement prepareStatement(String sql, Object... params) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
        return ps;
    }
}
