package parser;

import org.junit.jupiter.api.Test;

import exception.ParseException;
import task.KorolevDeadline;
import task.KorolevEvent;
import task.KorolevTodo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class EventParserTest {
    @Test
    public void parseName_correctInput_success() throws Exception {
        assertEquals("event Test abc ",
                EventParser.parseName("]", "/from", "] event Test abc /from"));
    }

    @Test
    public void parseTodo_correctInput_success() throws Exception {
        String msg = "[T][ ] Todo test123";
        KorolevTodo expected = new KorolevTodo("Todo test123");
        assertEquals(expected.toString(), EventParser.parseLoadedRecord(msg).toString());
    }

    @Test
    public void parseTodo_incorrectInput_exceptionThrown() {
        try {
            String msg = "(T)( ) Todo test123";
            KorolevTodo expected = new KorolevTodo("Todo test123");
            assertEquals(expected.toString(), EventParser.parseLoadedRecord(msg).toString());
            fail();
        } catch (ParseException e) {
            assertEquals(e.getMessage(), "Fail to extract information: fail to parse the record");
        }
    }

    @Test
    public void parseDeadline_correctInput_success() throws Exception {
        String msg = "[D][ ] Todo test123 (by 00:00 Jan 1 2023)";
        KorolevDeadline expected = new KorolevDeadline("Todo test123", "2023-01-01T00:00");
        assertEquals(expected.toString(),
                EventParser.parseLoadedRecord(msg).toString());
    }

    @Test
    public void parseDeadline_incorrectInput_exceptionThrown() {
        try {
            String msg = "[D][ ] Todo test123 <by 00:00 Jan 1 2023>";
            KorolevDeadline expected = new KorolevDeadline("Todo test123", "2023-01-01T00:00");
            assertEquals(expected.toString(),
                    EventParser.parseLoadedRecord(msg).toString());
            fail();
        } catch (ParseException e) {
            assertEquals(e.getMessage(), "Fail to extract information: fail to parse the record");
        }
    }

    @Test
    public void parseEvent_correctInput_success() throws Exception {
        String msg = "[E][ ] Todo test123 "
                + "(from: 00:00 Jan 1 2023 to: 01:01 Jan 2 2023)";
        KorolevEvent expected = new KorolevEvent("Todo test123",
                    "2023-01-01T00:00", "2023-01-02T01:01");
        assertEquals(expected.toString(), EventParser.parseLoadedRecord(msg).toString());
    }

    @Test
    public void parseEvent_incorrectInput_exceptionThrown() {
        try {
            String msg = "[E][ ] Todo test123 "
                    + "(from: 00:00 Jan 1 2023 to: 01:01 Jan 2 2023)";
            KorolevEvent expected = new KorolevEvent("Todo test123",
                    "2023-01-01T00:00", "2023-01-02T01:01");
            assertEquals(expected.toString(), EventParser.parseLoadedRecord(msg).toString());
        } catch (ParseException e) {
            assertEquals(e.getMessage(), "Fail to extract information: fail to parse the record");
        }
    }
}
