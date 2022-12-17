/*
 * Group Project (Dungeon Adventure)
 * Official version
 * Team 3
 * TCSS 360
 * Autumn 2022
 */

package model;

/**
 * This interface class contains a method that will be
 * implemented by subclasses, the class helps to prevent duplicate
 * method.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
interface CharRepresentable {

    /**
     * Template method for subclasses.
     */
    char charRepresentation();
}
