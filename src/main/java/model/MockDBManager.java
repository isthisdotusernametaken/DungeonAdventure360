package model;

import java.util.HashMap;

public class MockDBManager {

    private static final HashMap<String, String[][]> TABLES = new HashMap<>();

    static Table readTable(final String theTable) {
        return new MockTable(TABLES.get(theTable));
    }

    static void setTable(final String theTable, final String[] ... theRows) {
        // Only used for unit tests and not included in production branch, so
        // proper cloning of arrays not needed
        TABLES.put(theTable, theRows);
    }

    private static class MockTable implements Table {

        private final String[][] myFields;
        private int myRow;
        private boolean myNull;

        private MockTable(final String[][] theFields) {
            myFields = theFields;
            myRow = 0;
        }

        @Override
        public boolean next() {
            return myRow++ < myFields.length;
        }

        @Override
        public String getString(int theColumn) {
            final String field = getStringAndCheckNull(theColumn);

            return myNull ? "" : field;
        }

        @Override
        public int getInt(int theColumn) {
            final String field = getStringAndCheckNull(theColumn);

            return myNull ? 0 : Integer.parseInt(field);
        }

        @Override
        public double getDouble(int theColumn) {
            final String field = getStringAndCheckNull(theColumn);

            return myNull ? 0.0 : Double.parseDouble(field);
        }

        @Override
        public boolean getBoolean(int theColumn) {
            final String field = getStringAndCheckNull(theColumn);

            return !myNull && Boolean.parseBoolean(field);
        }

        @Override
        public boolean wasNull() {
            return myNull;
        }

        private String getStringAndCheckNull(final int theColumn) {
            final String field = myFields[myRow][theColumn];
            myNull = field == null;

            return field;
        }
    }
}
