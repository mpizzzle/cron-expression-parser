package cron.parser;

import java.util.Arrays;
import java.util.List;

/**
 * Responsible for validating and parsing the arguments via {@link CronParser}.
 */
public class CronParserApp
{
    private static final int EXPECTED_ARGS = 6;

    public List<String> run(String[] args)
    {
        if (validateArguments(args)) {
          return new CronParser().parse(args);
        }

        return Arrays.asList(unexpectedArgsLengthMessage(args));
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
