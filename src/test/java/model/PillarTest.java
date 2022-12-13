package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PillarTest {
    private static final Pillar myPillar = new Pillar(ItemType.ABSTRACTION, 'H');
    private static final Pillar[] myAPillar = Pillar.createPillars();

    @Test
    void testCopy() {
        Item myItem = new Pillar(ItemType.ABSTRACTION, 'H') ;
        ItemType expected = myItem.getType();

        assertEquals(
                expected,
                myPillar.copy().getType());
    }

    @Test
    void testCreatePillars() {
        Pillar[] expected = {
                new Pillar(ItemType.ABSTRACTION, 'H'),
                new Pillar(ItemType.ENCAPSULATION, 'E'),
                new Pillar(ItemType.INHERITANCE, 'I'),
                new Pillar(ItemType.POLYMORPHISM, 'P')
        };

        for (int i = 0; i < expected.length; i++) {
            assertEquals(
                    expected[i].getType(),
                    myAPillar[i].getType());
        }
    }

    @Test
    void testGetName() {
        String expected = "Abstraction";

        assertEquals(expected, myPillar.getName());
    }
}
