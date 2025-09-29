public class PostgresFactory extends AbstractDatabaseFactory {
    public PostgresFactory(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public String getDriverClassName() {
        return "org.postgresql.Driver";
    }
}