package model;

import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestingUtil {

    static <T extends Throwable> void assertThrowsWithMessage(
            final Class<T> expectedType,
            final Executable executable,
            final String theExceptionMessage) {
        assertEquals(
                theExceptionMessage,
                assertThrows(expectedType, executable).getMessage()
        );
    }
}
