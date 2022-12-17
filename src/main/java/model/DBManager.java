package model;

import java.sql.SQLException;

/**
 * This interface class contain method that will be
 * implemented by SQLite database subclass, the class helps
 * to prevent duplicate method.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public interface DBManager {

    /**
     * Template method to create database table.
     *
     * @param theTable      The string representing the table component.
     * @return a DB table
     * @throws SQLException An exception that provides information on a
     *                      database access error or other errors.
     */
    Table readTable(String theTable) throws SQLException;

    /**
     * Template method to close SQlite database process.
     *
     * @throws SQLException An exception that provides information on a
     *                      database access error or other errors.
     */
    void close() throws SQLException;
}
