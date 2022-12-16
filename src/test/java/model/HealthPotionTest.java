package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class HealthPotionTest {

    private static final HealthPotion HEALTH_POTION = new HealthPotion(99);
    private static final DungeonCharacter CHARACTER =  new Adventurer(
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
        CHARACTER.applyDamage(10);

        assertEquals(expected, HEALTH_POTION.applyEffect(CHARACTER));
    }

    @Test
    void testApplyEffectCharacterFullHP() {
        assertEquals("", HEALTH_POTION.applyEffect(CHARACTER));
    }

    @Test
    void testCopyType() {
        Item expected = new HealthPotion(98);

        assertEquals(expected.getType(), HEALTH_POTION.copy().getType());
    }

    @Test
    void testCopyCount() {
        Item expected = new HealthPotion(98);

        assertEquals(expected.getCount(), HEALTH_POTION.copy().getCount());
    }

    @Test
    void testCopyNotSameItem() {
        Item expected = new BuffPotion(99, BuffType.STRENGTH);

        assertNotEquals(expected.getType(), HEALTH_POTION.copy().getType());
    }

    @Test
    void testGetName() {
        String expected = "Health Potion";

        assertEquals(expected, HEALTH_POTION.getName());
    }
}
