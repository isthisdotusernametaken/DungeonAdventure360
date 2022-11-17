package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class TrapFactory {

    private static final String TABLE_NAME = "Traps";
    private static final List<Trap> TEMPLATES = new ArrayList<>();

    static {
        generateTemplates();
    }

    static Trap createRandomTrap() {
        return createTrap(Util.randomIntExc(TEMPLATES.size()));
    }

    static Trap[] createAllTraps() {
        return IntStream.range(0, TEMPLATES.size())
               .mapToObj(TrapFactory::createTrap)
               .toArray(Trap[]::new);
    }

    static Trap createTrap(final int theTypeIndex) {
        final Trap template = TEMPLATES.get(theTypeIndex);

        return new Trap(
                template.getName(),
                template.isSingleUse(),
                template.isBoardable(),
                template.getMinDamage(),
                template.getMaxDamage(),
                template.getHitChance(),
                template.getDebuffChance(),
                template.getDebuffDuration(),
                template.getDamageType(),
                template.getSpeed(),
                template.charRepresentation()
        );
    }

    private static void generateTemplates() {
        TemplateGenerator table = new TemplateGenerator(TABLE_NAME);
        try {
            while (table.next()) {
                TEMPLATES.add(new Trap(
                        table.getString(),
                        table.getBoolean(),
                        table.getBoolean(),
                        table.getInt(),
                        table.getInt(),
                        table.getDouble(),
                        table.getDouble(),
                        table.getInt(),
                        DamageType.valueOf(table.getString()),
                        table.getInt(),
                        table.getChar()
                ));
            }
        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(0); // Replace?
        }
    }
}
