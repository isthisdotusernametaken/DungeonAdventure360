package model;

import java.io.Serializable;

public class DamageTypeResistance implements Serializable {

    private final DamageType myDamageType;
    private final double myResistance;

    DamageTypeResistance(final DamageType theDamageType,
                         final double theResistance) {
        myDamageType = theDamageType;
        myResistance = Util.clampFraction(theResistance);
    }

    DamageType getDamageType() {
        return myDamageType;
    }

    double getResistance() {
        return myResistance;
    }
}
