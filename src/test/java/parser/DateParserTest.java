package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class DateParserTest {
    @Test
    public void byStatement_correctFormat_success() throws Exception {
        assertEquals(DateParser.parseBy("/by 2023-01-20"),
                "2023-01-20T00:00");
        assertEquals(DateParser.parseBy("/by 2023-02-20 18:01"),
                "2023-02-20T18:01");
        assertEquals(DateParser.parseBy("/by 2023-11-20 22:03"),
                "2023-11-20T22:03");
    }

    @Test
    public void byStatement_wrongFormat_exceptionThrown() {
        try {
            assertEquals(DateParser.parseBy("/by day1 to day2"),
                    "");
            fail();
        } catch (Exception e) {
            assertEquals("Fail to extract information: Wrong format of dates",
                    e.getMessage());
        }
    }

    @Test
    public void fromStatement_correctFormat_success() throws Exception {
        assertEquals(DateParser.parseFrom("/from 2023-01-20"),
                "2023-01-20T00:00");
        assertEquals(DateParser.parseFrom("/from 2024-01-20"),
                "2024-01-20T00:00");
        assertEquals(DateParser.parseFrom(
                "/from 2023-11-20 22:03 /to 2023-11-21 23:00"),
                "2023-11-20T22:03");
    }

    @Test
    public void fromStatement_wrongFormat_exceptionThrown() {
        try {
            assertEquals(DateParser.parseFrom("/from day1 to day2"),
                        "");
            fail();
        } catch (Exception e) {
            assertEquals("Fail to extract information: Wrong format of dates",
                    e.getMessage());
        }
    }

    @Test
    public void toStatement_correctFormat_success() throws Exception {
        assertEquals(DateParser.parseTo("/to 2023-01-20"),
                "2023-01-20T00:00");
        assertEquals(DateParser.parseTo("/to 2024-01-20"),
                "2024-01-20T00:00");
        assertEquals(DateParser.parseTo("/to 2023-11-20 22:03"),
                "2023-11-20T22:03");
    }

    @Test
    public void toStatement_wrongFormat_exceptionThrown() {
        try {
            assertEquals(DateParser.parseFrom("/to day1 to day2"),
                    "");
            fail();
        } catch (Exception e) {
            assertEquals("Fail to extract information: Wrong format of dates",
                    e.getMessage());
        }
    }

    @Test
    public void recordStatement_correctFormat_success() throws Exception {
        assertEquals(DateParser.parseRecordedDate("00:00 Jan 1 2023"),
                "2023-01-01T00:00");
        assertEquals(DateParser.parseRecordedDate("22:03 Nov 20 2023"),
                "2023-11-20T22:03");
    }

    @Test
    public void recordStatement_wrongFormat_exceptionThrown() {
        try {
            assertEquals(DateParser.parseFrom("2002-09-21"),
                    "");
            fail();
        } catch (Exception e) {
            assertEquals("Fail to extract information: Wrong format of dates",
                    e.getMessage());
        }
    }
}
