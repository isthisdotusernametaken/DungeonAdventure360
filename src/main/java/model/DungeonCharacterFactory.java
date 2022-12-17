/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

import java.sql.SQLException;

/**
 * This factory class helps to create dungeon character database table and
 * let subclasses use it to prevent duplication of code.
 * @param <T> Generic class that is parameterized over types.
 */
public abstract class DungeonCharacterFactory<T extends DungeonCharacter>
        extends DamageDealerFactory<T> {

    /**
     * The generated name for adventurer's character.
     */
    private final NameGenerator myNameGenerator;

    /**
     * Constructor to construct the database table for dungeon characters.
     *
     * @param theDBManager              The SQL database manager to handle,
     *                                  and manage the database for adventurer.
     * @param theStatsTable             The string representing the stats of
     *                                  the dungeon character in the format
     *                                  of the database table.
     * @param theFirstNamesTable        The string representing the first name
     *                                  of the dungeon character in the
     *                                  format of the database table.
     * @param theLastNamesTable         The string representing the last name
     *                                  of the dungeon character in the
     *                                  format of the database table.
     *
     * @throws SQLException             Thrown if there are any string.
     * @throws IllegalArgumentException Thrown to indicate that a method has been
     *                                  passed an illegal or inappropriate argument.
     */
    DungeonCharacterFactory(final DBManager theDBManager,
                            final String theStatsTable,
                            final String theFirstNamesTable,
                            final String theLastNamesTable)
            throws SQLException, IllegalArgumentException {
        super(theDBManager, theStatsTable);

        myNameGenerator = NameGeneratorFactory.create(
                theDBManager,
                theFirstNamesTable, theLastNamesTable
        );
    }

    /**
     * Generates the name for the adventurer's character.
     *
     * @return The generated name.
     */
    String generateName() {
        return myNameGenerator.generate();
    }
}
