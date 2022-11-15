package model;

import java.util.ArrayList;
import java.util.List;

public class MonsterFactory {

    private static final List<Monster> TEMPLATES = new ArrayList<>();

    static {
        // static call for db connection and read ResultSet for monster table
        // into TEMPLATES
    }

    Monster createRandomMonster() {
        final Monster template = TEMPLATES.get(
                Util.randomIntExc(TEMPLATES.size())
        );

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
