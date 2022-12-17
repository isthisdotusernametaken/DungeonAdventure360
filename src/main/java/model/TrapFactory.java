package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This factory class provides an interface for trap class to create, generate, and
 * access the database for dungeon monster.
 */
public final class TrapFactory extends DamageDealerFactory<Trap> {

    /**
     * String representing the title of the stats table for the trap.
     */
    private static final String STATS_TABLE = "Traps";

    /**
     * Instance of the trap factory.
     */
    private static TrapFactory INSTANCE;

    /**
     * Constructor of trap factory to create the database table for
     * dungeon monster.
     *
     * @param theDBManager              The SQL database manager to handle,
     *                                  and modify the database for the trap.
     *
     * @throws SQLException             Thrown if there are any string.
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
     */
    private TrapFactory(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        super(theDBManager, STATS_TABLE);
    }

    /**
     * Constructs instance for trap factory.
     *
     * @param theDBManager              The SQL database manager to handle,
     *                                  and modify the database for the trap.
     *
     * @throws SQLException             Thrown if there are any string.
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
     */
    static void buildInstance(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        if (INSTANCE == null) {
            INSTANCE = new TrapFactory(theDBManager);
        }
    }

    static TrapFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Gets instance for the trap factory.
     *
     * @return The instance of the trap factory.
     */
    List<String> getClassesAndRepresentations() {
        final String[] classes = getClasses();
        final String[] representations = mapToStrings(template ->
                "" + ((CharRepresentable) template).charRepresentation());
        final List<String> classesAndRepresentations = new ArrayList<>();

        for (int i = 0; i < classes.length; i++) {
            classesAndRepresentations.add(
                    representations[i] + ": " + classes[i]
            );
        }

        return classesAndRepresentations;
    }

    /**
     * Constructs table template for the trap database table.
     *
     * @param theTable                  The table template generator for
     *                                  the trap database table.
     *
     * @throws SQLException             Thrown if there are any string.
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
     */
    @Override
    Trap buildTemplate(final TemplateGenerator theTable)
            throws SQLException, IllegalArgumentException {
        return new Trap(
                theTable.getString(),
                theTable.getBoolean(),
                theTable.getBoolean(),
                theTable.getInt(),
                theTable.getInt(),
                theTable.getDouble(),
                theTable.getDouble(),
                theTable.getInt(),
                theTable.getDamageType(),
                theTable.getInt(),
                theTable.getChar()
        );
    }

    /**
     * Modifies table template for the trap database table.
     *
     * @param theTemplate               The adventurer to obtain and update
     *                                  trap stats and information
     *                                  into the database table.
     * @param theDifficulty             The difficulty to adjust the difficulty
     *                                  level of the dungeon and adjust the
     *                                  stats of the dungeon traps.
     *
     * @throws SQLException             Thrown if there are any string.
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
     */
    @Override
    Trap buildModifiedTemplate(final Trap theTemplate,
                               final Difficulty theDifficulty) {
        return new Trap(
                theTemplate.getClassName(),
                theTemplate.isSingleUse(),
                theTemplate.isBoardable(),
                theDifficulty.modifyNegative(theTemplate.getMinDamage()),
                theDifficulty.modifyNegative(theTemplate.getMaxDamage()),
                theTemplate.getHitChance(),
                theTemplate.getDebuffChance(),
                theTemplate.getDebuffDuration(),
                theTemplate.getDamageType(),
                theDifficulty.modifyNegative(theTemplate.getSpeed()),
                theTemplate.charRepresentation()
        );
    }

    /**
     * Modifies table template for the trap database table with
     * generated random name.
     *
     * @param theTemplate               The adventurer to obtain and update
     *                                  trap stats and information
     *                                  into the database table.
     *
     * @throws SQLException             Thrown if there are any string.
     * @throws IllegalArgumentException Thrown to indicate that a method has
     *                                  been passed an illegal or inappropriate
     *                                  argument.
     */
    @Override
    Trap createFromTemplate(final Trap theTemplate) {
        return new Trap(
                theTemplate.getClassName(),
                theTemplate.isSingleUse(),
                theTemplate.isBoardable(),
                theTemplate.getMinDamage(),
                theTemplate.getMaxDamage(),
                theTemplate.getHitChance(),
                theTemplate.getDebuffChance(),
                theTemplate.getDebuffDuration(),
                theTemplate.getDamageType(),
                theTemplate.getSpeed(),
                theTemplate.charRepresentation()
        );
    }
}
