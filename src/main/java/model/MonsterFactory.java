package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MonsterFactory {

    private static final String TABLE_NAME = "Monsters";
    private static final List<Monster> TEMPLATES = new ArrayList<>();

    static void generateTemplates(final DBManager theDBManager,
                                  final Difficulty theDifficulty)
            throws SQLException, IllegalArgumentException {
        TemplateGenerator table = new TemplateGenerator(
                theDBManager, TABLE_NAME
        );

        while (table.next()) {
            TEMPLATES.add(new Monster(
                    table.getString(),
                    table.getIntModified(theDifficulty),
                    table.getIntModified(theDifficulty),
                    table.getIntModified(theDifficulty),
                    table.getDouble(),
                    table.getDouble(),
                    table.getInt(),
                    DamageType.valueOf(table.getString()),
                    table.getIntModified(theDifficulty),
                    table.getDouble(),
                    table.getDouble(),
                    table.getResistanceData()
            ));
        }
    }

    static Monster createRandomMonster() {
        return createMonster(Util.randomIntExc(TEMPLATES.size()));
    }

    static Monster[] createAllMonsters() {
        return IntStream.range(0, TEMPLATES.size())
               .mapToObj(MonsterFactory::createMonster)
               .toArray(Monster[]::new);
    }

    static Monster createMonster(final int theTypeIndex) {
        final Monster template = TEMPLATES.get(theTypeIndex);

        return new Monster(
                template.getName(),
                template.getMaxHP(),
                template.getMinDamage(),
                template.getMaxDamage(),
                template.getHitChance(),
                template.getDebuffChance(),
                template.getDebuffDuration(),
                template.getDamageType(),
                template.getSpeed(),
                template.getBlockChance(),
                template.getHealChance(),
                template.getResistances()
        );
    }
}
