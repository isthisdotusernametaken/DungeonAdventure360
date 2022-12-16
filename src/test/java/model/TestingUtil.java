package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    static void adjustStatsResistanceTestHelper(final BuffType theBuffType) {
        final AdjustedCharacterStats stats = buildAdjustedStats();
        final Buff buff = BuffFactory.create(theBuffType, 1);

        assertResistanceDataEqualsArray(
                stats.myResistances, stats.myCharacter.getResistances()
        );
        buff.adjustStats(stats);

        final double[] resistances = stats.myCharacter.getResistances().myResistances;
        final double[] adjustedResistances = stats.myResistances;
        for (int i = 0; i < stats.myResistances.length; i++) {
            assertEquals(
                    Util.clampFraction(
                            resistances[i] * buff.getStatMultiplier()
                    ), adjustedResistances[i]
            );
        }
    }

    static void adjustStatsSpeedTestHelper(final BuffType theBuffType) {
        final AdjustedCharacterStats stats = buildAdjustedStats();
        final Buff buff = BuffFactory.create(theBuffType, 1);

        assertEquals(stats.myCharacter.getSpeed(), stats.getSpeed());
        buff.adjustStats(stats);
        assertEquals(
                (int) (stats.myCharacter.getSpeed() * buff.getStatMultiplier()),
                stats.getSpeed()
        );
    }

    static void adjustStatsDamageTestHelper(final BuffType theBuffType) {
        final AdjustedCharacterStats stats = buildAdjustedStats();
        final Buff buff = BuffFactory.create(theBuffType, 1);

        assertEquals(stats.myCharacter.getMinDamage(), stats.getMinDamage());
        assertEquals(stats.myCharacter.getMaxDamage(), stats.getMaxDamage());
        buff.adjustStats(stats);
        assertEquals(
                (int) (stats.myCharacter.getMinDamage() * buff.getStatMultiplier()),
                stats.getMinDamage()
        );
        assertEquals(
                (int) (stats.myCharacter.getMaxDamage() * buff.getStatMultiplier()),
                stats.getMaxDamage()
        );
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

    static void assertNotNullAndEquals(final Object theExpected,
                                       final Object theActual) {
        // assertEquals already handles null cases, but this method allows
        // immediate more granular detection of whether the failure involved a
        // null or another kind of incorrect value

        assertNotNull(theActual);
        assertEquals(theExpected, theActual);
    }

    static AdjustedCharacterStats buildAdjustedStats() {
        // Guarantee factories already populated. No effect other than extra
        // delay to open and not use connection to DB if already built
        DungeonAdventure.buildFactories();

        return new AdjustedCharacterStats(
                AdventurerFactory.getInstance().createRandom(Difficulty.NORMAL)
        );
    }

    static void unexpectedExceptionFailure(final Exception theException) {
        theException.printStackTrace(); // Mock doesn't throw SQLException. Should
        fail(UNEXPECTED_EXCEPTION);     // never be encountered
    }
}
