package mysql;

import java.sql.*;

import static mysql.ParseData.parseColumn;


public class jdbcMySQL {

    Connection conn;
    Statement stmt;
    ResultSet rs;

    public void connectDB(String host, String dbname, String username, String password) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String url = "jdbc:mysql://"+host+"/"+dbname;

        System.out.println(ts
                +" log: "
                +"a db connection is about to be made to: \t"
                + url);

        try (Connection conn = DriverManager.getConnection(url,username,password)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println(ts +" log: " +
                        "The driver name is " + meta.getDriverName());
                System.out.println(ts +" log: " +
                        "Tested connection successful");
//                this.conn = conn; // set this.conn to conn if successful
                this.conn=DriverManager.getConnection(url,username,password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            stmt.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
//            e.printStackTrace();
        } catch (NullPointerException e){
        }
    }

    public void selectUser() {
        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM USERS";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getString("username") + " " +
                        rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser() {
        try {
            stmt = conn.createStatement();
            String insert = "INSERT INTO users (ID, username,password) values " +
                    "(1004, 'newUser','newPass')";
            int rowCount = stmt.executeUpdate(insert);
            System.out.println("row Count = " + rowCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser() {
        try {
            stmt = conn.createStatement();
            String delete = "DELETE FROM users WHERE username = 'newUser'";
            int rowCount = stmt.executeUpdate(delete);
            System.out.println("row Count = " + rowCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void validateLogin(String username, String password) {
        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM users WHERE username = '" + username
                    + "' and password ='" + password + "'";
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                System.out.println("User is logged in");
            } else {
                System.out.println("Invalid Username and Password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectUserPreparedStatement() {
        try {
            String query = "SELECT * FROM users where username= ? and password =?";
            PreparedStatement pStat = conn.prepareStatement(query);
            pStat.setString(1, "Jackson");
            pStat.setString(2, "123456");
            rs = pStat.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("username") + " " +
                        rs.getString("password"));
            }
            pStat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUserPreparedStatement() {
        try {
            String query = "INSERT INTO users (ID,username, password) values" +
                    "(?,?,?)";
            PreparedStatement pStat = conn.prepareStatement(query);
            pStat.setInt(1, 1004);
            pStat.setString(2, "newUser");
            pStat.setString(3, "newPass");
            int rowCount = pStat.executeUpdate();
            System.out.println("row Count = " + rowCount);
            pStat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * drop schema if exists, then re-create it
     * @param schemaName
     */
    public void flushSchema(String schemaName){
        try (Statement stmt = conn.createStatement()) {
            String sql = "drop schema if exists " + schemaName + ";";
//            System.out.println(sql);
            stmt.executeUpdate(sql);
            sql = "create schema " + schemaName + ";";
//            System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void query(String sql){
        try (Statement stmt = conn.createStatement()) {
//            System.out.println("Querying: \n" +
//                    "----------"+sql+"\n----------");
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertValuesFromArray(String tableName, String[] row){
        String sql = prepareInsertQuery(tableName, row);
        System.out.println(sql);
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String prepareInsertQuery(String tableName, String[] row) {
        StringBuilder sqlSB = new StringBuilder();
        sqlSB.append("INSERT INTO `" + tableName +"` "+"values(");
        for (int i = 0; i< row.length; i++){
//            System.out.println(row[i]);
            switch (parseColumn(row[i])){
                case "int":
                    sqlSB.append(row[i]);
                    break;
                case "float":
                    sqlSB.append(row[i]);
                    break;
                default:
                    sqlSB.append("\'"+row[i]+"\'");
                    break;
            }
            if (i+1< row.length)
                sqlSB.append(",");
        }
        sqlSB.append(");");
        return sqlSB.toString();
    }


    public void createTable(String[] headerRow, String[] firstRow, String tableName) {
        String sqlCreateTable= sqlPrepareAutoTableCreation(headerRow, firstRow, tableName);
        jdbcMySQL jdbc=new jdbcMySQL();
        jdbc.connectDB("localhost:3306","mysql","testadmin", "passw0rd");
        jdbc.query("use stream_dev_schema;");
        jdbc.query("drop table if exists `monroe-county-crash-data2003-to-2015`;");
//        System.out.print(sqlCreateTable);
        jdbc.query(sqlCreateTable);
        jdbc.close();
    }

    /**
     * use the first row and header row to create table
     * @param headerRow
     * @param firstRow
     * @param table_name
     * @return
     */
    public static String sqlPrepareAutoTableCreation(String[] headerRow, String[] firstRow, String table_name){
        StringBuilder sqlSB = new StringBuilder();
        sqlSB.append("create table " +
                "`"+table_name +"`"+
                "\n" + "(\n");
        for (int i = 0; i < headerRow.length; i++){
            sqlSB.append("\t"+headerRow[i].replaceAll("\\s+","")
                    .replaceAll("[^A-Za-z0-9]", "")
                    + " " + parseColumn(firstRow[i]));
            if (i+1!=headerRow.length){
                sqlSB.append(",\n");
            }
        }
        sqlSB.append("\n" + ");");
//        System.out.print(sqlSB.toString());
        return sqlSB.toString();
    }

    public static void main(String[] args) {
//        MyJDBCApp app = new MyJDBCApp();
//        app.initializeConnection();
    }

}

