package model;

public enum Direction {

    NORTH,
    EAST,
    SOUTH,
    WEST;


    private String myName;

    @Override
    public String toString() {
        if (myName == null) {
            myName = Util.createNameFromEnumName(this);
        }

        return myName;
    }
}
