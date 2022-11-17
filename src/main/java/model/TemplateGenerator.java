package model;

import java.sql.SQLException;
import java.util.Scanner;

public class TemplateGenerator {

    static final String INVALID_RESISTANCE_DATA =
            "Invalid resistance data: ";
    static final String CHAR_TOO_LONG =
            "Invalid individual char: ";
    static final String NULL_FIELD =
            "Field is null: ";
    static final String INVALID_TABLE =
            "No such table exists: ";

    final Table myTable;
    int myColumn;

    TemplateGenerator(final String theTable) throws IllegalArgumentException {
        myTable = MockDBManager.readTable(theTable);
        exceptionOnNoTable(theTable);

        myColumn = 1;
    }

    static void resistanceDataException(final String theField)
            throws IllegalArgumentException {
        throw new IllegalArgumentException(
                INVALID_RESISTANCE_DATA + theField
        );
    }

    static void exceptionOnMultipleChars(final String theField)
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

    SpecialSkill getSpecialSkill()
            throws SQLException, IllegalArgumentException {
        return SpecialSkillFactory.createSpecialSkill(getString());
    }

    ResistanceData getResistanceData()
            throws SQLException, IllegalArgumentException {
        final String resistances = getString();
        final Scanner scanner = new Scanner(resistances.trim());
        final double[] resistanceValues =
                new double[DamageType.values().length];

        for (int i = 0; i < resistanceValues.length; i++) {
            if (!scanner.hasNextDouble()) {
                resistanceDataException(resistances);
            }

            resistanceValues[i] = scanner.nextDouble();
        }

        if (scanner.hasNext()) { // Invalid because of extra data in field
            resistanceDataException(resistances);
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

    void exceptionOnNull()
            throws SQLException, IllegalArgumentException {
        if (myTable.wasNull()) {
            throw new IllegalArgumentException(
                    NULL_FIELD + myColumn
            );
        }
    }

    void exceptionOnNoTable(final String theTable)
            throws IllegalArgumentException {
        if (myTable == null) {
            throw new IllegalArgumentException(
                    INVALID_TABLE + theTable
            );
        }
    }
}
