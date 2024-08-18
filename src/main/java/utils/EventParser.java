package utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class EventParser {
    public static String parseDate(String eventString) throws ParseException {
        Pattern fromPattern = Pattern.compile("/from\\s+([\\w\\s]+)");
        Pattern toPattern = Pattern.compile("/to\\s+([\\w\\s]+)");
        Pattern byPattern = Pattern.compile("/by\\s+([\\w\\s]+)");

        Matcher fromMatcher = fromPattern.matcher(eventString);
        Matcher toMatcher = toPattern.matcher(eventString);
        Matcher byMatcher = byPattern.matcher(eventString);

        if (fromMatcher.find() && toMatcher.find()) {
            return "from: " + fromMatcher.group(1) + " " + "to: " + toMatcher.group(1);
        } else if (byMatcher.find()) {
            return "by: " + byMatcher.group(1);
        } else {
            throw new ParseException("the description of event is incorrect");
        }
    }

    public static String parseName(String start, String stop, String input)  throws ParseException {
        Pattern pattern = Pattern.compile(start + "\\s+(.*)\\s*" + stop);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new ParseException("description of event is not provided or incorrect.");
        }
    }
}
