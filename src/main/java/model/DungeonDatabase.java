package model;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class DungeonDatabase {
    private final static String myFile = "jdbc:sqlite:DungeonDataBase.db";
    private final static SQLiteDataSource myDS = new SQLiteDataSource();

    DungeonDatabase () {
        createDataBase();
        createMonsterDataBaseTable();
        createAdventurerDataBaseTable();
        createTrapDataBaseTable();
    }


    public static void main(String[] args) {
        createDataBase();
        createMonsterDataBaseTable();
        createAdventurerDataBaseTable();
        createTrapDataBaseTable();
        MonsterDB monster = new MonsterDB();
        AdventurerDB adventurer = new AdventurerDB();
        TrapDB trap = new TrapDB();
    }

    private static void createDataBase() {
        try {
            myDS.setUrl(myFile);
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Created SQl data base successfully");
    }

    private static void createMonsterDataBaseTable() {

        String query = "CREATE TABLE IF NOT EXISTS Monster (\n"
                + "theMonsterName TEXT PRIMARY KEY,\n"
                + "theMaxHP INTEGER NOT NULL,\n"
                + "theMinDamage INTEGER NOT NULL,\n"
                + "theMaxDamage INTEGER NOT NULL,\n"
                + "theHitChance REAL NOT NULL,\n"
                + "theDeBuffChance REAL NOT NULL,\n"
                + "theDebuffDuration INTEGER NOT NULL,\n"
                + "theHealChance REAL NOT NULL,\n"
                + "theAttackSpeed INTEGER NOT NULL,\n"
                + "theMinHealPoints INTEGER NOT NULL,\n"
                + "theMaxHealPoints INTEGER NOT NULL)";

        try ( Connection conn = myDS.getConnection();
              Statement stmt = conn.createStatement(); ) {
            stmt.execute(query);
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        System.out.println("Created Monster table successfully");
    }

    private static void createAdventurerDataBaseTable() {

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

        try ( Connection conn = myDS.getConnection();
              Statement stmt = conn.createStatement(); ) {
            stmt.execute(query);
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        System.out.println("Created Adventurer table successfully");
    }

    private static void createTrapDataBaseTable() {

            String query = "CREATE TABLE IF NOT EXISTS Trap (\n"
                    + "theTrapType TEXT PRIMARY KEY,\n"
                    + "theMinDamage INTEGER NOT NULL,\n"
                    + "theMaxDamage INTEGER NOT NULL,\n"
                    + "theHitChance REAL NOT NULL,\n"
                    + "theDeBuffChance REAL NOT NULL,\n"
                    + "theSpeed INTEGER NOT NULL)";

            try ( Connection conn = myDS.getConnection();
                  Statement stmt = conn.createStatement(); ) {
                stmt.execute(query);
            } catch ( SQLException e ) {
                e.printStackTrace();
                System.exit( 0 );
            }
            System.out.println( "Created Trap table successfully" );
        }

    public static Connection openConnection() {
        Connection cn = null;
        try {
            cn = DriverManager.getConnection(myFile);
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }

        return cn;
    }
}
