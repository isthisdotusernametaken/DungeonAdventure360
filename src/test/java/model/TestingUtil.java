package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.function.Executable;

public class TestingUtil {

    private static final String UNEXPECTED_EXCEPTION =
            "Test failed due to unexpected exception.";

    static <T extends Exception> void assertThrowsWithMessage(
            final Class<T> expectedType,
            final Executable executable,
            final String theExceptionMessage) {
        assertEquals(
                theExceptionMessage,
                assertThrows(expectedType, executable).getMessage()
        );
    }

    static void assertResistanceDataEqualsArray(final double[] theResistanceValues,
                                                final ResistanceData theResistanceData) {
        for (int i = 0; i < theResistanceValues.length; i++) {
            assertEquals(
                    theResistanceValues[i],
                    theResistanceData.myResistances[i]
            );
        }
    }

    static void unexpectedExceptionFailure(final Exception theException) {
        theException.printStackTrace(); // Mock doesn't throw SQLException. Should
        fail(UNEXPECTED_EXCEPTION);     // never be encountered
    }

    static void assertIsAttemptDamageResultType(final AttackResult theResult,
                                                final AttackResult ... theOtherOptions) {
        boolean success =
                theResult == AttackResult.NO_ACTION ||
                theResult == AttackResult.MISS ||
                theResult == AttackResult.BLOCK ||
                theResult == AttackResult.HIT_DEBUFF ||
                theResult == AttackResult.HIT_NO_DEBUFF ||
                theResult == AttackResult.KILL;

        if (!success) {
            for (AttackResult allowedResult : theOtherOptions) {
                if (theResult == allowedResult) {
                    success = true;
                    break;
                }
            }
        }

        assertTrue(success);
    }
}
