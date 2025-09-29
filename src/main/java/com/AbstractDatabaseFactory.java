package com.exercicio;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDatabaseFactory {
    protected String url;
    protected String username;
    protected String password;
    protected HikariDataSource dataSource;

    public AbstractDatabaseFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        initializeDataSource();
    }

    private void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(getDriverClassName());
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        this.dataSource = new HikariDataSource(config);
        System.out.println("Pool de conexões configurado para: " + url);
    }

    public abstract String getDriverClassName();

    public Connection createConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public Statement createStatement(Connection conn) throws SQLException {
        return conn.createStatement();
    }

    public PreparedStatement createPreparedStatement(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    public void testConnection(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeQuery("SELECT 1"); 
            System.out.println("Teste de conexão com pool OK!");
        } catch (SQLException e) {
            System.err.println("Erro no teste de conexão: " + e.getMessage());
        }
    }

    public void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool de conexões encerrado.");
        }
    }
}