package model;

import java.util.Arrays;

public class ResistanceData {

    private static final double UNSPECIFIED_RESISTANCE = -1;

    private final double[] myResistances;

    ResistanceData(final DamageTypeResistance[] theResistances) {
        myResistances = allUnspecified();
        setResistances(theResistances);
        setUnspecifiedToZero();
    }

    double getResistance(final DamageType theDamageType) {
        return myResistances[theDamageType.ordinal()];
    }

    double getResistance(final int theDamageType) {
        return myResistances[theDamageType];
    }


    // Constructor helpers

    private double[] allUnspecified() {
        double[] resistances = new double[DamageType.values().length];
        Arrays.fill(resistances, UNSPECIFIED_RESISTANCE);

        return resistances;
    }

    private void setResistances(final DamageTypeResistance[] theResistances) {
        for (DamageTypeResistance resistance : theResistances) {
            setResistance(resistance);
        }
    }

    private void setResistance(final DamageTypeResistance theResistance) {
        myResistances[
                theResistance.getDamageType().ordinal()
        ] = theResistance.getResistance();
    }

    private void setUnspecifiedToZero() {
        int i = 0;
        for (double resistance : myResistances) {
            if (isUnspecified(resistance)) {
                myResistances[i] = 0.0;
            }
            i++;
        }
    }

    private static boolean isUnspecified(final double theResistance) {
        return theResistance == UNSPECIFIED_RESISTANCE;
    }
}
