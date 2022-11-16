package model;

import java.util.ArrayList;
import java.util.List;

public class TrapFactory {

    private static final List<Trap> TEMPLATES = new ArrayList<>();

    static {
        // static call for db connection and read ResultSet for monster table
        // into TEMPLATES
    }

    static Trap createRandomTrap() {
        final Trap template = TEMPLATES.get(
                Util.randomIntExc(TEMPLATES.size())
        );

        return new Trap(
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
}
