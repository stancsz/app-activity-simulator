package mysql;

public class jdbcMySQLTest {
    public static void main(String[] args) {
        jdbcMySQL test = new jdbcMySQL();
        test.connectDB("localhost:3306","mysql","testadmin", "passw0rd");
        test.flushSchema("stream_dev_schema");
    }
}
