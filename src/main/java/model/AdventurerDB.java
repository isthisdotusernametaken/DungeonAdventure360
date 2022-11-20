package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AdventurerDB extends DungeonDatabase{

    AdventurerDB() {
        openConnection();
        insertAdventurerTable();
    }

    private static void insertAdventurerTable() {

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

        try (Connection conn = openConnection();
             Statement stmt = conn.createStatement(); ) {
            stmt.executeUpdate( warriorDB );
            stmt.executeUpdate( priestessDB );
            stmt.execute(thiefDB);
            System.out.println( "Inserted rows into Adventurer table successfully" );

        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
    }
}
