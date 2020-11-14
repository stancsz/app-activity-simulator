import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AppActivitySimulatorTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        AppActivitySimulator test = new AppActivitySimulator();
        test.processCSV("monroe-county-crash-data2003-to-2015","monroe-county-crash-data2003-to-2015.csv", 1);
    }
}