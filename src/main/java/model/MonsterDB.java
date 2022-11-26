package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MonsterDB extends DungeonDatabase{

    MonsterDB () {
        openConnection();
        insertMonsterTable();
    }

    private static void insertMonsterTable() {

        String ogreDB = """
                INSERT INTO Monster ( theMonsterName,
                theMaxHP,
                theMinDamage,
                theMaxDamage,
                theHitChance,
                theDebuffChance,
                theDebuffDuration,
                theHealChance,
                theAttackSpeed,
                theMinHealPoints,
                theMaxHealPoints)
                VALUES ('Ogre',
                '200',
                '30',
                '60',
                '0.6',
                '0.3',
                '2',
                '0.1',
                '2',
                '30',
                '60')""";

        String gremlinDB = """
                INSERT INTO Monster ( theMonsterName,
                theMaxHP,
                theMinDamage,
                theMaxDamage,
                theHitChance,
                theDebuffChance,
                theDebuffDuration,
                theHealChance,
                theAttackSpeed,
                theMinHealPoints,
                theMaxHealPoints)
                VALUES ('Gremlin',
                '70',
                '15',
                '30',
                '0.8',
                '0.4',
                '2',
                '0.4',
                '5',
                '20',
                '40')""";

        String skeletonDB = """
                INSERT INTO Monster ( theMonsterName,
                theMaxHP,
                theMinDamage,
                theMaxDamage,
                theHitChance,
                theDebuffChance,
                theDebuffDuration,
                theHealChance,
                theAttackSpeed,
                theMinHealPoints,
                theMaxHealPoints)
                VALUES ('Skeleton',
                '100',
                '30',
                '50',
                '0.8',
                '0.4',
                '2',
                '0.3',
                '3',
                '30',
                '50')""";

        try (Connection conn = openConnection();
             Statement stmt = conn.createStatement() ) {
            stmt.executeUpdate( ogreDB );
            stmt.executeUpdate( gremlinDB );
            stmt.execute(skeletonDB);
            System.out.println( "Inserted rows into Monster table successfully" );
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
    }
}
