package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class SQLiteDBManager implements DBManager {

    private static final String DB_FILE = "dungeon_adventure.db";
    private static final String TABLE_QUERY = "SELECT * FROM ";
    private static final String CONNECT_ERROR = "Database connection to " +
                                                DB_FILE +
                                                " could not be established.";
    private static final String READ_ERROR = "Database query could not be " +
                                             "completed for the table: ";

    private final Connection myConnection;
    private final Statement myStatement;

    SQLiteDBManager() throws SQLException{
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + DB_FILE);

        try {
            myConnection = dataSource.getConnection();
            myStatement = myConnection.createStatement();
        } catch (SQLException e) {
            close();

            throw new SQLException(CONNECT_ERROR, e);
        }
    }

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

    public void close() throws SQLException {
        myConnection.close();
        myStatement.close();
    }
}
