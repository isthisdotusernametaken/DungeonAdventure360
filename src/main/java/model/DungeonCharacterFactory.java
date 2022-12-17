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
 * This factory produces DungeonCharacter objects with classes and stats
 * defined by the DB provided when building the factory.
 *
 * @param <T> The kind of DungeonCharacter the factory creates
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public abstract class DungeonCharacterFactory<T extends DungeonCharacter>
        extends DamageDealerFactory<T> {

    /**
     * The generator for the created DungeonCharacters' names.
     */
    private final NameGenerator myNameGenerator;

    /**
     * Reads its table from the provided DB and creates templates to build
     * Monsters from.
     *
     * @param theDBManager The DB to build the factory from
     * @param theStatsTable The name of the table to read for stats
     * @param theFirstNamesTable The name of the table to read for first names
     * @param theLastNamesTable The name of the table to read for last names
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
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
     * Generates a random name for this kind of DungeonCharacter.
     *
     * @return The generated name.
     */
    String generateName() {
        return myNameGenerator.generate();
    }
}
