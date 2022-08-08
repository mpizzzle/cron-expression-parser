package cron.parser;

import java.util.List;

/**
 * Hello world!
 *
 */
public class CronParserApp
{
    private static final int EXPECTED_ARGS = 6;

    public void run(String[] args)
    {
        if (validateArguments(args)) {
          CronParser parser = new CronParser();
          List<String> results = parser.parse(args);
          results.forEach(line -> System.out.println(line));
        }
        else {
          System.out.println(unexpectedArgsLengthMessage(args));
        }
    }

    private static boolean validateArguments(String[] args) {
        return args != null && args.length == EXPECTED_ARGS;
    }

    private static String unexpectedArgsLengthMessage(String[] args) {
        return new StringBuilder()
            .append("Expected arguments size of ")
            .append(EXPECTED_ARGS)
            .append(", was ")
            .append(args.length).toString();
    }
}
