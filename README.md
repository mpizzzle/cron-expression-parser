# cron-expression-parser
A simple cron expression parser.

- mvn clean package
- mvn exec:java -q -Dexec.mainClass=cron.parser.App -Dexec.args="*/15 0 1,15 * 1-5 /usr/bin/find"

