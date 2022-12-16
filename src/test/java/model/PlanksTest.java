package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PlanksTest {

    private static final Planks PLANKS = new Planks(999);

    @Test
    void testApplyEffectSuccess() {
        final String expected = "Trap boarded.\n";

        assertEquals(expected, PLANKS.applyEffect(buildRoom()));
    }

    @Test
    void testApplyEffectFail() {
        final Room room = buildRoom();

        PLANKS.applyEffect(room);
        assertEquals("", PLANKS.applyEffect(room)); // Already boarded
    }

    @Test
    void testCopyType() {
        assertEquals(PLANKS.getType(), PLANKS.copy().getType());
    }

    @Test
    void testCopyCount() {
        assertEquals(PLANKS.getCount(), PLANKS.copy().getCount());
    }

    @Test
    void testGetName() {
        final String expected = "Planks";

        assertEquals(expected, PLANKS.getName());
    }

    private Room buildRoom() {
        return new Room(
                new boolean[] {true, true, true, true},
                new Trap(
                        "Pit",
                        true,
                        true,
                        1,
                        2,
                        0.0,
                        0.0,
                        0,
                        DamageType.SHARP,
                        0,
                        'P'
                ),
                null,
                false,
                false
        );
    }
}
