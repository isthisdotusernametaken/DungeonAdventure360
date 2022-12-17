package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static model.SpeedTest.*;

public class SpeedTestTest {

    @Test
    void testEvaluateDamageDealers() {
        // adventurer exactly twice as fast as monster, so exactly halfway
        // between second (1) and third (3) endpoints
        final Adventurer adventurer = new Adventurer(
                "", "", 1, 1, 1, 0.0, 0.0, 0, DamageType.SHARP, 10, 0.0,
                new ResistanceData(new double[]{0.0, 0.0, 0.0, 0.0, 0.0}),
                new CrushingBlow()
        );
        final Monster monster = new Monster(
                "", "", 1, 1, 1, 0.0, 0.0, 0, DamageType.SHARP, 5, 0.0,
                0.0,
                new ResistanceData(new double[]{0.0, 0.0, 0.0, 0.0, 0.0})
        );

        assertEquals((MID_Y + END_Y) / 2, evaluate(adventurer, monster), 0.0001);
    }

    @Test
    void testEvaluateRatioInRange() {
        assertEquals((MID_Y + END_Y) / 2, evaluate(2.0), 0.0001);
    }

    @Test
    void testEvaluateRatioLowerClamp() {
        assertEquals(START_Y, evaluate(0.05), 0.0001);
    }

    @Test
    void testEvaluateRatioUpperClamp() {
        assertEquals(END_Y, evaluate(10.0), 0.0001);
    }
}
