package model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static model.Dungeon.*;

public class DungeonTest {

    @Test
    void testConstructor() {
        final Map map = new Map() { // Stubs, not used

            @Override
            boolean isExplored(final int theFloor,
                               final int theX,
                               final int theY) {
                return false;
            }

            @Override
            boolean isExplored(final RoomCoordinates theCoords) {
                return false;
            }

            @Override
            void explore(final int theFloor,
                         final int theX,
                         final int theY) {
            }

            @Override
            void explore(final RoomCoordinates theCoords) {

            }

            @Override
            boolean isInBounds(final int theFloor,
                               final int theX,
                               final int theY) {
                return false;
            }
        };

        assertEquals(
                map,
                new Dungeon(map) { // Stubs, not used

                    @Override
                    public String toString() {
                        return null;
                    }

                    @Override
                    String toString(final RoomCoordinates theAdventurerCoords,
                                    final boolean theHideUnknown) {
                        return null;
                    }

                    @Override
                    RoomCoordinates getDimensions() {
                        return null;
                    }

                    @Override
                    Room getRoom(final RoomCoordinates theCoords) {
                        return null;
                    }

                    @Override
                    boolean hasStairsUp(final RoomCoordinates theCoords) {
                        return false;
                    }

                    @Override
                    boolean hasStairsDown(final RoomCoordinates theCoords) {
                        return false;
                    }

                    @Override
                    RoomCoordinates getEntrance() {
                        return null;
                    }
                }.getMap()
        );
    }

    @Test
    void testGetMapRepresentations() {
        assertArrayEquals(
                new String[] {
                        UP_STAIRS + ": Stairs to floor above",
                        DOWN_STAIRS + ": Stairs to floor below",
                        UNKNOWN + ": Unexplored area"
                },
                getMapRepresentations().toArray(new String[0])
        );
    }
}
