package com.ust.sdet.support;

import com.ust.sdet.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DbSupport {

    private final DatabaseConfig config;

    public DbSupport(DatabaseConfig config) {
        this.config = config;
    }

    public boolean isReachable() throws SQLException {
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT 1");
             ResultSet result = statement.executeQuery()) {
            return result.next() && result.getInt(1) == 1;
        }
    }

    public long countOrders() throws SQLException {
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM orders");
             ResultSet result = statement.executeQuery()) {
            result.next();
            return result.getLong(1);
        }
    }

    public boolean orderExists(String sku) throws SQLException {
        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM orders WHERE sku = ?")) {
            statement.setString(1, sku);
            try (ResultSet result = statement.executeQuery()) {
                result.next();
                return result.getLong(1) > 0;
            }
        }
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(config.jdbcUrl(), config.username(), config.password());
    }
}