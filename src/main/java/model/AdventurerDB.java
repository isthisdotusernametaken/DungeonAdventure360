package model;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AdventurerDB {

    private final static String myFile = "jdbc:sqlite:Adventurer.db";
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

        String query = "CREATE TABLE IF NOT EXISTS Adventurer (\n"
                + "theAdventurerName TEXT PRIMARY KEY,\n"
                + "theMaxHP INTEGER NOT NULL,\n"
                + "theMinDamage INTEGER NOT NULL,\n"
                + "theMaxDamage INTEGER NOT NULL,\n"
                + "theHitChance REAL NOT NULL,\n"
                + "theDeBuffChance REAL NOT NULL,\n"
                + "theDebuffDuration INTEGER NOT NULL,\n"
                + "theAttackSpeed INTEGER NOT NULL,\n"
                + "theBlockChance REAL NOT NULL)";

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

        String warriorDB = "INSERT INTO Adventurer ( theAdventurerName, "
                + "theMaxHP,"
                + "theMinDamage,"
                + "theMaxDamage,"
                + "theHitChance,"
                + "theDebuffChance,"
                + "theDebuffDuration,"
                + "theAttackSpeed,"
                + "theBlockChance)" +
                "VALUES ('Warriror',"
                + "'125',"
                + "'35',"
                + "'60',"
                + "'0.8',"
                + "'0.6',"
                + "'2',"
                + "'4',"
                + "'0.2')";
        String priestessDB = "INSERT INTO Adventurer ( theAdventurerName, "
                + "theMaxHP,"
                + "theMinDamage,"
                + "theMaxDamage,"
                + "theHitChance,"
                + "theDebuffChance,"
                + "theDebuffDuration,"
                + "theAttackSpeed,"
                + "theBlockChance)" +
                "VALUES ('Priestess',"
                + "'75',"
                + "'25',"
                + "'40',"
                + "'0.7',"
                + "'0.8',"
                + "'4',"
                + "'5',"
                + "'0.3')";
        String thiefDB = "INSERT INTO Adventurer ( theAdventurerName, "
                + "theMaxHP,"
                + "theMinDamage,"
                + "theMaxDamage,"
                + "theHitChance,"
                + "theDebuffChance,"
                + "theDebuffDuration,"
                + "theAttackSpeed,"
                + "theBlockChance)" +
                "VALUES ('Thief',"
                + "'75',"
                + "'20',"
                + "'40',"
                + "'0.8',"
                + "'0.4',"
                + "'1',"
                + "'6',"
                + "'0.4')";

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement(); ) {
            stmt.executeUpdate( warriorDB );
            stmt.executeUpdate( priestessDB );
            stmt.execute(thiefDB);

        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        System.out.println( "Inserted rows into test table successfully" );
    }
}
