package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private static final SQLiteDataSource DATA_SOURCE = createDataSource();

    private static SQLiteDataSource createDataSource() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + DB_FILE);

        return dataSource;
    }

    private static Connection connect() throws SQLException {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new SQLException(CONNECT_ERROR, e);
        }
    }

    public ResultSetTable readTable(final String theTable)
            throws SQLException {
        try (Connection connection = connect();
             Statement stmt = connection.createStatement()) {
            return new ResultSetTable(
                    stmt.executeQuery(TABLE_QUERY + theTable)
            );
        } catch (SQLException e) {
            throw new SQLException(READ_ERROR + theTable + ".", e);
        }
    }
}
