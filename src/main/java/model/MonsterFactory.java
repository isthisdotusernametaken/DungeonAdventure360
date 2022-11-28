package model;

import java.sql.SQLException;

public class MonsterFactory extends DungeonCharacterFactory<Monster> {

    private static final String STATS_TABLE = "Monsters";
    private static final String FIRST_NAME_TABLE = "MonsterFirstNames";
    private static final String LAST_NAME_TABLE = "MonsterLastNames";

    private static MonsterFactory INSTANCE;

    private MonsterFactory(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        super(theDBManager, STATS_TABLE, FIRST_NAME_TABLE, LAST_NAME_TABLE);
    }

    static void buildInstance(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        if (INSTANCE == null) {
            INSTANCE = new MonsterFactory(theDBManager);
        }
    }

    static MonsterFactory getInstance() {
        return INSTANCE;
    }

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
