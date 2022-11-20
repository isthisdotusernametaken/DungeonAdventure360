package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TrapDB extends DungeonDatabase{

    TrapDB() {
        openConnection();
        insertTrapTable();
    }
    private static void insertTrapTable() {

        try (Connection conn = openConnection();
             Statement stmt = conn.createStatement(); ) {

        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        System.out.println( "Inserted rows into Trap table successfully" );
    }
}
