package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.DungeonAdventure;

/**
 * This class manages all interactions with the program's directory for saving
 * game instances and logging errors.
 *
 * @author Joshua Barbee, Tinh Diep, Alexander Boudreaux
 * @version 16 December 2022
 */
public final class ProgramFileManager {

    /**
     * The single instance of the file manager
     */
    private static ProgramFileManager INSTANCE;

    /**
     * The extension to use for save files
     */
    private static final String SAVES_EXTENSION =
            ".dungeon";

    /**
     * The path of the saves folder from the main directory
     */
    private static final String SAVES_NAME =
            "\\saves";
    /**
     * The name of the log file
     */
    private static final String LOG_NAME =
            "\\log.txt";
    /**
     * The directory to use for the program if the intended directory is
     * unavailable/
     */
    private static final String BACKUP_PROGRAM_DIR =
            "Dungeon Adventure";
    /**
     * The path of the backup saves folder
     */
    private static final String BACKUP_SAVES_DIR =
            BACKUP_PROGRAM_DIR + SAVES_NAME;
    /**
     * The path of the backup log file
     */
    private static final String BACKUP_LOG_PATH =
            BACKUP_PROGRAM_DIR + LOG_NAME;
    /**
     * The intended path of the program directory
     */
    private static final String PROGRAM_DIR =
            System.getenv("APPDATA") + "\\" + BACKUP_PROGRAM_DIR;
    /**
     * The intended path of the saves folder
     */
    private static final String SAVES_DIR =
            PROGRAM_DIR + SAVES_NAME;
    /**
     * The intended path of the log file
     */
    private static final String LOG_PATH =
            PROGRAM_DIR + LOG_NAME;

    /**
     * Alert indicating the game could not be saved
     */
    private static final String SAVING_ERROR =
            "The game could not be saved as \"%s\". Try again.";
    /**
     * Alert indicating the game could not be loaded
     */
    private static final String LOADING_ERROR =
            "The file \"%s\" could not be loaded. Try again.";
    /**
     * Alert indicating the specified file is not a valid save file
     */
    private static final String INVALID_FILE =
            "The file \"%s\" is invalid. Try again";

    /**
     * Whether the intended directory is available
     */
    private final boolean myUsesPrimaryDir;

    /**
     * Creates a new file manager that will use the specified directory
     * (intended or backup)
     *
     * @param theUsesPrimaryDir Whether the intended directory can be used
     */
    private ProgramFileManager(final boolean theUsesPrimaryDir) {
        myUsesPrimaryDir = theUsesPrimaryDir;
    }

    /**
     * Retrieves the single instance of the file manager;
     *
     * @return The game's file manager
     */
    public static ProgramFileManager getInstance() {
        return INSTANCE;
    }

    /**
     * Tries to create the file manager if it does not exist yet. Indicates
     * whether a valid file manager exists after this call.
     *
     * @return Whether an instance of the file manager now exists
     */
    public static boolean tryCreateInstance() {
        if (INSTANCE != null) {
            // Ensures instance constant, so same program dir used
            return true;
        }

        final boolean dirExists = createDirs(SAVES_DIR);
        final boolean backupDirExists = !dirExists &&
                createDirs(BACKUP_SAVES_DIR);

        if (dirExists || backupDirExists) {
            INSTANCE = new ProgramFileManager(dirExists);
            return true;
        }
        return false; // Could not find a suitable dir for program files
    }

    /**
     * Attempts to create all the directories in the specified path and
     * indicates whether this could be done.
     *
     * @param theDir The path to create all the directories of
     * @return Whether the specified directories now exist
     */
    private static boolean createDirs(final String theDir) {
        final File dir = new File(theDir);

        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        return dir.exists();
    }

