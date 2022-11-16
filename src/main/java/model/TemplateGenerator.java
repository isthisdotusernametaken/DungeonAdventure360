package model;

import java.sql.ResultSet;
import java.util.Scanner;

public class TemplateGenerator {

    private static final String INVALID_RESISTANCE_DATA =
            "Invalid resistance data: ";

    static ResultSet readTable(final String theTable) {
        return DBManager.readTable(theTable);
    }

    static ResistanceData parseResistances(final String theResistances)
            throws IllegalArgumentException {
        final Scanner scanner = new Scanner(theResistances.trim());
        final double[] resistanceValues = new double[DamageType.values().length];
        for (int i = 0; i < resistanceValues.length; i++) {
            if (!scanner.hasNextDouble()) {
                throw new IllegalArgumentException(INVALID_RESISTANCE_DATA);
            }

            resistanceValues[i] = scanner.nextDouble();
        }

        if (scanner.hasNext()) { // Extra data in field
            throw new IllegalArgumentException(INVALID_RESISTANCE_DATA);
        }

        return new ResistanceData(resistanceValues);
    }
}
