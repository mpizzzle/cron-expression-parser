package cron.parser;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Unit test suite for {@link CronParserApp}.
 */
public class CronParserAppTest
{
    private static final int EXPECTED_ARGS = 6;

    private CronParserApp cronParserApp;

    @BeforeEach
    public void setup() {
        cronParserApp = new CronParserApp();
    }

    @DisplayName("Tests run method with the example from the technical task.")
    @Test
    public void testCronParserApp_example()
    {
        String[] testArgs = { "*/15", "0", "1,15", "*", "1-5", "/usr/bin/find" };
        List<String> results = cronParserApp.run(testArgs);

        Assertions.assertTrue(results.get(0).equals("minute        0 15 30 45"));
        Assertions.assertTrue(results.get(1).equals("hour          0"));
        Assertions.assertTrue(results.get(2).equals("day of month  1 15"));
        Assertions.assertTrue(results.get(3).equals("month         1 2 3 4 5 6 7 8 9 10 11 12"));
        Assertions.assertTrue(results.get(4).equals("day of week   1 2 3 4 5"));
        Assertions.assertTrue(results.get(5).equals("command       /usr/bin/find"));
    }

    @DisplayName("Tests run method with wrong arguments length.")
    @Test
    public void testCronParserApp_WrongArgumentLength()
    {
        String[] testArgs = { "*/15", "0", "1,15", "JAN-DEC", "/usr/bin/find" };
        List<String> results = cronParserApp.run(testArgs);

        String expected = new StringBuilder()
            .append("Expected arguments size of ")
            .append(EXPECTED_ARGS)
            .append(", was ")
            .append(testArgs.length).toString();

        Assertions.assertTrue(results.get(0).equals(expected));
    }
}
