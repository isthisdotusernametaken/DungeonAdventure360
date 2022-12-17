package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles and manage the SQL database table for the dungeon
 * adventure game.
 */
public class ResultSetTable implements Table {

    /**
     * The result set database table.
     */
    private final ResultSet myResultSet;

    /**
     * Constructor to construct the result set database table.
     *
     * @param theResultSet  The result set table.
     */
    ResultSetTable(final ResultSet theResultSet) {
        myResultSet = theResultSet;
    }

    /**
     * Moves the cursor forward on row from its current position.
     *
     * @return                   The next row in the result set table.
     *
     * @throws SQLException      Thrown if there is exception that provides
     *                           information on a database access error
     *                           or other errors.
     */
    @Override
    public boolean next() throws SQLException {
        return myResultSet.next();
    }

    /**
     * Gets the current row from current position.
     *
     * @return                   The current row in the result set table.
     *
     * @throws SQLException      Thrown if there is exception that provides
     *                           information on a database access error
     *                           or other errors.
     */
    @Override
    public int getRow() throws SQLException {
        return myResultSet.getRow();
    }

    /**
     * Gets the string from current position.
     *
     * @param theColumn          The integer value representing the column
     *                           location.
     * @return                   The string from current position.
     *
     * @throws SQLException      Thrown if there is exception that provides
     *                           information on a database access error
     *                           or other errors.
     */
    @Override
    public String getString(final int theColumn) throws SQLException {
        return myResultSet.getString(theColumn);
    }


    /**
     * Gets the integer value from current position.
     *
     * @param theColumn          The integer value representing the column
     *                           location.
     * @return                   The integer value from current position.
     *
     * @throws SQLException      Thrown if there is exception that provides
     *                           information on a database access error
     *                           or other errors.
     */
    @Override
    public int getInt(final int theColumn) throws SQLException {
        return myResultSet.getInt(theColumn);
    }

    /**
     * Gets the double value from current position.
     *
     * @param theColumn          The integer value representing the column
     *                           location.
     * @return                   The double value from current position.
     *
     * @throws SQLException      Thrown if there is exception that provides
     *                           information on a database access error
     *                           or other errors.
     */
    @Override
    public double getDouble(final int theColumn) throws SQLException {
        return myResultSet.getDouble(theColumn);
    }

    /**
     * Gets the boolean value from current position.
     *
     * @param theColumn          The integer value representing the column
     *                           location.
     * @return                   The boolean value from current position.
     *
     * @throws SQLException      Thrown if there is exception that provides
     *                           information on a database access error
     *                           or other errors.
     */
    @Override
    public boolean getBoolean(final int theColumn) throws SQLException {
        return myResultSet.getBoolean(theColumn);
    }

    /**
     * Check if the last column location read was null.
     *
     * @return                   The boolean true or false if the
     *                           last column location read was null.
     *
     * @throws SQLException      Thrown if there is exception that provides
     *                           information on a database access error
     *                           or other errors.
     */
    @Override
    public boolean wasNull() throws SQLException {
        return myResultSet.wasNull();
    }
}
