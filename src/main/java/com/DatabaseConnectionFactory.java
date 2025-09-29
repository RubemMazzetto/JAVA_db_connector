import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionFactory {
    private AbstractDatabaseFactory dbFactory;

    public DatabaseConnectionFactory(String driver, String url, String username, String password) {
        switch (driver.toLowerCase()) {
            case "sqlite":
                dbFactory = new SQLiteFactory(url, username, password);
                break;
            case "postgres":
                dbFactory = new PostgresFactory(url, username, password);
                break;
            case "mysql":
                dbFactory = new MySQLFactory(url, username, password);
                break;
            default:
                dbFactory = new SQLiteFactory(url, "", "");
        }
        System.out.println("Fábrica selecionada para driver: " + driver);
    }

    public Connection getConnection() throws SQLException {
        return dbFactory.createConnection();
    }

    public void testConnection(Connection conn) {
        dbFactory.testConnection(conn);
    }

    public void shutdown() {
        dbFactory.shutdown();
    }
}