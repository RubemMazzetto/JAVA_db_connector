@"
package com.exercicio;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;

    /**
     * Construtor - carrega o arquivo de configuração
     * Como ler um .env ou config.json no JavaScript
     */
    public ConfigLoader(String configFile) {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(configFile)) {
            properties.load(fis);
            System.out.println("✅ Configuração carregada: " + configFile);
        } catch (IOException e) {
            System.err.println("❌ Erro ao carregar config: " + e.getMessage());
            throw new RuntimeException("Falha na configuração: " + e.getMessage(), e);
        }
    }

    // Getters - como acessar propriedades em JS
    public String getDriver() {
        return properties.getProperty("driver", "sqlite"); // Default: sqlite
    }

    public String getUrl() {
        return properties.getProperty("url");
    }

    public String getUsername() {
        return properties.getProperty("username", "");
    }

    public String getPassword() {
        return properties.getProperty("password", "");
    }

    // Método para debug - mostra toda a config
    public void printConfig() {
        System.out.println("🔧 Configuração:");
        System.out.println("  Driver: " + getDriver());
        System.out.println("  URL: " + getUrl());
        System.out.println("  Username: " + (getUsername().isEmpty() ? "[vazio]" : getUsername()));
        System.out.println("  Password: " + (getPassword().isEmpty() ? "[vazio]" : "***"));
    }
}
"@ | Out-File -FilePath "src\main\java\com\exercicio\ConfigLoader.java" -Encoding UTF8