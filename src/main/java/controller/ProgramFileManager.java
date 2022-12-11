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

public class ProgramFileManager {

    private static ProgramFileManager INSTANCE;

    private static final String SAVES_EXTENSION =
            ".dungeon";
    private static final String SAVES_NAME =
            "\\saves";
    private static final String LOG_NAME =
            "\\log.txt";
    private static final String BACKUP_PROGRAM_DIR =
            "Dungeon Adventure";
    private static final String BACKUP_SAVES_DIR =
            BACKUP_PROGRAM_DIR + SAVES_NAME;
    private static final String BACKUP_LOG_PATH =
            BACKUP_PROGRAM_DIR + LOG_NAME;
    private static final String PROGRAM_DIR =
            System.getenv("APPDATA") + "\\" + BACKUP_PROGRAM_DIR;
    private static final String SAVES_DIR =
            PROGRAM_DIR + SAVES_NAME;
    private static final String LOG_PATH =
            PROGRAM_DIR + LOG_NAME;

    private static final String SAVING_ERROR =
            "The game could not be saved as \"%s\". Try again.";
    private static final String LOADING_ERROR =
            "The file \"%s\" could not be loaded. Try again.";
    private static final String INVALID_FILE =
            "The file \"%s\" is invalid. Try again";

    private final boolean myUsesPrimaryDir;

    private ProgramFileManager(final boolean theUsesPrimaryDir) {
        myUsesPrimaryDir = theUsesPrimaryDir;
    }

    public static ProgramFileManager getInstance() {
        return INSTANCE;
    }

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

    private static boolean createDirs(final String theDir) {
        final File dir = new File(theDir);

        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        return dir.exists();
    }

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

    public void logException(final Exception theException,
                             final boolean thePrintToConsoleOnSuccess) {
        logException(
                theException,
                theException.getMessage(), thePrintToConsoleOnSuccess
        );
    }

    String getLogPath() {
        return myUsesPrimaryDir ? LOG_PATH : BACKUP_LOG_PATH;
    }

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

    private String buildSaveFileName(final String theFile) {
        return SAVES_DIR + '\\' + theFile + SAVES_EXTENSION;
    }
}
