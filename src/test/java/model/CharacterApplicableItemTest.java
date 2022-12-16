package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CharacterApplicableItemTest {

    private final HealthPotion myHealthPotion = new HealthPotion(99);
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
    void testUse() {
        String expected = "Healed 10 hp and cleared all debuffs.";
        myCharacter.applyDamage(10);

        assertEquals(expected,  myHealthPotion.use(myCharacter));
    }
}
