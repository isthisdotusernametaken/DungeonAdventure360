package model;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MonsterDB {

    private final static String myFile = "jdbc:sqlite:Monster.db";
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

        String ogreDB = "INSERT INTO Monster ( theMonsterName, "
                + "theMaxHP,"
                + "theMinDamage,"
                + "theMaxDamage,"
                + "theHitChance,"
                + "theDebuffChance,"
                + "theDebuffDuration,"
                + "theHealChance,"
                + "theAttackSpeed,"
                + "theMinHealPoints,"
                + "theMaxHealPoints)" +
                "VALUES ('Ogre',"
                + "'200',"
                + "'30',"
                + "'60',"
                + "'0.6',"
                + "'0.3',"
                + "'2',"
                + "'0.1',"
                + "'2',"
                + "'30',"
                + "'60')";
        String gremlinDB = "INSERT INTO Monster ( theMonsterName, "
                + "theMaxHP,"
                + "theMinDamage,"
                + "theMaxDamage,"
                + "theHitChance,"
                + "theDebuffChance,"
                + "theDebuffDuration,"
                + "theHealChance,"
                + "theAttackSpeed,"
                + "theMinHealPoints,"
                + "theMaxHealPoints)" +
                "VALUES ('Gremlin',"
                + "'70',"
                + "'15',"
                + "'30',"
                + "'0.8',"
                + "'0.4',"
                + "'2',"
                + "'0.4',"
                + "'5',"
                + "'20',"
                + "'40')";
        String skeletonDB = "INSERT INTO Monster ( theMonsterName, "
                + "theMaxHP,"
                + "theMinDamage,"
                + "theMaxDamage,"
                + "theHitChance,"
                + "theDebuffChance,"
                + "theDebuffDuration,"
                + "theHealChance,"
                + "theAttackSpeed,"
                + "theMinHealPoints,"
                + "theMaxHealPoints)" +
                "VALUES ('Skeleton',"
                + "'100',"
                + "'30',"
                + "'50',"
                + "'0.8',"
                + "'0.4',"
                + "'2',"
                + "'0.3',"
                + "'3',"
                + "'30',"
                + "'50')";

        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement(); ) {
            stmt.executeUpdate( ogreDB );
            stmt.executeUpdate( gremlinDB );
            stmt.execute(skeletonDB);

        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        System.out.println( "Inserted rows into test table successfully" );
    }
}
