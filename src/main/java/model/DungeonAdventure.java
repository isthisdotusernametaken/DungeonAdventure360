package model;

import java.io.Serializable;

public class DungeonAdventure implements Serializable {

    private final Dungeon myDungeon;
    private final Adventurer myAdventurer;
    private final Container myInventory;
    private RoomCoordinates myAdventurerCoordinates;
    private boolean myIsInCombat;
    private TurnAllocator myTurnAllocator;

    public DungeonAdventure(final String theAdventurerName,
                            final int theAdventurerClass,
                            final Difficulty theDifficulty) {
        myDungeon = DungeonFactory.create(theDifficulty);
        myAdventurerCoordinates = myDungeon.getEntrance();

        myAdventurer = AdventurerFactory.getInstance()
                .create(theAdventurerClass, theDifficulty);
        myAdventurer.setName(theAdventurerName);

        myInventory = InventoryFactory.createWithInitialItems(theDifficulty);

        // myInCombat false and myTurnAllocator null
    }

    public String getAdventurer() {
        return myAdventurer.toString();
    }

    public String getAdventurerCoords() {
        return myAdventurerCoordinates.toString();
    }

    public String getRoom() {
        return myDungeon.getRoom(myAdventurerCoordinates).toString();
    }

    public String getRoomItems() {
        return myDungeon.getRoom(myAdventurerCoordinates)
                        .getContainer().toString();
    }

    public String getInventoryItems() {
        return myInventory.toString();
    }

    public String getMap() {
        return myDungeon.view(true);
    }

    public boolean isInCombat() {
        return myIsInCombat;
    }

    public String getSpecialSkill() {
        return myAdventurer.viewSpecialSkill();
    }

    public boolean canUseSpecialSkill() {
        return myAdventurer.getSpecialSkill().canUse();
    }
}
