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
 * This factory produces Monster objects with classes and stats defined by
 * the DB provided when building the factory.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public final class MonsterFactory extends DungeonCharacterFactory<Monster> {

    /**
     * String representing the title of the stats table for the monster.
     */
    private static final String STATS_TABLE = "Monsters";

    /**
     * String representing the title of the first name table for the monster.
     */
    private static final String FIRST_NAME_TABLE = "MonsterFirstNames";

    /**
     * String representing the title of the last name table for the monster.
     */
    private static final String LAST_NAME_TABLE = "MonsterLastNames";

    /**
     * Instance of the monster factory.
     */
    private static MonsterFactory INSTANCE;

    /**
     * Reads its table from the provided DB and creates templates to build
     * Monsters from.
     *
     * @param theDBManager The DB to build the factory from.
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    private MonsterFactory(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        super(theDBManager, STATS_TABLE, FIRST_NAME_TABLE, LAST_NAME_TABLE);
    }

    /**
     * Constructs instance for monster factory if it does not already exist.
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
            INSTANCE = new MonsterFactory(theDBManager);
        }
    }

    /**
     * Gets instance for the monster factory.
     *
     * @return The instance of the monster factory.
     */
    static MonsterFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Constructs a template Monster from a row in a DB table.
     *
     * @param theTable The input processor between the DB and the factory.
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    @Override
    Monster buildTemplate(final TemplateGenerator theTable)
            throws SQLException, IllegalArgumentException {
        return new Monster(
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
                theTable.getDouble(),
                theTable.getResistanceData()
        );
    }

    /**
     * Creates a template with its stats adjusted according to the difficulty
     * level
     *
     * @param theTemplate The template Monster to use
     * @param theDifficulty The difficulty level to create a template for
     */
    @Override
    Monster buildModifiedTemplate(final Monster theTemplate,
                                  final Difficulty theDifficulty) {
        return new Monster(
                theTemplate.getName(),
                theTemplate.getClassName(),
                theDifficulty.modifyNegative(theTemplate.getMaxHP()),
                theDifficulty.modifyNegative(theTemplate.getMinDamage()),
                theDifficulty.modifyNegative(theTemplate.getMaxDamage()),
                theTemplate.getHitChance(),
                theTemplate.getDebuffChance(),
                theTemplate.getDebuffDuration(),
                theTemplate.getDamageType(),
                theDifficulty.modifyNegative(theTemplate.getSpeed()),
                theTemplate.getBlockChance(),
                theTemplate.getHealChance(),
                theTemplate.getResistances()
        );
    }

    /**
     * Creates a Monster from the provided template.
     *
     * @param theTemplate The template Monster.
     */
    @Override
    Monster createFromTemplate(final Monster theTemplate) {
        return new Monster(
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
                theTemplate.getHealChance(),
                theTemplate.getResistances()
        );
    }
}
