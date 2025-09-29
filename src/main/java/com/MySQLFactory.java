public class MySQLFactory extends AbstractDatabaseFactory {
    public MySQLFactory(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }
}