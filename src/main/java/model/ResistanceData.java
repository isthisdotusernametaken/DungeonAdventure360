package model;

import java.io.Serial;
import java.io.Serializable;

public class ResistanceData implements Serializable {

    @Serial
    private static final long serialVersionUID = -8491245410093200893L;

    private final double[] myResistances;

    ResistanceData(final double[] theResistances) {
        myResistances = theResistances.clone();
    }

    double getResistance(final int theDamageType) {
        return myResistances[theDamageType];
    }
}