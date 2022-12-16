package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PillarTest {

    @Test
    void testCopyIdentical() {
        final Pillar pillar = Pillar.createPillars()[0];
        assertEquals(pillar, pillar.copy());
    }

    @Test
    void testCreatePillars() {
        final Pillar[] expected = {
                new Pillar(ItemType.ABSTRACTION, 'A'),
                new Pillar(ItemType.ENCAPSULATION, 'E'),
                new Pillar(ItemType.INHERITANCE, 'I'),
                new Pillar(ItemType.POLYMORPHISM, 'P')
        };
        final Pillar[] actual = Pillar.createPillars();

        for (int i = 0; i < expected.length; i++) {
            assertEquals(
                    expected[i].getType(),
                    actual[i].getType()
            );
            assertEquals(
                    expected[i].charRepresentation(),
                    actual[i].charRepresentation()
            );
        }
    }

    @Test
    void testGetName() {
        final Pillar pillar = Pillar.createPillars()[3];

        assertEquals("Polymorphism", pillar.getName());
    }
}
