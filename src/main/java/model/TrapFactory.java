package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class TrapFactory extends DamageDealerFactory<Trap> {

    private static final String STATS_TABLE = "Traps";

    private static TrapFactory INSTANCE;

    private TrapFactory(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        super(theDBManager, STATS_TABLE);
    }

    static void buildInstance(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        if (INSTANCE == null) {
            INSTANCE = new TrapFactory(theDBManager);
        }
    }

    static TrapFactory getInstance() {
        return INSTANCE;
    }

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
