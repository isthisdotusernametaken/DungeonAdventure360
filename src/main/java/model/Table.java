package model;

import java.sql.SQLException;

public interface Table {

    boolean next() throws SQLException;

    int getRow() throws SQLException;

    String getString(int theColumn) throws SQLException;

    int getInt(int theColumn) throws SQLException;

    double getDouble(int theColumn) throws SQLException;

    boolean getBoolean(int theColumn) throws SQLException;

    boolean wasNull() throws SQLException;
}
