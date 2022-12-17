package model;

import java.sql.SQLException;

/**
 * This factory class provides an interface for monster class to create, generate, and
 * access the database for dungeon monster.
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
     * Constructor of monster factory to create the database table for
     * dungeon monster.
     *
     * @param theDBManager              The SQL database manager to handle,
     *                                  and manage the database for the
     *                                  monster.
     *
     * @throws SQLException             Thrown if there are any string.
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
     */
    private MonsterFactory(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        super(theDBManager, STATS_TABLE, FIRST_NAME_TABLE, LAST_NAME_TABLE);
    }

    /**
     * Constructs instance for monster factory.
     *
     * @param theDBManager              The SQL database manager to handle,
     *                                  and modify the database for the
     *                                  monster.
     *
     * @throws SQLException             Thrown if there are any string.
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
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
     * Constructs table template for the monster database table.
     *
     * @param theTable                  The table template generator for
     *                                  the monster database table.
     *
     * @throws SQLException             Thrown if there are any string.
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
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
     * Modifies table template for the monster database table.
     *
     * @param theTemplate               The adventurer to obtain and update
     *                                  monster stats and information
     *                                  into the database table.
     * @param theDifficulty             The difficulty to adjust the difficulty
     *                                  level of the dungeon and adjust the
     *                                  stats of the dungeon characters
     *                                  (exclude adventurer).
     *
     * @throws SQLException             Thrown if there are any string.
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
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
     * Modifies table template for the monster database table with
     * generated random name.
     *
     * @param theTemplate               The adventurer to obtain and update
     *                                  monster stats and information
     *                                  into the database table.
     *
     * @throws SQLException             Thrown if there are any string.
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
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
