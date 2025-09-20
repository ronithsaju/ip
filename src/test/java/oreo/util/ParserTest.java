package oreo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import oreo.OreoException;
import oreo.ui.Ui;

/**
 * Tests the extractNumber() and handleEventCommand() methods in the Parser class
 */
public class ParserTest {
    @Test
    public void extractNumber_basicCommand_success() throws OreoException {
        assertEquals(12, Parser.extractNumber("mark 12"));
    }

    @Test
    public void extractNumber_invalidCommand_exceptionThrown() {
        assertThrows(OreoException.class, () -> Parser.extractNumber("delete"));
    }

    @Test
    public void handleEventCommand_validEvent_success() throws OreoException {
        Parser parser = new Parser(new Ui(), new Storage("data/testing.txt"));
        String result = parser.parse("event hackathon /from 17-11-2025 /to 18-11-2025", new TaskList());
        String expected = "Got it. I've added this task:\n"
                + "[E][ ] hackathon (from: Nov 17 2025 to: Nov 18 2025)\n"
                + "Now you have 1 tasks in the list.";
        assertEquals(expected, result);
    }

    @Test
    public void handleEventCommand_noEndDate_exceptionThrown() throws OreoException {
        Parser parser = new Parser(new Ui(), new Storage("data/testing.txt"));
        OreoException e = assertThrows(
                OreoException.class, () -> parser.parse("event hackathon /from 17-11-2025", new TaskList())
        );
        assertEquals("Please provide an event name, start date and end date.", e.getMessage());
    }

    @Test
    public void handleEventCommand_invalidDateFormat_exceptionThrown() throws OreoException {
        Parser parser = new Parser(new Ui(), new Storage("data/testing.txt"));
        OreoException e = assertThrows(
                OreoException.class, () ->
                        parser.parse("event hackathon /from 7-11-2025 /to 18-11-2025", new TaskList())
        );
        assertEquals("Invalid date format. Please follow DD-MM-YYYY. e.g. 17-11-2002", e.getMessage());
    }
}
