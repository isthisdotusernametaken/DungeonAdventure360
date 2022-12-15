package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrapTest {

    private static final Trap myTrap = new Trap(
            "Pit",
            true,
            true,
            1,
            2,
            0.0,
            0.0,
            0,
            DamageType.SHARP,
            0,
            'P');

    private static final DungeonCharacter myCharacter = new Adventurer(
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
            new CrushingBlow());

    @Test
    void testCharRepresentation() {
        char expected = 'P';

        assertEquals(expected,myTrap.charRepresentation());
    }

    @Test
    void testIsSingleUse() {
        boolean expected = true;

        assertEquals(expected, myTrap.isSingleUse());
    }


    @Test
    void testIsBoardable() {
        boolean expected = true;

        assertEquals(expected, myTrap.isBoardable());
    }

    @Test
    void testBoard() {
        boolean expected = true;

        assertEquals(expected, myTrap.board());
    }

    @Test
    void testActivate() {
        AttackResultAndAmount expected =
                new AttackResultAndAmount(AttackResult.HIT_NO_DEBUFF, 2);
        AttackResult actual = myTrap.activate(myCharacter).getResult();

        assertTrue(
                actual.equals(AttackResult.MISS) ||
                actual.equals(AttackResult.DODGE));
    }
}
