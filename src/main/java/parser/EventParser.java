package parser;

import exception.ParseException;
import task.KorolevDeadline;
import task.KorolevEvent;
import task.KorolevTask;
import task.KorolevTodo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles all the description of different types of events
 */
public class EventParser {
    /**
     * Analyses the input string and extracts any substring appearing between
     * stop and start.
     *
     * @param start the start of the expected string
     * @param stop the end of the expected string
     * @param input string to be parsed
     * @return content between start and stop
     * @throws ParseException when cannot find any content with given start and stop
     */
    public static String parseName(String start, String stop, String input) throws ParseException {
        Pattern pattern = Pattern.compile(start + "\\s+(.+)\\s*" + stop);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new ParseException("description of event is not provided or incorrect.");
        }
    }

    /**
     * Parses the records read from local data file.
     *
     * @param record each line of record
     * @return the KorolevTask object
     * @throws ParseException when the line of record cannot be parsed
     */
    public static KorolevTask parseLoadedRecord(String record) throws ParseException {
        char type = record.charAt(1);
        switch (type) {
        case 'T' -> {
            return parseTodoRecord(record);
        }
        case 'D' -> {
            return parseDeadlineRecord(record);
        }
        case 'E' -> {
            return parseEventRecord(record);
        }
        default -> throw new ParseException("Invalid record presented in the hard disk.");
        }
    }

    private static KorolevTodo parseTodoRecord(String record) throws ParseException {
        Pattern p = Pattern.compile("\\]\\s(.+)");
        Matcher m = p.matcher(record);
        String taskDescription;
        if (m.find()) {
            taskDescription = m.group(1);
            return new KorolevTodo(taskDescription);
        } else {
            throw new ParseException("fail to parse the record");
        }
    }

    private static KorolevDeadline parseDeadlineRecord(String record) throws ParseException {
        Pattern p1 = Pattern.compile("\\]\\s(.+)\\s\\(");
        Matcher m1 = p1.matcher(record);

        Pattern p2 = Pattern.compile("by\\s(.+)\\)");
        Matcher m2 = p2.matcher(record);
        String taskDescription, date;
        if (m1.find() && m2.find()) {
            taskDescription = m1.group(1);
            date = DateParser.parseRecordedDate(m2.group(1));
            return new KorolevDeadline(taskDescription, date);
        } else {
            throw new ParseException("fail to parse the record");
        }
    }

    private static KorolevEvent parseEventRecord(String record) throws ParseException {
        Pattern name_pattern = Pattern.compile("\\]\\s(.+)\\s\\(");
        Matcher m1 = name_pattern.matcher(record);

        Pattern from_pattern = Pattern.compile("from:\\s(.+)\\s*to");
        Matcher m2 = from_pattern.matcher(record);

        Pattern to_pattern = Pattern.compile("to:\\s(.+)\\s*\\)");
        Matcher m3 = to_pattern.matcher(record);
        String taskDescription, from, to;
        if (m1.find() && m2.find() && m3.find()) {
            taskDescription = m1.group(1);
            from = DateParser.parseRecordedDate(m2.group(1).strip());
            to = DateParser.parseRecordedDate(m3.group(1).strip());
            return new KorolevEvent(taskDescription, from, to);
        } else {
            throw new ParseException("fail to parse the record");
        }
    }
}