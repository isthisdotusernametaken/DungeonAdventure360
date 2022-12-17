package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DungeonCharacterFactoryTest {

    @BeforeAll
    static void ensureFactoriesBuilt() {
        DungeonAdventure.buildFactories();
    }

    @Test
    void testNameGeneratorNotNull() {
        assertNotNull(AdventurerFactory.getInstance().myNameGenerator);
        assertNotNull(MonsterFactory.getInstance().myNameGenerator);
    }

    @Test
    void testGenerateName() {
        final String adventurerName = AdventurerFactory.getInstance().generateName();
        final String monsterName = AdventurerFactory.getInstance().generateName();

        assertNotNull(adventurerName);
        assertNotEquals("",  adventurerName);
        assertNotNull(monsterName);
        assertNotEquals("",  monsterName);
    }
}
