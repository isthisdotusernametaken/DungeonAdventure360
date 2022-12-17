package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This factory produces Trap objects with classes and stats defined by
 * the DB provided when building the factory.
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
     * Reads its table from the provided DB and creates templates to build
     * Traps from.
     *
     * @param theDBManager The DB to build the factory from.
     *
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
     */
    private TrapFactory(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        super(theDBManager, STATS_TABLE);
    }

    /**
     * Constructs instance for trap factory if it does not already exist.
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
            INSTANCE = new TrapFactory(theDBManager);
        }
    }

    /**
     * Gets instance for the trap factory.
     *
     * @return The instance of the trap factory.
     */
    static TrapFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Compiles the names and representations of each class of Trap into a List
     *
     * @return A list of pairs of representations and class names
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
     * Constructs a template Trap from a row in a DB table.
     *
     * @param theTable The input processor between the DB and the factory.
     * @throws SQLException Indicates a failure while reading from the DB.
     * @throws IllegalArgumentException Indicates an invalid format or value
     *                                  for a field in the DB.
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
     * Creates a template with its stats adjusted according to the difficulty
     * level
     *
     * @param theTemplate The template Monster to use
     * @param theDifficulty The difficulty level to create a template for
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
     * Creates a Trap from the provided template.
     *
     * @param theTemplate The template Trap.
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
