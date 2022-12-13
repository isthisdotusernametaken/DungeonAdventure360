package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomApplicableItemTest {

    private static final RoomApplicableItem myPlank = new Planks(999);
    private static final Item myItem = new Planks(999);
    private static final Monster myMonster = new Monster(
            "Skeleton",
            "Skeleton",
            110,
            15,
            25,
            0.5,
            0.1,
            2,
            DamageType.SHARP,
            4,
            0.05,
            0.3,
            new ResistanceData(new double[]{0.1, 0.1, 0.0, 0.2, 0.2})
    );
    private static final Trap myTrap = new Trap(
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
            'P');
    private static final Room myRoom = new Room(
            new boolean[] {true, true, true, true},
            myTrap,
            myMonster,
            true,
            true,
            myItem );

    @Test
    void testUse() {
        String expected = "Trap boarded.\n";

        assertEquals(expected, myPlank.use(myRoom));
    }
}
