package model;

public enum AttackResult {

    NO_ACTION,
    BUFF_DAMAGE,
    HEAL,
    KILL,
    HIT_NO_DEBUFF,
    HIT_DEBUFF,
    EXTRA_TURN_NO_DEBUFF,
    EXTRA_TURN_DEBUFF,
    FLED_SUCCESSFULLY,
    COULD_NOT_FLEE,
    MISS,
    BLOCK,
    DODGE;

    private String myName;

    @Override
    public String toString() {
        if (myName == null) {
            myName = Util.createNameFromEnumName(this);
        }

        return myName;
    }
}
