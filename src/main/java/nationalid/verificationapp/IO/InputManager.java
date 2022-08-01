package nationalid.verificationapp.IO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nationalid.SegmentedNationalID;
import nationalid.helpers.FileManager;
import nationalid.loggers.LogManager;

public class InputManager {

    private String fileName;

    public InputManager(String fileName) {
        this.fileName = fileName;
    }

    public List<SegmentedNationalID> ReadIDsFromFile(LogManager logManager) throws IOException {

        String fileContents = FileManager.ReadFromFile(fileName);
        return ReadIDsFromFromString(logManager, fileContents);
    }

    public static List<SegmentedNationalID> ReadIDsFromFromString(LogManager logManager, String text) {

        String[] contentLines = cutContentIntoLines(text);

        return getIDsFromString(logManager, contentLines);
    }

    public static List<SegmentedNationalID> getIDsFromString(LogManager logManager, String... lines) {
        final ArrayList<SegmentedNationalID> IDList = new ArrayList<>();

        Arrays.stream(lines).forEach(Line -> {
            try {
                IDList.add(ParseSegmentedIDFromString(Line));
            } catch (NumberFormatException ex) {
                logManager.LogMessage(String.format("Could not parse a number out of line: %s", Line));
            }
        });

        return IDList;
    }

    public static SegmentedNationalID ParseSegmentedIDFromString(String line) throws NumberFormatException {
        long ID = Long.parseLong(line);
        return new SegmentedNationalID(ID);
    }

    public static String[] cutContentIntoLines(String content) {
        // TODO: ensure all types of new liners
        return content.split("\r\n");
    }

}
