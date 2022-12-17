package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static model.SpeedTest.*;
import static model.SpeedTest.LinearEquation.*;

public class LinearEquationTest {

    @Test
    void testConstructor() {
        final LinearEquation eq = new LinearEquation(0.0, 0.0, 1.0, 1.0);

        assertEquals(calculateSlope(0.0, 0.0, 1.0, 1.0), eq.mySlope);
        assertEquals(calculateOffset(0.0, 0.0, eq.mySlope), eq.myOffset);
    }

    @Test
    void testCalculateSlope() {
        assertEquals(2.0, calculateSlope(0.0, 0.0, 1.0, 2.0), 0.0001);
    }

    @Test
    void testCalculateOffset() {
        assertEquals(-1.0, calculateOffset(1.0, 1.0, 2.0), 0.0001);
    }

    @Test
    void testEvaluate() {
        assertEquals(
                10.0, new LinearEquation(0.0, 0.0, 1.0, 2.5).evaluate(4.0),
                0.0001
        );
    }
}
