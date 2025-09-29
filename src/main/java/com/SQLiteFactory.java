public class SQLiteFactory extends AbstractDatabaseFactory {
    public SQLiteFactory(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public String getDriverClassName() {
        return "org.sqlite.JDBC";
    }
}