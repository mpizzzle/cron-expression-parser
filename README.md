# cron-expression-parser
A simple cron expression parser.

Parses cron expressions of the following format:

*   (minute) (hour) (day-of-month) (month) (day-of-week) (command)
*   `,` (values) specifies multiple values. For example, “MON, WED, FRI“ in <day-of-week> field means on the days “Monday, Wednesday and Friday.”
*   `-` (range) determines the value range. For example, “10-11” in the <hour> field means “10th and 11th hours.”
*   `/` (increments) specifies the incremental values. For example, a “5/15” in the <minute> field means at “5, 20, 35 and 50 minutes of an hour.”
*   `*` (all) specifies that event should happen for every time unit. For example, “*” in the <minute> field means “for every minute.”

For example, given the input argument:

`*/15 0 1,15 * 1-5 /usr/bin/find`

The output should be:
```sh
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

Must have exactly 6 arguments to run.

to compile a jar and run junits:
- mvn clean package

to run the app:
- mvn exec:java -q -Dexec.mainClass=cron.parser.App -Dexec.args="*/15 0 1,15 * 1-5 /usr/bin/find"
