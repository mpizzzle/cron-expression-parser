package cron.parser;

import java.util.List;

/**
 * Main entry point for cron expression parser.
 */
public class App
{
    private static final CronParserApp CPA = new CronParserApp();

    public static void main( String[] args )
    {
        List<String> results = CPA.run(args);

        results.forEach(System.out::println);
    }
}
