package com.exercicio;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ConfigLoader config = new ConfigLoader("config.properties");
        DatabaseConnectionFactory factory = new DatabaseConnectionFactory(
                config.getDriver(),
                config.getUrl(),
                config.getUsername(),
                config.getPassword()
        );

        try (Connection conn = factory.getConnection()) {
            System.out.println("Conexão estabelecida com " + config.getDriver() + "!");
            factory.testConnection(conn);
        } catch (SQLException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
        } finally {
            factory.shutdown();
        }
    }
}