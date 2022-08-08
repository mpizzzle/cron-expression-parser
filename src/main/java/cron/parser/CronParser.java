package cron.parser;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.StringUtils.split;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class CronParser
{
  private static final String[] FIELD_NAMES = { "minute", "hour", "day of month", "month", "day of week", "command" };
  private static final Integer[] timeFieldMax = { 60, 24, 31, 12, 7 };
  private static final String[] MONTHS = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
  private static final String[] DAYS = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

  public List<String> parse(String[] args) {
      return range(0, FIELD_NAMES.length).mapToObj(i -> format(format(args[i], i), FIELD_NAMES[i])).collect(toList());
  }

  private String format(String arg, int idx) {
    String s = arg;

    if (idx == 3) {
        s = replaceAll(arg, MONTHS);
    }

    if (idx == 4) {
        s = replaceAll(arg, DAYS);
    }

    if (idx == 5) {
      return s;
    }

    String[] c = split(s, ',');
    if (c.length > 1) {
      return Arrays.asList(c).stream().collect(joining(" "));
    }

    String[] a = split(s, '-');
    if (a.length > 1) {
      return range(parseInt(a[0]), parseInt(a[1]) + 1)
        .mapToObj(String::valueOf)
        .collect(joining(" "));
    }

    String[] b = split(s, '/');
    if (b.length > 1) {
      final int start = (!"*".equals(b[0])) ? parseInt(b[0]) : 0;
      int interval = parseInt(b[1]);

      return range(start, timeFieldMax[idx] / interval)
        .map(i -> start + (i * interval))
        .filter(i -> i < timeFieldMax[idx])
        .mapToObj(String::valueOf)
        .collect(joining(" "));
    }

    if (s.contains("*")) {
      return range(0, timeFieldMax[idx]).mapToObj(i -> String.valueOf(i + 1)).collect(joining(" "));
    }

    return s;
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
