package model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static model.Difficulty.*;

public class TrapFactoryTest {

    @BeforeAll
    static void ensureAllFactoriesBuilt() {
        DungeonAdventure.buildFactories();
    }

    @Test
    void testInstanceNotNull() {
        assertNotNull(TrapFactory.getInstance());
    }

    @Test
    void testGetClassesAndRepresentationsLength() {
        final List<String> representations =
                TrapFactory.getInstance().getClassesAndRepresentations();

        assertNotNull(representations);
        assertEquals(
                TrapFactory.getInstance().getClasses().length,
                representations.size()
        );
    }

    @Test
    void testBuildModifiedTemplate() {
        final Trap template =
                TrapFactory.getInstance().myTemplates.get(0).get(0);
        final Trap modified = TrapFactory.getInstance()
                .buildModifiedTemplate(template, EASY);

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
        final Trap template =
                TrapFactory.getInstance().myTemplates.get(0).get(0);

        areEqual(
                template,
                TrapFactory.getInstance().createFromTemplate(template)
        );
    }

    @Test
    void testCreate() {
        areEqual(
                TrapFactory.getInstance().myTemplates.get(0).get(0),
                TrapFactory.getInstance().create(0, EASY)
        );
    }

    private void areEqual(final Trap trap1, final Trap trap2) {
        assertEquals(trap1.getClassName(), trap2.getClassName());
        assertEquals(trap1.isSingleUse(), trap2.isSingleUse());
        assertEquals(trap1.isBoardable(), trap2.isBoardable());
        assertEquals(trap1.getMinDamage(), trap2.getMinDamage());
        assertEquals(trap1.getMaxDamage(), trap2.getMaxDamage());
        assertEquals(trap1.getHitChance(), trap2.getHitChance());
        assertEquals(trap1.getDebuffChance(), trap2.getDebuffChance());
        assertEquals(trap1.getDebuffDuration(), trap2.getDebuffDuration());
        assertEquals(trap1.getDamageType(), trap2.getDamageType());
        assertEquals(trap1.getSpeed(), trap2.getSpeed());
        assertEquals(trap1.charRepresentation(), trap2.charRepresentation());
    }
}
