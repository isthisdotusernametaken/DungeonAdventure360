package model;

import org.junit.jupiter.api.Test;

import static model.Direction.*;
import static model.TestingUtil.assertNotNullAndEquals;

public class DirectionTest {

    @Test
    void testToStringNorth() {
        assertNotNullAndEquals("North", NORTH.toString());
    }

    @Test
    void testToStringEast() {
        assertNotNullAndEquals("East", EAST.toString());
    }

    @Test
    void testToStringSouth() {
        assertNotNullAndEquals("South", SOUTH.toString());
    }

    @Test
    void testToStringWest() {
        assertNotNullAndEquals("West", WEST.toString());
    }
}
