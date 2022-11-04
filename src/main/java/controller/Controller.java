package controller;


import model.DungeonAdventure;
import view.ConsoleUI;
import view.UISelection;

public class Controller {

    private DungeonAdventure myGame;
    private final ConsoleUI myUI;

    public Controller() {
        myUI = UISelection.select() == UISelection.CONSOLE_UI ?
                new ConsoleUI(this) :
                null/*new GUI(this)*/;
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

    public boolean move(final String theDirection) {
        return true;
    }

    public void useItem(final int theIndexInInventory) {

    }

    public void dropItem(final int theIndexInInventory) {

    }

    public void collectItem(final int theIndexInInventory) {

    }

    public String attack() {
        return "";
    }

    public String useSpecialSkills(final int theIndex) {
        return "";
    }

    public String flee() {
        return "";
    }

    public String createGame(final String theGameDetails) {
        return "";
    }

    public String loadGame(final String theFile) {
        return "";
    }

    public String saveGame(final String theFile) {
        return "";
    }

    private void runGame() {

    }

    private void startUI() {

    }
}
