package model;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TrapDB {
    private final static String myFile = "jdbc:sqlite:Trap.db";
    private static final SQLiteDataSource ds = new SQLiteDataSource();

    public static void main(String[] args) {
        openConnection(ds);
        createDatabaseTable(ds);
        insertMonsterTable(ds);
    }

    private static void openConnection(SQLiteDataSource ds) {
        try {
            ds.setUrl(myFile);
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println( "Opened database successfully" );
    }

    private static void createDatabaseTable(SQLiteDataSource ds) {

        String query = "CREATE TABLE IF NOT EXISTS Trap (\n"
                + "theTrapType TEXT PRIMARY KEY,\n"
                + "theMinDamage INTEGER NOT NULL,\n"
                + "theMaxDamage INTEGER NOT NULL,\n"
                + "theHitChance REAL NOT NULL,\n"
                + "theDeBuffChance REAL NOT NULL,\n"
                + "theSpeed INTEGER NOT NULL)";

        try ( Connection conn = ds.getConnection();
              Statement stmt = conn.createStatement(); ) {
            stmt.execute(query);
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        System.out.println( "Created database successfully" );
    }

    private static void insertMonsterTable(SQLiteDataSource ds) {

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement(); ) {

        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        System.out.println( "Inserted rows into test table successfully" );
    }
}
