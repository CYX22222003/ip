package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KorolevEventTest {
    @Test
    public void eventTest_markEvent_success() {
        KorolevEvent expected = new KorolevEvent("test", "2021-01-01T00:00", "2033-01-02T01:01");
        expected.markTask();
        assertEquals(expected.toString(), "[E][X] test (from: 00:00 Jan 1 2021 to: 01:01 Jan 2 2033)");
        expected.unmarkTask();
        assertEquals(expected.toString(), "[E][ ] test (from: 00:00 Jan 1 2021 to: 01:01 Jan 2 2033)");
    }

}
