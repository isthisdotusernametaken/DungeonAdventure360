package model;

import java.sql.SQLException;

/**
 * This interface class contain method that will be
 * implemented by SQLite database subclasses, the class helps
 * to prevent duplicate method.
 */
public interface Table {

    /**
     * Template method for next method.
     *
     * @return              The boolean true or false.
     * @throws SQLException Thrown if there is exception that provides
     *                      information on a database access error or
     *                      other errors.
     */
    boolean next() throws SQLException;

    /**
     * Template method for next getRow method.
     *
     * @return              The integer value.
     * @throws SQLException Thrown if there is exception that provides
     *                      information on a database access error or
     *                      other errors.
     */
    int getRow() throws SQLException;

    /**
     * Template method for getString method.
     *
     * @return              The string.
     * @throws SQLException Thrown if there is exception that provides
     *                      information on a database access error or
     *                      other errors.
     */
    String getString(int theColumn) throws SQLException;

    /**
     * Template method for getInt method.
     *
     * @return              The integer value.
     * @throws SQLException Thrown if there is exception that provides
     *                      information on a database access error or
     *                      other errors.
     */
    int getInt(int theColumn) throws SQLException;

    /**
     * Template method for getDouble method.
     *
     * @return              The double.
     * @throws SQLException Thrown if there is exception that provides
     *                      information on a database access error or
     *                      other errors.
     */
    double getDouble(int theColumn) throws SQLException;

    /**
     * Template method for getBoolean method.
     *
     * @return              The boolean true or false.
     * @throws SQLException Thrown if there is exception that provides
     *                      information on a database access error or
     *                      other errors.
     */
    boolean getBoolean(int theColumn) throws SQLException;

    /**
     * Template method for wasNull method.
     *
     * @return              The boolean true or false.
     * @throws SQLException Thrown if there is exception that provides
     *                      information on a database access error or
     *                      other errors.
     */
    boolean wasNull() throws SQLException;
}
