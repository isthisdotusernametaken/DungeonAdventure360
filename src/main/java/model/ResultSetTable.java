package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetTable implements Table {

    private final ResultSet myResultSet;

    ResultSetTable(final ResultSet theResultSet) {
        myResultSet = theResultSet;
    }

    @Override
    public boolean next() throws SQLException {
        return myResultSet.next();
    }

    @Override
    public String getString(final int theColumn) throws SQLException {
        return myResultSet.getString(theColumn);
    }

    @Override
    public int getInt(final int theColumn) throws SQLException {
        return myResultSet.getInt(theColumn);
    }

    @Override
    public double getDouble(final int theColumn) throws SQLException {
        return myResultSet.getDouble(theColumn);
    }

    @Override
    public boolean getBoolean(final int theColumn) throws SQLException {
        return myResultSet.getBoolean(theColumn);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return myResultSet.wasNull();
    }
}
