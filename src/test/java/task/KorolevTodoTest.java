package task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class KorolevTodoTest {
    @Test
    public void todoTest_markEvent_success() {
        KorolevTodo expected = new KorolevTodo("test");
        expected.markTask();
        assertEquals(expected.toString(), "[T][X] test");
        expected.unmarkTask();
        assertEquals(expected.toString(), "[T][ ] test");
    }
}
