package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ItemTest {

    private static final int SIZE = 4;
    private Item[] myItems = new Item[SIZE];

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

    @Test
    void testCanChangeCount() {
        boolean[] expected = {
                true,
                true,
                true,
                true
        };
        for (int i = 0; i < SIZE; i++) {
            assertEquals(expected[i], myItems[i].canChangeCount());
        }
    }

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
                10000,
                10001,
                10002,
                10003
        };
        for (int i = 0; i < SIZE; i++) {
            myItems[i].addToStack(9999 + i);
            assertEquals(expected[i], myItems[i].getCount());
        }
    }

    @Test
    void testConsume() {
        int[] expected = {
                9999,
                10000,
                10001,
                10002
        };
        for (int i = 0; i < SIZE; i++) {
            myItems[i].addToStack(9999 + i);
            myItems[i].consume();
            assertEquals(expected[i], myItems[i].getCount());
        }
    }

    @Test
    void testIsSameType() {
        assertTrue(myItems[1].isSameType(new VisionPotion(2)));
    }

}
