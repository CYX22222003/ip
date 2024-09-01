package exception;

public class ParseException extends Exception {
    public ParseException(String msg) {
        super("Fail to extract information: " + msg);
    }
}