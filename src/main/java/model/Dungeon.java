package model;

import java.util.Arrays;

public class Dungeon {

    private static final String UNKNOWN_ROOM = createUnknownRoomString('~');
    // left/top wall + width/height of room contents + right/bottom wall
    private static final int TOTAL_ROOM_SIZE = Room.ROOM_SIZE + 2;

    private final Room[][][] myRooms;
    private final Map myMap;

    Dungeon(final RoomCoordinates theDimensions){
        myRooms = generateMaze(theDimensions);
        myMap = new Map(theDimensions);
    }

    private static String createUnknownRoomString(final char theUnknown) {
        final String unknownRoomRow = ("" + theUnknown)
                                      .repeat(TOTAL_ROOM_SIZE);

        return (unknownRoomRow + '\n')
               .repeat(TOTAL_ROOM_SIZE - 1) + // No LF after last row
               unknownRoomRow;
    }

    public String toString() {
        return view(false);
    }

    String view(final boolean theHideUnknown) {
        final StringBuilder dungeon = new StringBuilder();

        for (int i = 0; i < myRooms.length; i++) {
            dungeon.append("Floor ").append(i).append(":\n");
            for (int j = 0; j < myRooms[0].length; j++) {
                appendRow(dungeon, i, j, theHideUnknown);
            }
            dungeon.append('\n');
        }

        return dungeon.toString();
    }

    Room getRoom(final RoomCoordinates theCoords) {
        return myRooms[theCoords.getFloor()]
                      [theCoords.getX()]
                      [theCoords.getY()];
    }

    Map getMap() {
        return myMap;
    }

    private Room[][][] generateMaze(final RoomCoordinates theDimensions) {
        final Room[][][] maze = new Room[theDimensions.getFloor()]
                                        [theDimensions.getX()]
                                        [theDimensions.getY()];

        // generate maze

        return maze;
    }

    private void appendRow(final StringBuilder theStringBuilder,
                           final int theFloor,
                           final int theRow,
                           final boolean theHideUnknown) {
        final String[][] rooms = (String[][]) Arrays.stream(viewRowAsArray(
                theFloor, theRow, theHideUnknown
        )).map((s) -> s.split("\n")).toArray();

        for (int i = 0; i < TOTAL_ROOM_SIZE; i++) {
            for (String[] room : rooms) {
                theStringBuilder.append(room[i]);
            }
            theStringBuilder.append('\n');
        }
    }

    private String[] viewRowAsArray(final int theFloor,
                                    final int theRow,
                                    final boolean theHideUnknown) {
        final String[] rooms = new String[myRooms[0][0].length];

        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = (
                    theHideUnknown &&
                    !myMap.isExplored(theFloor, theRow, i)
            ) ?
                    UNKNOWN_ROOM :
                    myRooms[theFloor][theRow][i].toString();
        }

        return rooms;
    }
}
