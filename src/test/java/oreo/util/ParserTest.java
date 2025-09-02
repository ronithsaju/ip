package oreo.util;

import oreo.OreoException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the extractNumber() method in the Parser class
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
}
