package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RoomApplicableItemTest {

    private static final RoomApplicableItem PLANKS = new Planks(999);

    @Test
    void testUseSuccess() {
        assertEquals(PLANKS.applyEffect(buildRoom()), PLANKS.use(buildRoom()));
    }

    @Test
    void testUseFail() {
        final Room room = buildRoom();
        PLANKS.use(room);

        assertEquals(Item.CANNOT_USE_HERE, PLANKS.use(room));
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
