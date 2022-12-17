package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DamageDealerFactoryTest {

    @BeforeAll
    static void ensureFactoriesBuilt() {
        DungeonAdventure.buildFactories();
    }

    @Test
    void testGetClassesCorrectNames() {
        assertArrayEquals(
                AdventurerFactory.getInstance().myTemplates.get(0).stream()
                        .map(DamageDealer::getClassName).toArray(String[]::new),
                AdventurerFactory.getInstance().getClasses()
        );

        assertArrayEquals(
                MonsterFactory.getInstance().myTemplates.get(0).stream()
                        .map(DamageDealer::getClassName).toArray(String[]::new),
                MonsterFactory.getInstance().getClasses()
        );

        assertArrayEquals(
                TrapFactory.getInstance().myTemplates.get(0).stream()
                        .map(DamageDealer::getClassName).toArray(String[]::new),
                TrapFactory.getInstance().getClasses()
        );
    }

    @Test
    void testIsValidIndexMin() {
        assertTrue(AdventurerFactory.getInstance().isValidIndex(0));
    }

    @Test
    void testIsValidIndexMax() {
        assertTrue(
                AdventurerFactory.getInstance().isValidIndex(
                        AdventurerFactory.getInstance().myTemplates.get(0)
                                .size() - 1
                )
        );
    }

    @Test
    void testIsValidIndexTooSmall() {
        assertFalse(AdventurerFactory.getInstance().isValidIndex(-1));
    }

    @Test
    void testIsValidIndexTooLarge() {
        assertFalse(
                AdventurerFactory.getInstance().isValidIndex(
                        AdventurerFactory.getInstance().myTemplates.get(0)
                                .size()
                )
        );
    }
}
