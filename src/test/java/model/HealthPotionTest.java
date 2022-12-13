package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HealthPotionTest {
    private static final HealthPotion myHealthPotion = new HealthPotion(99);
    private static final DungeonCharacter myCharacter =  new Adventurer(
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
    void testApplyEffect() {
        String expected = "Healed 10 hp and cleared all debuffs.";
        myCharacter.applyDamage(10);

        assertEquals(expected, myHealthPotion.applyEffect(myCharacter));
    }

    @Test
    void testApplyEffectCharacterFullHP() {
        assertEquals("", myHealthPotion.applyEffect(myCharacter));
    }

    @Test
    void testCopy() {
        Item expected = new HealthPotion(98);

        assertEquals(expected.getType(), myHealthPotion.copy().getType());
    }

    @Test
    void testCopyNotSameItem() {
        Item expected = new BuffPotion(99, BuffType.STRENGTH);

        assertNotEquals(expected.getType(), myHealthPotion.copy().getType());
    }

    @Test
    void testGetName() {
        String expected = "Health Potion";

        assertEquals(expected, myHealthPotion.getName());
    }
}
