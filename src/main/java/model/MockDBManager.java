package model;

import java.util.HashMap;

public class MockDBManager {

    private static final String[][] MONSTERS = {
            {
                "TestMonster", "400", "20", "30", "0.5", "0.05", "2", "BLUNT",
                "5", "0.05", "0.2", "0.1 0.0 0.2 0.5 0.1"
            }
    };
    private static final String[][] ADVENTURERS = {
            {
                    "TestAdventurer", "800", "60", "80", "0.8", "0.15", "3",
                    "SHARP", "12", "0.1", "0.1 0.1 0.1 0.0 0.0"
            }
    };
    private static final String[][] TRAPS = {
            {
                    "TestTrap", "false", "true", "50", "100", "0.8", "0.5",
                    "4", "BLUNT", "5", "P"
            }
    };

    private static final HashMap<String, String[][]> TABLES = new HashMap<>();


    static {
        setTable("Monsters", MONSTERS);
        setTable("Adventurers", ADVENTURERS);
        setTable("Traps", TRAPS);
    }

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
