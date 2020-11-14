package sqlite;

public class jdbcSQLiteTest {
    public static void main(String[] args) {
        jdbcSQLite test = new jdbcSQLite();
        test.createDirIfNotExist("localDB");
        test.createDB("localDB","test.db");
    }
}
