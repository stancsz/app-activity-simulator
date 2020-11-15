package mysql;

public class jdbcMySQLTest {
    public static void main(String[] args) {
        JDBC test = new JDBC();
        test.connectDB("localhost:3306","mysql","testadmin", "passw0rd");
        test.flushSchema("stream_dev_schema");
    }
}
