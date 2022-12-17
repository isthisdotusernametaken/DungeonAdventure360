package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This factory creates NameGenerator objects for DungeonCharacterFactory
 * objects to get random names from.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public class NameGeneratorFactory {

    /**
     * Reads two tables from the provided DB and creates a NameGenerator with
     * those tables' single-column rows as possible first and last names.
     *
     * @param theDBManager The DB to build the factory from
     * @param theFirstNamesTable The name of the table to read for first names
     * @param theLastNamesTable The name of the table to read for last names
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    static NameGenerator create(final DBManager theDBManager,
                                final String theFirstNamesTable,
                                final String theLastNamesTable)
            throws SQLException, IllegalArgumentException {
        return new NameGenerator(
                readNames(theDBManager, theFirstNamesTable),
                readNames(theDBManager, theLastNamesTable)
        );
    }

    /**
     * Reads a single-column table from the provided DB into a List of Strings.
     *
     * @param theDBManager The DB to read the table from
     * @param theTable The name of the table to read for names
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    private static List<String> readNames(final DBManager theDBManager,
                                          final String theTable)
            throws SQLException, IllegalArgumentException {
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
