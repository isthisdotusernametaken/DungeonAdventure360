package model;

import org.junit.jupiter.api.Test;

import static model.TestingUtil.assertResistanceDataEqualsArray;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResistanceDataTest {

    private static final ResistanceData RES_DATA = new ResistanceData(
            new double[DamageType.values().length]
    );

    @Test
    void testConstructorInitializeArray() {
        double[] resistances = new double[DamageType.values().length];
        for (int i = 0; i < resistances.length; i++) {
            resistances[i] = 0.1 * i;
        }

        assertResistanceDataEqualsArray(
                resistances, new ResistanceData(resistances)
        );
    }

//    @Test
//    void testGetResistanceDamageType() {
//        DamageType type = DamageType.SHARP;
//        double resistance = 0.777;
//        RES_DATA.myResistances[type.ordinal()] = resistance;
//
//        assertEquals(resistance, RES_DATA.getResistance(type));
//    }

    @Test
    void testGetResistanceInt() {
        int index = 0;
        double resistance = 0.5;
        RES_DATA.myResistances[index] = resistance;

        assertEquals(resistance, RES_DATA.getResistance(index));
    }
}