    /**
     * Attempts to save the provided error information to the program's log
     * file. If this is unsuccessful, the main error message will be displayed
     * in the console (this may also be chosen to apply to cases where the
     * operation succeeds).
     *
     * @param theException The exception and details to log
     * @param theMessage The associated brief message to log and maybe print
     * @param thePrintToConsoleOnSuccess Whether the main message should be
     *                                   printed to the console even if the
     *                                   error is logged successfully
     */
    public void logException(final Exception theException,
                             final String theMessage,
                             final boolean thePrintToConsoleOnSuccess) {
        try (FileWriter fw = new FileWriter(getLogPath(), true);
             PrintWriter pw = new PrintWriter(new BufferedWriter(fw))) {
            pw.println();

            pw.print(new Date());
            pw.println(':');

            pw.println(theMessage);

            theException.printStackTrace(pw);

            if (thePrintToConsoleOnSuccess) {
                System.out.println(theMessage);
            }
        } catch (IOException e) {
            System.out.println(theMessage);
        }
    }

    /**
     * Attempts to log the provided error information.
     *
     * @param theException The exception to log
     * @param thePrintToConsoleOnSuccess Whether the exception's brief
     *                                   description should always be printed
     *                                   to the console
     * @see #logException(Exception, String, boolean)
     */
    public void logException(final Exception theException,
                             final boolean thePrintToConsoleOnSuccess) {
        logException(
                theException,
                theException.getMessage(), thePrintToConsoleOnSuccess
        );
    }

    /**
     * Determines which path to use for the log file
     *
     * @return The log file path as decided by myUsesPrimaryDir
     */
    String getLogPath() {
        return myUsesPrimaryDir ? LOG_PATH : BACKUP_LOG_PATH;
    }

    /**
     * Retrieves an array of all save files (identified by their extension) in
     * the save directory.
     *
     * @return An array of the existing saves' names, excluding the extension.
     */
    String[] getSaveFiles() {
        final String[] files = new File(SAVES_DIR).list();

        if (files != null) {
            final List<String> filenames = new ArrayList<>(files.length);
            int extensionStart;

            for (String file : files) {
                extensionStart = file.lastIndexOf('.');
                if (SAVES_EXTENSION.equalsIgnoreCase(
                        file.substring(extensionStart))) {
                    filenames.add(file.substring(0, extensionStart));
                }
            }

            return filenames.toArray(new String[0]);
        }
        return new String[0]; // Lost access to save file during execution
    }

    /**
     * Attempts to load a game from the specified file. Logs any errors that
     * occur and prints that an error occurred.
     *
     * @param theFile The name of the file to attempt to load
     * @return The game instance that was loaded from the file, or null if a
     *         game instance could not be loaded from the file
     */
    DungeonAdventure loadGame(final String theFile) {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(buildSaveFileName(theFile)))) {
            return (DungeonAdventure) in.readObject();
        } catch (ObjectStreamException e) {
            logException(e, String.format(LOADING_ERROR, theFile), true);
        } catch (IOException | SecurityException | ClassNotFoundException e) {
            logException(e, String.format(INVALID_FILE, theFile), true);
        }

        return null;
    }

    /**
     * Attempts to save the provided game to the specified save file.
     *
     * @param theFile The file to save the game to
     * @param theGame The game instance to save to the file
     * @return Whether the game was successfully saved
     */
    boolean saveGame(final String theFile,
                     final DungeonAdventure theGame) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(buildSaveFileName(theFile)))) {
            out.writeObject(theGame);
            return true;
        } catch (ObjectStreamException e) {
            logException(e, String.format(SAVING_ERROR, theFile), true);
        } catch (IOException | SecurityException e) {
            logException(e, String.format(INVALID_FILE, theFile), true);
        }

        return false;
    }

    /**
     * Formats the provided name as a full path using the save file extension
     * and the save directory.
     *
     * @param theFile The bare name to formalize
     * @return The full path of a save file corresponding to the provided name
     */
    private String buildSaveFileName(final String theFile) {
        return SAVES_DIR + '\\' + theFile + SAVES_EXTENSION;
    }
}



