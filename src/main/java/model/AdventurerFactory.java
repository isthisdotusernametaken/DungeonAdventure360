package model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AdventurerFactory implements Serializable {

    private static final String TABLE_NAME = "Adventurers";
    private static final List<Adventurer> TEMPLATES = new ArrayList<>();

    static void generateTemplates(final DBManager theDBManager)
            throws SQLException, IllegalArgumentException {
        TemplateGenerator table = new TemplateGenerator(
                theDBManager, TABLE_NAME
        );

        while (table.next()) {
            TEMPLATES.add(new Adventurer(
                    table.getString(),
                    table.getInt(),
                    table.getInt(),
                    table.getInt(),
                    table.getDouble(),
                    table.getDouble(),
                    table.getInt(),
                    DamageType.valueOf(table.getString()),
                    table.getInt(),
                    table.getDouble(),
                    table.getResistanceData(),
                    table.getSpecialSkill()
            ));
        }
    }

    static Adventurer createRandomAdventurer() {
        return createAdventurer(Util.randomIntExc(TEMPLATES.size()));
    }

    static Adventurer[] createAllAdventurers() {
        return IntStream.range(0, TEMPLATES.size())
                .mapToObj(AdventurerFactory::createAdventurer)
                .toArray(Adventurer[]::new);
    }

    static Adventurer createAdventurer(final int theTypeIndex) {
        final Adventurer template = TEMPLATES.get(theTypeIndex);

        return new Adventurer(
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
                template.getResistances(),
                template.getSpecialSkill()
        );
    }
}
