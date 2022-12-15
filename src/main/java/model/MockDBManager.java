package model;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * This class provides tables with valid and invalid data for testing the
 * behavior of the TemplateGenerator class independently of the real SQLite DB.
 */
public class MockDBManager implements DBManager {

    /**
     * Table with Monster entry for testing
     */
    static final String[][] MONSTERS = {
            {
                    "TestMonster", "400", "20", "30", "0.5", "0.05", "2",
                    "BLUNT", "5", "0.05", "0.2", "0.1 0.0 0.2 0.5 0.1"
            }
    };
    /**
     * Table with Adventurer Entry
     */
    static final String[][] ADVENTURERS = {
            {
                "TestAdventurer", "800", "60", "80", "0.8", "0.15", "3",
                "SHARP", "12", "0.1", "0.1 0.1 0.1 0.0 0.0", "CrushingBlow"
            }
    };
    /**
     * Table with Trap entry
     */
    static final String[][] TRAPS = {
            {
                "TestTrap", "false", "true", "50", "100", "0.8", "0.5",
                "4", "BLUNT", "5", "P"
            }
    };
    /**
     * Table with invalid ResistanceData information
     */
    static final String[][] INVALID_RESISTANCES = {
            {
                "0.1 0.1 0.1 0.0",
                "0.1 0.1 0.1 0.0 0.0 0.1",
                "SHARP"
            }
    };
    /**
     * Table with valid and invalid ints
     */
    static final String[][] INTS = {
            {
                "1", "" + Util.MAX_INT, "0", "" + (Util.MAX_INT + 1)
            }
    };
    /**
     * Table with valid and invalid doubles/probabilities
     */
    static final String[][] DOUBLES = {
            {
                "0.0", "1.0", "-0.1", "1.1"
            }
    };
    /**
     * Table for testing behavior when a null field is detected in the database
     */
    static final String[][] NULL = {
            {
                null
            }
    };
    /**
     * Table for testing illegally empty field behavior
     */
    static final String[][] EMPTY = {
            {
                ""
            }
    };

    /**
     * All tables in this mock DB
     */
    private static final HashMap<String, String[][]> TABLES = new HashMap<>();


    static {
        setTable("Monsters", MONSTERS);
        setTable("Adventurers", ADVENTURERS);
        setTable("Traps", TRAPS);
        setTable("InvalidResistances", INVALID_RESISTANCES);
        setTable("Doubles", DOUBLES);
        setTable("Ints", INTS);
        setTable("Null", NULL);
        setTable("Empty", EMPTY);
    }

    /**
     * Add the specified table to the DB
     *
     * @param theTable The name of the new table
     * @param theRows The rows and columns of the new table
     */
    static void setTable(final String theTable, final String[] ... theRows) {
        // Only used for unit tests and not included in production branch, so
        // proper cloning of arrays not needed
        TABLES.put(theTable, theRows);
    }

    /**
     * Close method required for a DB manager. Not needed to be anything more
     * than nop since no real DB connection
     */
    @Override
    public void close() {
    }

    /**
     * Generates a Table object to retrieve fields from the specified DB table
     *
     * @param theTable The name of the table to read
     * @return A MockTable object for a TemplateGenerator to read values from
     * @throws SQLException Indicates the specified table does not exist in the
     *                      DB
     */
    @Override
    public MockTable readTable(final String theTable) throws SQLException {
        final String[][] table = TABLES.get(theTable);

        if (table == null) {
            throw new SQLException();
        }

        return new MockTable(table);
    }


    /**
     * Iterates through the rows of a mock DB table for testing
     * TemplateGenerator
     */
    static class MockTable implements Table {

        /**
         * The rows and columns of the associated DB table
         */
        final String[][] myFields;
        /**
         * The current row
         */
        int myRow;
        /**
         * Whether the most recent access read a null value
         */
        boolean myNull;

        /**
         * Creates a new MockTable backed by the provided fake DB table
         *
         * @param theFields The rows and columns of the fake table
         */
        private MockTable(final String[][] theFields) {
            myFields = theFields;
            myRow = 0;
        }

        /**
         * Advances to the next row and indicates whether another row is
         * available to read
         *
         * @return Whether another row exists in the table
         */
        @Override
        public boolean next() {
            return myRow++ < myFields.length;
        }

        /**
         * Retrieves the index of the row being read
         *
         * @return The current row's index
         */
        @Override
        public int getRow() {
            return myRow + 1;
        }

        /**
         * Reads a String from the specified location in the current row
         *
         * @param theColumn The column to read from the current row
         * @return The contents of the specified field as a String
         */
        @Override
        public String getString(int theColumn) {
            final String field = getStringAndCheckNull(theColumn);

            return myNull ? "" : field;
        }

        /**
         * Reads an int from the specified location in the current row
         *
         * @param theColumn The column to read from the current row
         * @return The contents of the specified field as an int
         * @throws SQLException Indicates the field could not be interpreted as
         *                      an int
         */
        @Override
        public int getInt(int theColumn) throws SQLException {
            final String field = getStringAndCheckNull(theColumn);

            try {
                return myNull ? 0 : Integer.parseInt(field);
            } catch (NumberFormatException e) {
                throw new SQLException();
            }
        }

        /**
         * Reads a double from the specified location in the current row
         *
         * @param theColumn The column to read from the current row
         * @return The contents of the specified field as a double
         * @throws SQLException Indicates the field could not be interpreted as
         *                      a double
         */
        @Override
        public double getDouble(int theColumn) throws SQLException {
            final String field = getStringAndCheckNull(theColumn);

            try {
                return myNull ? 0.0 : Double.parseDouble(field);
            } catch (NumberFormatException e) {
                throw new SQLException();
            }
        }

        /**
         * Reads a boolean from the specified location in the current row.
         * Note: a real SQLite DB will usually use another kind of value to
         * represent the boolean, but this behavior is sufficient to test
         * TemplateGenerator
         *
         * @param theColumn The column to read from the current row
         * @return The contents of the specified field as a boolean
         * @throws SQLException Indicates the field could not be interpreted as
         *                      a boolean
         */
        @Override
        public boolean getBoolean(int theColumn) throws SQLException {
            final String field = getStringAndCheckNull(theColumn);

            if (myNull || "false".equalsIgnoreCase(field)) {
                return false;
            }
            if ("true".equalsIgnoreCase(field)) {
                return true;
            }

            throw new SQLException();
        }

        /**
         * Indicates whether the most recently read field was null. This is
         * necessary because (in the mock and a real SQLite DB) default values
         * are provided with no other indication of a difference if a null
         * field is encountered.
         */
        @Override
        public boolean wasNull() {
            return myNull;
        }

        /**
         * Reads a String from the specified location in the current row and
         * sets myNull if the field was null
         *
         * @param theColumn The column to read from the current row
         * @return The contents of the specified field as a String, or null
         */
        private String getStringAndCheckNull(final int theColumn) {
            final String field = myFields[myRow][theColumn - 1];
            myNull = field == null;

            return field;
        }
    }
}
