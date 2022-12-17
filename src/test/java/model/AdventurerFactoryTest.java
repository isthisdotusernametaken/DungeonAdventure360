package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static model.Difficulty.*;

public class AdventurerFactoryTest {

    @BeforeAll
    static void ensureAllFactoriesBuilt() {
        DungeonAdventure.buildFactories();
    }

    @Test
    void testInstanceNotNull() {
        assertNotNull(AdventurerFactory.getInstance());
    }

    @Test
    void testBuildModifiedTemplate() {
        final Adventurer template =
                AdventurerFactory.getInstance().myTemplates.get(0).get(0);
        final Adventurer modified = AdventurerFactory.getInstance()
                .buildModifiedTemplate(template, EASY);

        areEqual(template, modified);
    }

    @Test
    void testCreateFromTemplate() {
        final Adventurer template =
                AdventurerFactory.getInstance().myTemplates.get(0).get(0);

        areEqual(
                template,
                AdventurerFactory.getInstance().createFromTemplate(template)
        );
    }

    @Test
    void testCreate() {
        areEqual(
                AdventurerFactory.getInstance().myTemplates.get(0).get(0),
                AdventurerFactory.getInstance().create(0, EASY)
        );
    }

    private void areEqual(final Adventurer adventurer1,
                          final Adventurer adventurer2) {
        assertEquals(adventurer1.getClassName(), adventurer2.getClassName());
        assertEquals(adventurer1.getMaxHP(), adventurer2.getMaxHP());
        assertEquals(adventurer1.getMinDamage(), adventurer2.getMinDamage());
        assertEquals(adventurer1.getMaxDamage(), adventurer2.getMaxDamage());
        assertEquals(adventurer1.getHitChance(), adventurer2.getHitChance());
        assertEquals(adventurer1.getDebuffChance(), adventurer2.getDebuffChance());
        assertEquals(adventurer1.getDebuffDuration(), adventurer2.getDebuffDuration());
        assertEquals(adventurer1.getDamageType(), adventurer2.getDamageType());
        assertEquals(adventurer1.getSpeed(), adventurer2.getSpeed());
        assertEquals(adventurer1.getBlockChance(), adventurer2.getBlockChance());
        TestingUtil.assertResistanceDataEqualsArray(
                adventurer1.getResistances().myResistances,
                adventurer2.getResistances()
        );
        assertEquals(
                adventurer1.getSpecialSkill().getClass(),
                adventurer2.getSpecialSkill().getClass()
        );
    }
}
