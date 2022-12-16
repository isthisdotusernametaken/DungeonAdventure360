package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PlanksTest {

    private static final Planks myPlank = new Planks(999);
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
    void testApplyEffect() {
        String expected = "Trap boarded.\n";

        assertEquals(expected, myPlank.applyEffect(myRoom));
    }

    @Test
    void testCopy() {
        Item expected = myItem;

        assertEquals(expected.getType(), myPlank.copy().getType());
    }

    @Test
    void testGetName() {
        String expected = "Planks";

        assertEquals(expected, myPlank.getName());
    }
}
