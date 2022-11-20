package model;

import java.sql.SQLException;

public interface DBManager {

    Table readTable(String theTable) throws SQLException;
}
