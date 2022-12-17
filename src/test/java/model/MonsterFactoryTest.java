package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static model.Difficulty.*;

public class MonsterFactoryTest {

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
        final Monster template =
                MonsterFactory.getInstance().myTemplates.get(0).get(0);
        final Monster modified = MonsterFactory.getInstance()
                .buildModifiedTemplate(template, EASY);

        assertEquals(
                EASY.modifyNegative(template.getMaxHP()),
                modified.getMaxHP()
        );
        assertEquals(
                EASY.modifyNegative(template.getMinDamage()),
                modified.getMinDamage()
        );
        assertEquals(
                EASY.modifyNegative(template.getMaxDamage()),
                modified.getMaxDamage()
        );
        assertEquals(
                EASY.modifyNegative(template.getSpeed()),
                modified.getSpeed()
        );
    }

    @Test
    void testCreateFromTemplate() {
        final Monster template =
                MonsterFactory.getInstance().myTemplates.get(0).get(0);

        areEqual(
                template,
                MonsterFactory.getInstance().createFromTemplate(template)
        );
    }

    @Test
    void testCreate() {
        areEqual(
                MonsterFactory.getInstance().myTemplates.get(0).get(0),
                MonsterFactory.getInstance().create(0, EASY)
        );
    }

    private void areEqual(final Monster monster1,
                          final Monster monster2) {
        assertEquals(monster1.getClassName(), monster2.getClassName());
        assertEquals(monster1.getMaxHP(), monster2.getMaxHP());
        assertEquals(monster1.getMinDamage(), monster2.getMinDamage());
        assertEquals(monster1.getMaxDamage(), monster2.getMaxDamage());
        assertEquals(monster1.getHitChance(), monster2.getHitChance());
        assertEquals(monster1.getDebuffChance(), monster2.getDebuffChance());
        assertEquals(monster1.getDebuffDuration(), monster2.getDebuffDuration());
        assertEquals(monster1.getDamageType(), monster2.getDamageType());
        assertEquals(monster1.getSpeed(), monster2.getSpeed());
        assertEquals(monster1.getBlockChance(), monster2.getBlockChance());
        assertEquals(monster1.getHealChance(), monster2.getHealChance());
        TestingUtil.assertResistanceDataEqualsArray(
                monster1.getResistances().myResistances,
                monster2.getResistances()
        );
    }
}
