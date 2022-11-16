package model;

import java.sql.*;

public class DBManager {

    private static final String DB_FILE = "dungeon_adventure.db";
    private static final String TABLE_QUERY = "SELECT * FROM ?";
    private static final String CONNECT_ERROR = "Database connection to " +
                                                DB_FILE +
                                                " could not be established.";
    private static final String READ_ERROR = "Database query could not be " +
                                             "completed for the table: ";

    static ResultSet readTable(final String theTable) {
        try (Connection cnct = connect();
             PreparedStatement stmt = cnct.prepareStatement(TABLE_QUERY)) {

            stmt.setString(1, theTable);
            ResultSet table = stmt.executeQuery();
            cnct.close();

            return table;
        } catch (SQLException e) {
            System.out.println(READ_ERROR + theTable);
            e.printStackTrace();
            System.exit(0); // Replace?
        }

        return null; // Should never be encountered
    }

    private static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);
        } catch (SQLException e) {
            System.out.println(CONNECT_ERROR);
            e.printStackTrace();
            System.exit(0); // Replace?
        }

        return null; // Should never be encountered
    }
}
