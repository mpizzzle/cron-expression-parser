package cron.parser;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Unit test suite for {@link CronParser}.
 */
public class CronParserTest
{
    private CronParser cronParser;

    @BeforeEach
    public void setup() {
        cronParser = new CronParser();
    }

    @DisplayName("Tests the example from the technical task.")
    @Test
    public void testCronParser_example()
    {
        String[] testArgs = { "*/15", "0", "1,15", "*", "1-5", "/usr/bin/find" };
        List<String> result = cronParser.parse(testArgs);

        Assertions.assertTrue(result.get(0).equals("minute        0 15 30 45"));
        Assertions.assertTrue(result.get(1).equals("hour          0"));
        Assertions.assertTrue(result.get(2).equals("day of month  1 15"));
        Assertions.assertTrue(result.get(3).equals("month         1 2 3 4 5 6 7 8 9 10 11 12"));
        Assertions.assertTrue(result.get(4).equals("day of week   1 2 3 4 5"));
        Assertions.assertTrue(result.get(5).equals("command       /usr/bin/find"));
    }

    @DisplayName("Tests the example from the technical task with string inputs.")
    @Test
    public void testCronParser_withNames()
    {
        String[] testArgs = { "*/15", "0", "1,15", "JAN-DEC", "MON-FRI", "/usr/bin/find" };
        List<String> result = cronParser.parse(testArgs);

        Assertions.assertTrue(result.get(0).equals("minute        0 15 30 45"));
        Assertions.assertTrue(result.get(1).equals("hour          0"));
        Assertions.assertTrue(result.get(2).equals("day of month  1 15"));
        Assertions.assertTrue(result.get(3).equals("month         1 2 3 4 5 6 7 8 9 10 11 12"));
        Assertions.assertTrue(result.get(4).equals("day of week   1 2 3 4 5"));
        Assertions.assertTrue(result.get(5).equals("command       /usr/bin/find"));
    }
}
