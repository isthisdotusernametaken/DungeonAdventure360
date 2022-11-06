package model;

public class ResistanceData {

    private static final double UNSPECIFIED_RESISTANCE = -1;

    private final double[] myResistances;

    ResistanceData(final DamageTypeResistance[] theResistances) {
        myResistances = allUnspecified();
        setResistances(theResistances);
        setUnspecifiedToZero();
    }

    double getResistance(final DamageType theDamageType) {

        return 0;
    }

    double getResistance(final int theDamageType) {


        return 0;
    }


    // Constructor helpers

    private double[] allUnspecified() {

        return new double[0];
    }

    private void setResistances(final DamageTypeResistance[] theResistance) {

    }

    private void setResistance(final DamageTypeResistance theResistance) {

    }

    private void setUnspecifiedToZero() {

    }

    private boolean isUnspecified(final int theIndex) {
        return false;
    }

}
