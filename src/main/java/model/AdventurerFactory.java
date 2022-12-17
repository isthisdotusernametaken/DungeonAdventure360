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
 * This factory produces Adventurer objects with classes and stats defined by
 * the DB provided when building the factory.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public final class AdventurerFactory extends DungeonCharacterFactory<Adventurer> {

    /**
     * String representing the title of the stats table for the adventurer.
     */
    private static final String STATS_TABLE = "Adventurers";

    /**
     * String representing the title of the first name table for the adventurer.
     */
    private static final String FIRST_NAME_TABLE = "AdventurerFirstNames";

    /**
     * String representing the title of the last name table for the adventurer.
     */
    private static final String LAST_NAME_TABLE = "AdventurerLastNames";

    /**
     * Instance of the adventurer factory.
     */
    private static AdventurerFactory INSTANCE;

    /**
     * Reads its table from the provided DB and creates templates to build
     * Adventurers from.
     *
     * @param theDBManager The DB to build the factory from.
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    private AdventurerFactory(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        super(theDBManager, STATS_TABLE, FIRST_NAME_TABLE, LAST_NAME_TABLE);
    }


    /**
     * Constructs instance for adventurer factory if it does not already exist.
     *
     * @param theDBManager The DB to build the factory from.
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    static void buildInstance(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        if (INSTANCE == null) {
            INSTANCE = new AdventurerFactory(theDBManager);
        }
    }

    /**
     * Gets instance for adventurer factory.
     *
     * @return The instance of adventurer factory.
     */
    static AdventurerFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Constructs a template Adventurer from a row in a DB table.
     *
     * @param theTable The input processor between the DB and the factory.
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    @Override
    Adventurer buildTemplate(final TemplateGenerator theTable)
            throws SQLException, IllegalArgumentException {
        return new Adventurer(
                "",
                theTable.getString(),
                theTable.getInt(),
                theTable.getInt(),
                theTable.getInt(),
                theTable.getDouble(),
                theTable.getDouble(),
                theTable.getInt(),
                theTable.getDamageType(),
                theTable.getInt(),
                theTable.getDouble(),
                theTable.getResistanceData(),
                theTable.getSpecialSkill()
        );
    }

    /**
     * Creates a template with its stats adjusted according to the difficulty
     * level
     *
     * @param theTemplate The template Adventurer to use
     * @param theDifficulty The difficulty level to create a template for
     */
    @Override
    Adventurer buildModifiedTemplate(final Adventurer theTemplate,
                                     final Difficulty theDifficulty) {
        // Difficulty level changes Trap and Monster stats but not Adventurer
        // stats
        return new Adventurer(
                theTemplate.getName(),
                theTemplate.getClassName(),
                theTemplate.getMaxHP(),
                theTemplate.getMinDamage(),
                theTemplate.getMaxDamage(),
                theTemplate.getHitChance(),
                theTemplate.getDebuffChance(),
                theTemplate.getDebuffDuration(),
                theTemplate.getDamageType(),
                theTemplate.getSpeed(),
                theTemplate.getBlockChance(),
                theTemplate.getResistances(),
                theTemplate.getSpecialSkill()
        );
    }

    /**
     * Creates an Adventurer from the provided template.
     *
     * @param theTemplate The template Adventurer.
     */
    @Override
    Adventurer createFromTemplate(final Adventurer theTemplate) {
        return new Adventurer(
                generateName(),
                theTemplate.getClassName(),
                theTemplate.getMaxHP(),
                theTemplate.getMinDamage(),
                theTemplate.getMaxDamage(),
                theTemplate.getHitChance(),
                theTemplate.getDebuffChance(),
                theTemplate.getDebuffDuration(),
                theTemplate.getDamageType(),
                theTemplate.getSpeed(),
                theTemplate.getBlockChance(),
                theTemplate.getResistances(),
                theTemplate.getSpecialSkill()
        );
    }
}
