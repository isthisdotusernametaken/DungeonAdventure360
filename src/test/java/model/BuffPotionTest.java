package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


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
    void testCopyType() {
        Item expected = new BuffPotion(1, BuffType.STRENGTH);
        assertEquals(expected.getType(), expected.copy().getType());
    }

    @Test
    void testCopyCount() {
        Item expected = new BuffPotion(10, BuffType.STRENGTH);
        assertEquals(expected.getCount(), expected.copy().getCount());
    }

    @Test
    void testGetName() {
        String expected = "Strength Potion";
        assertEquals(expected, myBuffPotion.getName());
    }
}
