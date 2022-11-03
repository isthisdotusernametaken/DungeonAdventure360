package controller;


import model.DungeonAdventure;
import view.ConsoleUI;

public class Controller {
    private DungeonAdventure myGame;
    private final ConsoleUI myUI;

    public Controller(DungeonAdventure myGame) { //Not in the UML
        myUI = null;
    }

    public static void main(String[] args) {

    }

    public static String getPlayGuide() {
        return "";
    }

    public static String getAdventureClasses() {
        return "";
    }

    public static String getDifficultyLevel() {
        return "";
    }

    public String getAdventure() {
        return "";
    }

    public String getRoom() {
        return "";
    }

    public String getRoomItems() {
        return "";
    }

    public String getInventory() {
        return "";
    }
    public String getMap() {
        return "";
    }

    public boolean isInCombat() {
        return true;
    }

    public String getMonster() {
        return "";
    }

    public String getSpecialSkills() {
        return "";
    }

    public String getSaveFiles() {
        return "";
    }

    public boolean move(String theDirection) {
        return true;
    }

    public void useItem(int theIndexInInventory) {

    }

    public void dropItem(int theIndexInInventory) {

    }

    public void collectItem(int theIndexInInventory) {

    }

    public String attack() {
        return "";
    }

    public String useSpecialSkills(int theIndex) {
        return "";
    }

    public String flee() {
        return "";
    }

    public String createGame(String theGameDetails) {
        return "";
    }

    public String loadGame(String theFile) {
        return "";
    }

    public String saveGame(String theFile) {
        return "";
    }

    private void runGame() {

    }

    private void startUI() {

    }
}
