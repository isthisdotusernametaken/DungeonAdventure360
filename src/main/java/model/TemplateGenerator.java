package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TemplateGenerator {

    private static final String INVALID_RESISTANCE_DATA =
            "Invalid resistance data: ";
    private static final String CHAR_TOO_LONG =
            "Invalid individual char: ";
    private static final String NULL_FIELD = "Field is null: ";

    private final ResultSet myTable;
    private int myColumn;

    TemplateGenerator(final String theTable) {
        myTable = DBManager.readTable(theTable);
        myColumn = 1;
    }

    private static void resistanceDataException(final String theField)
            throws IllegalArgumentException {
        throw new IllegalArgumentException(
                INVALID_RESISTANCE_DATA + theField
        );
    }

    private static void exceptionOnMultipleChars(final String theField)
            throws IllegalArgumentException {
        if (theField.length() != 1) {
            throw new IllegalArgumentException(
                    CHAR_TOO_LONG + theField
            );
        }
    }

    boolean next() throws SQLException {
        return myTable.next();
    }

    ResistanceData getResistanceData()
            throws SQLException, IllegalArgumentException {
        final String theResistances = getString();
        final Scanner scanner = new Scanner(theResistances.trim());
        final double[] resistanceValues =
                new double[DamageType.values().length];

        for (int i = 0; i < resistanceValues.length; i++) {
            if (!scanner.hasNextDouble()) {
                resistanceDataException(theResistances);
            }

            resistanceValues[i] = scanner.nextDouble();
        }

        if (scanner.hasNext()) { // Invalid because of extra data in field
            resistanceDataException(theResistances);
        }

        return new ResistanceData(resistanceValues);
    }

    String getString() throws SQLException, IllegalArgumentException {
        final String field = myTable.getString(myColumn++);
        exceptionOnNull();

        return field;
    }

    char getChar() throws SQLException, IllegalArgumentException {
        final String field = getString();
        exceptionOnMultipleChars(field);

        return field.charAt(0);
    }

    int getInt() throws SQLException, IllegalArgumentException {
        final int field = myTable.getInt(myColumn++);
        exceptionOnNull();

        return field;
    }

    double getDouble() throws SQLException, IllegalArgumentException {
        final double field = myTable.getDouble(myColumn++);
        exceptionOnNull();

        return field;
    }

    boolean getBoolean() throws SQLException, IllegalArgumentException {
        final boolean field = myTable.getBoolean(myColumn++);
        exceptionOnNull();

        return field;
    }

    private void exceptionOnNull()
            throws SQLException, IllegalArgumentException {
        if (myTable.wasNull()) {
            throw new IllegalArgumentException(
                    NULL_FIELD + myColumn
            );
        }
    }
}
