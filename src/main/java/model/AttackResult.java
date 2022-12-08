package model;

public enum AttackResult {

    NO_ACTION,
    BUFF_DAMAGE,
    HEAL,
    EXTRA_TURN,
    KILL,
    HIT_NO_DEBUFF,
    HIT_DEBUFF,
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
