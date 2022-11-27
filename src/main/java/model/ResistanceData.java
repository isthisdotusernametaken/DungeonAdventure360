package model;

import java.io.Serializable;

public class ResistanceData implements Serializable {

    private final double[] myResistances;

    ResistanceData(final double[] theResistances) {
        myResistances = theResistances.clone();
    }

    double getResistance(final DamageType theDamageType) {
        return myResistances[theDamageType.ordinal()];
    }

    double getResistance(final int theDamageType) {
        return myResistances[theDamageType];
    }
}