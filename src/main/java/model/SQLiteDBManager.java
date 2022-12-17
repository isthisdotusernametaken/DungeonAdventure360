package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

/**
 * This class handles and manages the SQL database.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class SQLiteDBManager implements DBManager {

    /**
     * The file name of database.
     */
    private static final String DB_FILE = "dungeon_adventure.db";

    /**
     * The query for the database table.
     */
    private static final String TABLE_QUERY = "SELECT * FROM ";

    /**
     * Alerts fail connection.
     */
    private static final String CONNECT_ERROR = "Database connection to " +
                                                DB_FILE +
                                                " could not be established.";

    /**
     * Alerts reading failure.
     */
    private static final String READ_ERROR = "Database query could not be " +
                                             "completed for the table: ";

    /**
     * The connection object to open SQL database connection.
     */
    private final Connection myConnection;

    /**
     * The statement object to execute the SQL database statements.
     */
    private final Statement myStatement;

    /**
     * Constructor to construct the SQL database.
     *
     * @throws SQLException Thrown if there is exception that provides information
     *                      on a database access error or other errors.
     */
    SQLiteDBManager() throws SQLException {
        final SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + DB_FILE);

        try {
            myConnection = dataSource.getConnection();
            myStatement = myConnection.createStatement();
        } catch (SQLException e) {
            close();

            throw new SQLException(CONNECT_ERROR, e);
        }
    }

    /**
     * Accesses and reads the SQL database table.
     *
     * @param theTable      The string representing the table component.
     * @return              The result set table.
     * @throws SQLException Thrown if there is exception that provides
     *                      information on a database access error or
     *                      other errors.
     */
    public ResultSetTable readTable(final String theTable)
            throws SQLException {
        try {
            return new ResultSetTable(
                    myStatement.executeQuery(TABLE_QUERY + theTable)
            );
        } catch (SQLException e) {
            throw new SQLException(READ_ERROR + theTable + ".", e);
        }
    }

    /**
     * Closes the process of opening connection and reading statement
     * in SQL database.
     *
     * @throws SQLException Thrown if there is exception that provides
     *                      information on a database access error or
     *                      other errors.
     */
    public void close() throws SQLException {
        myConnection.close();
        myStatement.close();
    }
}
