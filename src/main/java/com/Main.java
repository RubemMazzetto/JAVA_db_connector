@"
package com.exercicio;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe principal - ponto de entrada do programa
 * Como o app.listen() ou main() em JavaScript
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("🚀 Database Connector - OOP Exercise");
        System.out.println("=====================================");
        
        try {
            // 1. Carrega configuração (como process.env)
            ConfigLoader config = new ConfigLoader("config.properties");
            config.printConfig();
            
            // 2. Cria factory (OOP - encapsulamento)
            DatabaseConnectionFactory factory = new DatabaseConnectionFactory(
                config.getDriver(),
                config.getUrl(),
                config.getUsername(),
                config.getPassword()
            );
            
            // 3. Obtém conexão (Factory Pattern)
            try (Connection conn = factory.getConnection()) {
                System.out.println("✅ Conexão estabelecida com " + config.getDriver().toUpperCase() + "!");
                
                // 4. Testa a conexão
                factory.testConnection(conn);
                
            } // try-with-resources fecha conexão automaticamente
            
        } catch (SQLException e) {
            System.err.println("❌ Erro de banco de dados: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Erro geral: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n🏁 Programa finalizado!");
    }
}
"@ | Out-File -FilePath "src\main\java\com\exercicio\Main.java" -Encoding UTF8