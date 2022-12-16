package model;

import static model.TestingUtil.assertIsAttemptDamageResultType;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TrapTest {

    private static final Trap TRAP = buildTrap(false, true);
    private static final DungeonCharacter ADVENTURER = new Adventurer(
            "Dark LORD",
            "Warrior",
            200,
            25,
            40,
            0.7,
            0.2,
            2,
            DamageType.BLUNT,
            4,
            0.3,
            new ResistanceData(new double[]{0.1, 0.1, 0.0, 0.2, 0.2}),
            new CrushingBlow()
    );

    private static Trap buildTrap(final boolean theIsSingleUse,
                                  final boolean theIsBoardable) {
        return new Trap(
                "Pit",
                theIsSingleUse,
                theIsBoardable,
                1,
                2,
                0.0,
                0.0,
                0,
                DamageType.SHARP,
                0,
                'P'
        );
    }

    @Test
    void testCharRepresentation() {
        final char expected = 'P';

        assertEquals(expected, TRAP.charRepresentation());
    }

    @Test
    void testIsSingleUse() {
        assertFalse(TRAP.isSingleUse());
    }


    @Test
    void testIsBoardable() {
        assertTrue(TRAP.isBoardable());
    }

    @Test
    void testBoardSuccess() {
        TRAP.myIsBroken = false;
        assertTrue(TRAP.board());
    }

    @Test
    void testBoardAlreadyBoarded() {
        TRAP.myIsBroken = true;
        assertFalse(TRAP.board());
    }

    @Test
    void testBoardUnboardable() {
        assertFalse(buildTrap(true, false).board());
    }

    @Test
    void testActivateResult() {
        TRAP.myIsBroken = false;

        assertIsAttemptDamageResultType(TRAP.activate(ADVENTURER).getResult());
    }

    @Test
    void testActivateBreakingSingleUse() {
        final Trap trap = buildTrap(true, false);

        assertFalse(trap.isBroken());
        trap.activate(ADVENTURER);
        assertTrue(trap.isBroken());
    }
}
