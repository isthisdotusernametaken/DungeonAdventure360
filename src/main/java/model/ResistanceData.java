package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class constructs the resistance data for the dungeon character,
 * each resistance value is associated with certain damage type.
 */
public class ResistanceData implements Serializable {

    /**
     * Class Serial Identifier.
     */
    @Serial
    private static final long serialVersionUID = -8491245410093200893L;

    /**
     * The double array containing all the resistances.
     */
    private final double[] myResistances;

    /**
     * Constructor to construct the list of resistances.
     *
     * @param theResistances The double array containing all the resistances.
     */
    ResistanceData(final double[] theResistances) {
        myResistances = theResistances.clone();
    }

    /**
     * Gets the resistance associated with certain damage type.
     *
     * @param theDamageType The type of damage.
     * @return              The resistance associated with that
     *                      damage type.
     */
    double getResistance(final int theDamageType) {
        return myResistances[theDamageType];
    }
}