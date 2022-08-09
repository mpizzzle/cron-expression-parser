package cron.parser;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.StringUtils.split;

import java.util.Arrays;
import java.util.List;

/**
 * Expands cron expressions to show the times at which it will run.
 */
public class CronParser
{
  private static final String[] FIELD_NAMES = { "minute", "hour", "day of month", "month", "day of week", "command" };
  private static final Integer[] TIME_FIELDS_MAX = { 60, 24, 31, 12, 7 };
  private static final String[] MONTHS = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
  private static final String[] DAYS = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

  public List<String> parse(String[] args) {
    return Arrays.asList(
      parseMinute(args[0]),
      parseHour(args[1]),
      parseDayOfMonth(args[2]),
      parseMonth(args[3]),
      parseDayOfWeek(args[4]),
      parseCommand(args[5]));
  }

  private String parseMinute(String arg) {
    return format(expand(arg, TIME_FIELDS_MAX[0]), FIELD_NAMES[0]);
  }

  private String parseHour(String arg) {
    return format(expand(arg, TIME_FIELDS_MAX[1]), FIELD_NAMES[1]);
  }

  private String parseDayOfMonth(String arg) {
    return format(expand(arg, TIME_FIELDS_MAX[2]), FIELD_NAMES[2]);
  }

  private String parseMonth(String arg) {
    return format(expand(replaceAll(arg, MONTHS), TIME_FIELDS_MAX[3]), FIELD_NAMES[3]);
  }

  private String parseDayOfWeek(String arg) {
    return format(expand(replaceAll(arg, DAYS), TIME_FIELDS_MAX[4]), FIELD_NAMES[4]);
  }

  private String parseCommand(String arg) {
    return format(arg, FIELD_NAMES[5]);
  }

  private String expand(String arg, int timeField) {
    String[] valuesDelimited = split(arg, ',');
    if (valuesDelimited.length > 1) {
      return Arrays.asList(valuesDelimited).stream().collect(joining(" "));
    }

    String[] valueRange = split(arg, '-');
    if (valueRange.length > 1) {
      return range(parseInt(valueRange[0]), parseInt(valueRange[1]) + 1)
        .mapToObj(String::valueOf)
        .collect(joining(" "));
    }

    String[] valueInterval = split(arg, '/');
    if (valueInterval.length > 1) {
      final int start = (!"*".equals(valueInterval[0])) ? parseInt(valueInterval[0]) : 0;
      int interval = parseInt(valueInterval[1]);

      return range(0, timeField / interval)
        .map(i -> start + (i * interval))
        .filter(i -> i < timeField)
        .mapToObj(String::valueOf)
        .collect(joining(" "));
    }

    if (arg.contains("*")) {
      return range(1, timeField + 1).mapToObj(String::valueOf).collect(joining(" "));
    }

    return arg;
  }

  private String format(String interval, String timeField) {
    return String.format("%-14s%s", timeField, interval);
  }

  private String replaceAll(String baseString, String[] replaceParts) {
    for (int i = 0; i < replaceParts.length; ++i) {
      baseString = baseString.replaceAll(replaceParts[i], String.valueOf(i + 1));
    }

    return baseString;
  }
}
