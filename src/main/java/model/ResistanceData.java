package model;

import java.io.Serializable;

public class ResistanceData implements Serializable {

    final double[] myResistances;
    private final String myToString;

    ResistanceData(final double[] theResistances) {
        myResistances = theResistances.clone();
        myToString = buildString();
    }

    @Override
    public String toString() {
        return myToString;
    }

    double getResistance(final DamageType theDamageType) {
        return myResistances[theDamageType.ordinal()];
    }

    double getResistance(final int theDamageType) {
        return myResistances[theDamageType];
    }

    private String buildString() {
        final StringBuilder builder = new StringBuilder(" Resistances: ")
                                                .append('\n');

        int i = 0;
        for (DamageType type : DamageType.values()) {
            builder.append("  ").append(type).append(": ")
                   .append(myResistances[i++]).append('\n');
        }

        return builder.toString();
    }
}
