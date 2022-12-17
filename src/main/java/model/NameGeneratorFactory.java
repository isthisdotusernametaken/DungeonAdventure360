/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * This factory class helps to generate and create names for the dungeon
 * character from methods let subclasses use the methods to prevent
 * duplication of code.
 */
public class NameGeneratorFactory {

    /**
     * Creates random name for the dungeon characters.
     *
     * @param theDBManager       The SQL database manager to handle, and
     *                           modify the database for adventurer.
     * @param theFirstNamesTable The string representing the first names.
     * @param theLastNamesTable  The string representing the last names.
     * @return                   The name generator using the list of first
     *                           names and the list of last names.
     *
     * @throws SQLException      Thrown if there is exception that provides information
     *                           on a database access error or other errors.
     */
    static NameGenerator create(final DBManager theDBManager,
                                final String theFirstNamesTable,
                                final String theLastNamesTable)
            throws SQLException {
        return new NameGenerator(
                readNames(theDBManager, theFirstNamesTable),
                readNames(theDBManager, theLastNamesTable)
        );
    }

    /**
     * Accesses and reads the names in the SQL database table.
     *
     * @param theDBManager  The SQL database manager to handle, and
     *                      modify the database for adventurer.
     * @param theTable      The string table to create table.
     * @return              The string list of names.
     *
     * @throws SQLException Thrown if there is exception that provides information
     *                      on a database access error or other errors.
     */
    private static List<String> readNames(final DBManager theDBManager,
                                          final String theTable)
            throws SQLException {
        final TemplateGenerator table = new TemplateGenerator(
                theDBManager, theTable
        );

        final List<String> names = new ArrayList<>();
        while (table.next()) {
            names.add(table.getString());
        }

        return names;
    }
}
