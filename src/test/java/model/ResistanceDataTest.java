package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static model.TestingUtil.assertResistanceDataEqualsArray;

public class ResistanceDataTest {

    @Test
    void testConstructorInitializeArray() {
        final double[] resistances = new double[DamageType.values().length];
        for (int i = 0; i < resistances.length; i++) {
            resistances[i] = 0.1 * i;
        }

        assertResistanceDataEqualsArray(
                resistances, new ResistanceData(resistances)
        );
    }

    @Test
    void testGetResistance() {
        // No case to handle unexpected arguments is necessary because only
        // AdjustedCharacterStats uses this method, and its array size is
        // determined by the corresponding character's ResistanceData's array
        // size
        final ResistanceData resistances = new ResistanceData(
                new double[DamageType.values().length]
        );
        final int index = 0;
        final double resistance = 0.5;
        resistances.myResistances[index] = resistance;

        assertEquals(resistance, resistances.getResistance(index));
    }
}
