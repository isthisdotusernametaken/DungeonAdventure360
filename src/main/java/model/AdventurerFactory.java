package model;

import java.sql.SQLException;

public class AdventurerFactory extends DungeonCharacterFactory<Adventurer> {

    private static final String STATS_TABLE = "Adventurers";
    private static final String FIRST_NAME_TABLE = "AdventurerFirstNames";
    private static final String LAST_NAME_TABLE = "AdventurerLastNames";

    private static AdventurerFactory INSTANCE;

    private AdventurerFactory(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        super(theDBManager, STATS_TABLE, FIRST_NAME_TABLE, LAST_NAME_TABLE);
    }

    static void buildInstance(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        if (INSTANCE == null) {
            INSTANCE = new AdventurerFactory(theDBManager);
        }
    }

    static AdventurerFactory getInstance() {
        return INSTANCE;
    }

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
