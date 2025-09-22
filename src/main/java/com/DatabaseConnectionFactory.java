@"
package com.exercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Factory Pattern - cria conexões baseado no driver
 * Como uma factory function em JavaScript que retorna diferentes tipos de conexão
 */
public class DatabaseConnectionFactory {
    private final String driver;
    private final String url;
    private final String username;
    private final String password;

    public DatabaseConnectionFactory(String driver, String url, String username, String password) {
        this.driver = driver.toLowerCase();
        this.url = url;
        this.username = username;
        this.password = password;
        
        System.out.println("🔨 Factory criada para driver: " + driver);
    }

    /**
     * Método factory - cria conexão baseado no driver
     * Como: const connection = driver === 'sqlite' ? new SQLite() : new Postgres()
     */
    public Connection getConnection() throws SQLException {
        System.out.println("🔗 Tentando conectar em: " + url);
        
        switch (driver) {
            case "sqlite":
                return createSQLiteConnection();
                
            case "postgres":
                return createPostgresConnection();
                
            case "mysql":
                return createMySQLConnection();
                
            default:
                throw new SQLException("❌ Driver desconhecido: " + driver + 
                    ". Use: sqlite, postgres, ou mysql");
        }
    }

    private Connection createSQLiteConnection() throws SQLException {
        System.out.println("🗄️  Conectando SQLite...");
        // SQLite não precisa de username/password
        return DriverManager.getConnection(url);
    }

    private Connection createPostgresConnection() throws SQLException {
        System.out.println("🐘 Conectando PostgreSQL...");
        try {
            // Carrega driver dinamicamente (como require() em JS)
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("❌ Driver PostgreSQL não encontrado", e);
        }
        return DriverManager.getConnection(url, username, password);
    }

    private Connection createMySQLConnection() throws SQLException {
        System.out.println("🐬 Conectando MySQL...");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("❌ Driver MySQL não encontrado", e);
        }
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Método utilitário para testar a conexão
     * Cria tabela de teste e insere dados
     */
    public void testConnection(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // Cria tabela se não existir (como migrations no Laravel)
            String createTable = """
                CREATE TABLE IF NOT EXISTS usuarios_teste (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100),
                    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;
            
            stmt.execute(createTable);
            
            // Insere dados de teste
            String insert = "INSERT INTO usuarios_teste (nome, email) VALUES ('Rubem Mazzetto', 'rubem@email.com')";
            int rows = stmt.executeUpdate(insert);
            
            // Consulta para verificar
            java.sql.ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios_teste WHERE nome = 'Rubem Mazzetto'");
            if (rs.next()) {
                System.out.println("✅ Teste OK! Usuário inserido:");
                System.out.println("  ID: " + rs.getInt("id"));
                System.out.println("  Nome: " + rs.getString("nome"));
                System.out.println("  Email: " + rs.getString("email"));
                System.out.println("  Criado: " + rs.getTimestamp("criado_em"));
            }
            
            // Limpa tabela de teste
            stmt.execute("DELETE FROM usuarios_teste WHERE nome = 'Rubem Mazzetto'");
            
            System.out.println("🎉 Conexão testada com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("❌ Erro no teste de conexão: " + e.getMessage());
        }
    }
}
"@ | Out-File -FilePath "src\main\java\com\exercicio\DatabaseConnectionFactory.java" -Encoding UTF8