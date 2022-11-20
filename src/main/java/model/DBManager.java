package model;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class DBManager {

    private static final String DB_FILE = "dungeon_adventure.db";
    private static final String TABLE_QUERY = "SELECT * FROM ?";
    private static final String CONNECT_ERROR = "Database connection to " +
                                                DB_FILE +
                                                " could not be established.";
    private static final String READ_ERROR = "Database query could not be " +
                                             "completed for the table: ";

    private static final SQLiteDataSource DATA_SOURCE = createDataSource();

    static ResultSetTable readTable(final String theTable) {
        try (Connection connection = connect();
             PreparedStatement stmt = connection.prepareStatement(TABLE_QUERY)) {

            stmt.setString(1, theTable);
            ResultSet table = stmt.executeQuery();
            connection.close();

            return new ResultSetTable(table);
        } catch (SQLException e) {
            System.out.println(READ_ERROR + theTable);
            e.printStackTrace();
            return null; // Indicate failure to read table
        }
    }

    private static SQLiteDataSource createDataSource() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:" + DB_FILE);

        return dataSource;
    }

    private static Connection connect() {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            System.out.println(CONNECT_ERROR);
            e.printStackTrace();
            System.exit(0); // Replace?
            return null; // For compiler. Should never be encountered
        }
    }
}
