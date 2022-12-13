package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuffPotionTest {

    private final BuffPotion myBuffPotion = new BuffPotion(99, BuffType.STRENGTH);
    private final DungeonCharacter myCharacter =  new Adventurer(
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
    void testGetBuffType() {
        BuffType expected = BuffType.STRENGTH;
        assertEquals(expected, myBuffPotion.getBuffType());
    }

    @Test
    void testApplyEffect() {
        String actual = myBuffPotion.applyEffect(myCharacter);
        String[] expected = {
                "Strength +1 turns",
                "Strength +2 turns",
                "Strength +3 turns",
                "Strength +4 turns",
                "Strength +5 turns"
        };
        assertTrue(
                actual.equals(expected[0]) ||
                        actual.equals(expected[1]) ||
                        actual.equals(expected[2]) ||
                        actual.equals(expected[3]) ||
                        actual.equals(expected[4])
        );
    }

    @Test
    void testCopy() {
        Item expected = new BuffPotion(1, BuffType.STRENGTH);
        assertEquals(expected.getType(), myBuffPotion.getType());
    }

    @Test
    void testGetName() {
        String expected = "Strength Potion";
        assertEquals(expected, myBuffPotion.getName());
    }
}
