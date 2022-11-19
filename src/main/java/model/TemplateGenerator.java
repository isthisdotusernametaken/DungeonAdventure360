package model;

import java.sql.SQLException;
import java.util.Scanner;

public class TemplateGenerator {

    private static final String INVALID_TABLE =
            "No such table exists: ";
    private static final String NULL_FIELD =
            "Field is null: ";
    private static final String INVALID_RESISTANCE_DATA =
            "Invalid resistance data: ";
    private static final String CHAR_TOO_LONG =
            "Invalid individual char: ";
    private static final String INVALID_PROBABILITY =
            "Probability out of range: ";

    private final Table myTable;
    private final String myTableName;
    private int myColumn;

    TemplateGenerator(final String theTable)
            throws IllegalArgumentException {
        myTable = DBManager.readTable(theTable);
        exceptionOnNoTable(theTable);

        myTableName = theTable;
        myColumn = 1;
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
            exceptionOnIllegalProbability(resistanceValues[i]);
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
        exceptionOnIllegalProbability(field);

        return field;
    }

    boolean getBoolean() throws SQLException, IllegalArgumentException {
        final boolean field = myTable.getBoolean(myColumn++);
        exceptionOnNull();

        return field;
    }

    private String getFieldLocation() throws SQLException {
        return "(" +
                myTableName +
                ": row " + myTable.getRow() +
                ", column " + myColumn +
                ")";
    }

    private void exceptionOnNoTable(final String theTable)
            throws IllegalArgumentException {
        if (myTable == null) {
            throw new IllegalArgumentException(
                    INVALID_TABLE + theTable
            );
        }
    }

    private void exceptionOnNull()
            throws SQLException, IllegalArgumentException {
        if (myTable.wasNull()) {
            throw new IllegalArgumentException(
                    NULL_FIELD + myColumn + getFieldLocation()
            );
        }
    }

    private void resistanceDataException(final String theField)
            throws SQLException, IllegalArgumentException {
        throw new IllegalArgumentException(
                INVALID_RESISTANCE_DATA + theField + getFieldLocation()
        );
    }

    private void exceptionOnMultipleChars(final String theField)
            throws SQLException, IllegalArgumentException {
        if (theField.length() != 1) {
            throw new IllegalArgumentException(
                    CHAR_TOO_LONG + theField + getFieldLocation()
            );
        }
    }

    private void exceptionOnIllegalProbability(final double theProbability)
            throws SQLException, IllegalArgumentException {
        if (theProbability < 0.0 || theProbability > 1.0) {
            throw new IllegalArgumentException(
                    INVALID_PROBABILITY + theProbability + getFieldLocation()
            );
        }
    }
}
