/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * This class iterates through a DB table and parses and validates input from
 * the DB for the factories to use.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class TemplateGenerator {

    /**
     * Error indicating a field has an invalid value
     */
    private static final String INVALID_FIELD =
            "Invalid field (possibly wrong data type): ";
    /**
     * Error indicating a field is nul
     */
    private static final String NULL_FIELD =
            "Field is null";
    /**
     * Error indicating a field could not be interpreted as ResistanceData
     */
    private static final String INVALID_RESISTANCE_DATA =
            "Invalid resistance data: ";
    /**
     * Error indicating a field could not be interpreted as DamageType
     */
    private static final String INVALID_DAMAGE_TYPE =
            "Invalid damage type: ";
    /**
     * Error indicating a field could not be interpreted as a single char
     */
    private static final String INVALID_CHAR_LENGTH =
            "Invalid individual char: ";
    /**
     * Error indicating an int is too small or large
     */
    private static final String INVALID_INT =
            "Integer out of range: ";
    /**
     * Error indicating a double is too small or large (not in [0,1])
     */
    private static final String INVALID_PROBABILITY =
            "Probability out of range: ";

    /**
     * The DB table to read data from
     */
    private final Table myTable;
    /**
     * The name of the DB table
     */
    private final String myTableName;
    /**
     * The current column in the table
     */
    private int myColumn;

    /**
     * Creates a TemplateGenerator to parse the specified table from the
     * specified DB
     *
     * @param theDBManager The DB to read data from.
     * @param theTable The name of the table to read from the DB
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    TemplateGenerator(final DBManager theDBManager, final String theTable)
            throws SQLException, IllegalArgumentException {
        myTable = theDBManager.readTable(theTable);

        myTableName = theTable;
        myColumn = 1;
    }

    /**
     * Advances to the next row and indicates whether the next row exists
     *
     * @return Whether the table has another row
     * @throws SQLException Indicates a failure while reading from the DB.
     */
    boolean next() throws SQLException {
        myColumn = 1;
        return myTable.next();
    }

    /**
     * Parses a SpecialSkill from a field in the table
     *
     * @return a SpecialSkill from the current field
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
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

    /**
     * Parses a ResistanceData object from a field in the table
     *
     * @return resistance values from the current field
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
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

    /**
     * Parses a DamageType from a field in the table
     *
     * @return a DamageType from the current field
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
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

    /**
     * Reads a String from a field in the table
     *
     * @return a String from the current field
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    String getString() throws SQLException, IllegalArgumentException {
        try {
            final String field = myTable.getString(myColumn++);
            exceptionOnNull();

            return field;
        } catch (SQLException e) {
            throw invalidFieldException();
        }
    }

    /**
     * Parses a char from a field in the table
     *
     * @return a char from the current field
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    char getChar() throws SQLException, IllegalArgumentException {
        try {
            final String field = getString();
            exceptionOnInvalidCharLength(field);

            return field.charAt(0);
        } catch (SQLException e) {
            throw invalidFieldException();
        }
    }

    /**
     * Reads an int from a field in the table
     *
     * @return an int from the current field
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
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

    /**
     * Reads a double from a field in the table
     *
     * @return a double from the current field
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
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

    /**
     * Reads a boolean from a field in the table
     *
     * @return a boolean from the current field
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    boolean getBoolean() throws SQLException, IllegalArgumentException {
        try {
            final boolean field = myTable.getBoolean(myColumn++);
            exceptionOnNull();

            return field;
        } catch (SQLException e) {
            throw invalidFieldException();
        }
    }

    /**
     * Gets the most recently read field's row and column for an error message.
     *
     * @return The most recently read field's row and column
     * @throws SQLException Indicates a failure while reading from the DB.
     */
    private String getFieldLocation() throws SQLException {
        return " (" +
                myTableName +
                ": row " + myTable.getRow() +
                ", column " + (myColumn - 1) +
                ")";
    }

    /**
     * Creates an exception indicating the current field has an invalid value
     *
     * @return an exception with information about the invalid field
     * @throws SQLException Indicates a failure while reading from the DB.
     */
    private SQLException invalidFieldException() throws SQLException {
        return new SQLException(
                INVALID_FIELD + getFieldLocation()
        );
    }

    /**
     * Throws an IllegalArgumentException if the most recent field was null
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates a null field.
     */
    private void exceptionOnNull()
            throws SQLException, IllegalArgumentException {
        if (myTable.wasNull()) {
            throw new IllegalArgumentException(
                    NULL_FIELD + getFieldLocation()
            );
        }
    }

    /**
     * Throws an IllegalArgumentException because the field could not be parsed
     * as resistance values
     *
     * @param theField The invalid field
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException The field could not be parsed as a
     *                                  ResistanceData object.
     */
    private void resistanceDataException(final String theField)
            throws SQLException, IllegalArgumentException {
        throw new IllegalArgumentException(
                INVALID_RESISTANCE_DATA + theField + getFieldLocation()
        );
    }

    /**
     * Throws an IllegalArgumentException if the field could not be parsed
     * as a char
     *
     * @param theField The invalid field
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates the field could not be parsed
     *                                  as a single char.
     */
    private void exceptionOnInvalidCharLength(final String theField)
            throws SQLException, IllegalArgumentException {
        if (theField.length() != 1) {
            throw new IllegalArgumentException(
                    INVALID_CHAR_LENGTH + theField + getFieldLocation()
            );
        }
    }

    /**
     * Throws an IllegalArgumentException if the int in the field is too small
     * or large
     *
     * @param theInt The invalid field
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates the int is too small or
     *                                  large.
     */
    private void exceptionOnIllegalInt(final int theInt)
            throws SQLException, IllegalArgumentException {
        if (theInt < 1 || theInt > Util.MAX_INT) {
            throw new IllegalArgumentException(
                    INVALID_INT + theInt + getFieldLocation()
            );
        }
    }

    /**
     * Throws an IllegalArgumentException if the double in the field is too
     * small or large
     *
     * @param theProbability The invalid field
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates the probability is too small
     *                                  or large.
     */
    private void exceptionOnIllegalProbability(final double theProbability)
            throws SQLException, IllegalArgumentException {
        if (theProbability < 0.0 || theProbability > 1.0) {
            throw new IllegalArgumentException(
                    INVALID_PROBABILITY + theProbability + getFieldLocation()
            );
        }
    }
}
