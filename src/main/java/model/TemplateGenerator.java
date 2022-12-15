package model;

import java.sql.SQLException;
import java.util.Scanner;

public class TemplateGenerator {

    static final String INVALID_FIELD =
            "Invalid field (possibly wrong data type): ";
    static final String NULL_FIELD =
            "Field is null";
    static final String INVALID_RESISTANCE_DATA =
            "Invalid resistance data: ";
    static final String INVALID_DAMAGE_TYPE =
            "Invalid damage type: ";
    static final String INVALID_CHAR_LENGTH =
            "Invalid individual char: ";
    static final String INVALID_INT =
            "Integer out of range: ";
    static final String INVALID_PROBABILITY =
            "Probability out of range: ";

    final Table myTable;
    final String myTableName;
    int myColumn;

    TemplateGenerator(final DBManager theDBManager, final String theTable)
            throws SQLException, IllegalArgumentException {
        myTable = theDBManager.readTable(theTable);

        myTableName = theTable;
        myColumn = 1;
    }

    boolean next() throws SQLException {
        myColumn = 1;
        return myTable.next();
    }

    SpecialSkill getSpecialSkill()
            throws SQLException, IllegalArgumentException {
        try {
            return SpecialSkillFactory.createSpecialSkill(getString());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    e.getMessage() + getFieldLocation()
            );
        }
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

    DamageType getDamageType() throws SQLException, IllegalArgumentException {
        final String field = getString();
        try {
            return DamageType.valueOf(field);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    INVALID_DAMAGE_TYPE + field + getFieldLocation()
            );
        }
    }

    String getString() throws SQLException, IllegalArgumentException {
        try {
            final String field = myTable.getString(myColumn++);
            exceptionOnNull();

            return field;
        } catch (SQLException e) {
            throw invalidFieldException();
        }
    }

    char getChar() throws SQLException, IllegalArgumentException {
        try {
            final String field = getString();
            exceptionOnInvalidCharLength(field);

            return field.charAt(0);
        } catch (SQLException e) {
            throw invalidFieldException();
        }
    }

    int getInt() throws SQLException, IllegalArgumentException {
        try {
            final int field = myTable.getInt(myColumn++);
            exceptionOnNull();
            exceptionOnIllegalInt(field);

            return field;
        } catch (SQLException e) {
            throw invalidFieldException();
        }
    }

    double getDouble() throws SQLException, IllegalArgumentException {
        try {
            final double field = myTable.getDouble(myColumn++);
            exceptionOnNull();
            exceptionOnIllegalProbability(field);

            return field;
        } catch (SQLException e) {
            throw invalidFieldException();
        }
    }

    boolean getBoolean() throws SQLException, IllegalArgumentException {
        try {
            final boolean field = myTable.getBoolean(myColumn++);
            exceptionOnNull();

            return field;
        } catch (SQLException e) {
            throw invalidFieldException();
        }
    }

    String getFieldLocation() throws SQLException {
        return " (" +
                myTableName +
                ": row " + myTable.getRow() +
                ", column " + (myColumn - 1) +
                ")";
    }

    SQLException invalidFieldException() throws SQLException {
        return new SQLException(
                INVALID_FIELD + getFieldLocation()
        );
    }

    void exceptionOnNull()
            throws SQLException, IllegalArgumentException {
        if (myTable.wasNull()) {
            throw new IllegalArgumentException(
                    NULL_FIELD + getFieldLocation()
            );
        }
    }

    void resistanceDataException(final String theField)
            throws SQLException, IllegalArgumentException {
        throw new IllegalArgumentException(
                INVALID_RESISTANCE_DATA + theField + getFieldLocation()
        );
    }

    void exceptionOnInvalidCharLength(final String theField)
            throws SQLException, IllegalArgumentException {
        if (theField.length() != 1) {
            throw new IllegalArgumentException(
                    INVALID_CHAR_LENGTH + theField + getFieldLocation()
            );
        }
    }

    void exceptionOnIllegalInt(final int theInt)
            throws SQLException, IllegalArgumentException {
        if (theInt < 1 || theInt > Util.MAX_INT) {
            throw new IllegalArgumentException(
                    INVALID_INT + theInt + getFieldLocation()
            );
        }
    }

    void exceptionOnIllegalProbability(final double theProbability)
            throws SQLException, IllegalArgumentException {
        if (theProbability < 0.0 || theProbability > 1.0) {
            throw new IllegalArgumentException(
                    INVALID_PROBABILITY + theProbability + getFieldLocation()
            );
        }
    }
}
