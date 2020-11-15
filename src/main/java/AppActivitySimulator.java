import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import mysql.JDBC;

public class AppActivitySimulator {
    public void processCSV(String tableName, String pathToCsv, int sleepTime) throws IOException, InterruptedException {
        JDBC jdbc=new JDBC();

        createTableInDB(tableName, pathToCsv, jdbc);

        streamCSVtoDB(pathToCsv, sleepTime, jdbc, tableName);
    }

    private void createTableInDB(String tableName, String pathToCsv, JDBC jdbc){
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(pathToCsv));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] headerRow = new String[0];
        try {
            headerRow = getLine(csvReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] firstRow = new String[0];
        try {
            firstRow = getLine(csvReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String tableName = "monroe-county-crash-data2003-to-2015";
        jdbc.createTable(headerRow, firstRow, tableName);
        return;
    }


    private void streamCSVtoDB(String pathToCsv, int sleepTime, JDBC jdbc, String tableName) throws IOException, InterruptedException {
        BufferedReader csvReader;
        String row;
        // restarting from the 0 row
        csvReader = new BufferedReader(new FileReader(pathToCsv));
        getLine(csvReader.readLine()); // flush out the header row

        // get a jdbc for executing queries
        jdbc.connectDB("localhost:3306","mysql","testadmin", "passw0rd");
        jdbc.query("use stream_dev_schema;");

        while ((row = csvReader.readLine()) != null) {
            String[] data = getLine(row);
//            System.out.println(Arrays.toString(data));
            //TODO connect to mysql and dump data into an insert statement
            jdbc.insertValuesFromArray(tableName, data);
            Thread.sleep(sleepTime * 1000);
        }
        csvReader.close();
    }

    public String[] getLine(String row){
        String[] data = row.split(",");
        return data;
    }

}
