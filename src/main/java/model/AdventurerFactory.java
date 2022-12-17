package model;

import java.sql.SQLException;

/**
 * This factory class provides an interface for Adventure class to create, generate, and
 * access the database for dungeon adventure's character.
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
     * Constructor of adventurer factory to create the database table for
     * dungeon adventurer's characters.
     *
     * @param theDBManager              The SQL database manager to handle,
     *                                  and manage the database for adventurer.
     *
     * @throws SQLException             Thrown if there is exception that
     *                                  provides information on a database
     *                                  access error or other errors.
     * @throws IllegalArgumentException Thrown to indicate that a method has been
     *                                  passed an illegal or inappropriate argument.
     */
    private AdventurerFactory(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        super(theDBManager, STATS_TABLE, FIRST_NAME_TABLE, LAST_NAME_TABLE);
    }


    /**
     * Constructs instance for adventurer factory.
     *
     * @param theDBManager              The SQL database manager to handle,
     *                                  and manage the database for adventurer.
     *
     * @throws SQLException             Thrown if there is exception that
     *                                  provides information on a database
     *                                  access error or other errors.
     * @throws IllegalArgumentException Thrown to indicate that a method has been
     *                                  passed an illegal or inappropriate argument.
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
     * Constructs table template for the adventurer database table.
     *
     * @param theTable                  The table template generator for
     *                                  adventurer database table.
     *
     * @throws SQLException             Thrown if there is exception that
     *                                  provides information on a database
     *                                  access error or other errors.
     * @throws IllegalArgumentException Thrown to indicate that a method has been
     *                                  passed an illegal or inappropriate argument.
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
     * Modifies table template for the adventurer database table.
     *
     * @param theTemplate               The adventurer to obtain and update
     *                                  adventurer's character stats and
     *                                  information into the database table.
     * @param theDifficulty             The difficulty to adjust the difficulty
     *                                  level of the dungeon and adjust the
     *                                  stats of dungeon characters
     *                                  (exclude adventurer).
     *
     * @throws IllegalArgumentException Thrown to indicate that a method has been
     *                                  passed an illegal or inappropriate argument.
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
     * Modifies table template for the adventurer database table with
     * generate name if player choose random name.
     *
     * @param theTemplate               The adventurer to obtain and update
     *                                  adventurer's character stats and
     *                                  information into the database table.
     *
     * @throws IllegalArgumentException Thrown to indicate that a method has been
     *                                  passed an illegal or inappropriate argument.
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
