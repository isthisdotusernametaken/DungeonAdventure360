package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ResistanceDataTest {

    private ResistanceData myInstance;

    @BeforeEach
    void createInstance() {
        myInstance = new ResistanceData();
    }

    @Test
    void testGetResistanceDamageType() {
        DamageType type = DamageType.SHARP;
        double value = 0.777;
        myInstance.myResistances[type.ordinal()] = value;

        assertEquals(value, myInstance.getResistance(type));
    }

    @Test
    void testGetResistanceInt() {
        int index = 0;
        double value = 0.5;
        myInstance.myResistances[index] = value;

        assertEquals(value, myInstance.getResistance(index));
    }

    @Test
    void testAllUnspecified() {
        double[] resistances = myInstance.allUnspecified();

        for (double resistance : resistances) {
            assertEquals(
                    ResistanceData.UNSPECIFIED_RESISTANCE,
                    resistance
            );
        }
    }

    @Test
    void testSetResistances() {

    }

    @Test
    void testIsUnspecifiedNotUnspecified() {
        assertFalse(
                myInstance.isUnspecified(0)
        );
    }

    @Test
    void testIsUnspecifiedUnspecified() {
        assertTrue(
                myInstance.isUnspecified(
                        ResistanceData.UNSPECIFIED_RESISTANCE
                )
        );
    }
}
