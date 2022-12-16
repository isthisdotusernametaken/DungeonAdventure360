package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemTest {

    private static final int SIZE = 4;
    private static final Item[] myItems = new Item[SIZE];

    @BeforeEach
    void createObjectArray() {
       myItems[0] = new Planks(1);
       myItems[1] = new VisionPotion(1);
       myItems[2] = new HealthPotion(1);
       myItems[3] = new BuffPotion(1, BuffType.STRENGTH);
    }

    @Test
    void testCreateNameFromType() {
        String[] expected = {
                "Planks",
                "Vision Potion",
                "Health Potion",
                "Strength Potion"
        };

        for (int i = 0; i < SIZE; i++) {
            assertEquals(expected[i], myItems[i].getName());
        }
    }

    @Test
    void testToString() {
        String[] expected = {
                "Planks: 1",
                "Vision Potion: 1",
                "Health Potion: 1",
                "Strength Potion: 1"
        };

        for (int i = 0; i < SIZE; i++) {
            assertEquals(expected[i], myItems[i].toString());
        }
    }

    @Test
    void testCharRepresentation() {
        char[] expected = {'=', 'V', 'H', 'S'};

        for (int i = 0; i < SIZE; i++) {
            assertEquals(expected[i], myItems[i].charRepresentation());
        }
    }

//    @Test
//    void testCanChangeCount() {
//        boolean[] expected = {
//                true,
//                true,
//                true,
//                true
//        };
//        for (int i = 0; i < SIZE; i++) {
//            assertEquals(expected[i], myItems[i].canChangeCount());
//        }
//    }

    @Test
    void testGetCount() {
        int[] expected = {
                1,
                1,
                1,
                1
        };

        for (int i = 0; i < SIZE; i++) {
            assertEquals(expected[i], myItems[i].getCount());
        }
    }

    @Test
    void testAddToStack() {
        int[] expected = {
                997,
                998,
                999,
                999
        };

        for (int i = 0; i < SIZE; i++) {
            myItems[i].addToStack(996 + i);
            assertEquals(expected[i], myItems[i].getCount());
        }
    }

    @Test
    void testConsume() {
        int[] expected = {
                990,
                991,
                992,
                993
        };

        for (int i = 0; i < SIZE; i++) {
            myItems[i].addToStack(990 + i);
            myItems[i].consume();
            assertEquals(expected[i], myItems[i].getCount());
        }
    }

    @Test
    void testIsSameType() {
        Item expected = myItems[1];

        assertTrue(expected.isSameType(new VisionPotion(2)));
    }

}
