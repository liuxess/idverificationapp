package nationalid.verificationapp.IO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nationalid.SegmentedNationalID;
import nationalid.helpers.FileManager;
import nationalid.loggers.ConsoleLogger;
import nationalid.loggers.FileLogger;
import nationalid.loggers.LogManager;
import nationalid.models.NationalID;

public class InputManager {

    private String fileName;

    public InputManager(String fileName) {
        this.fileName = fileName;
    }

    public List<SegmentedNationalID> ReadIDsFromFile(LogManager logManager) throws IOException {

        String fileContents = FileManager.ReadFromFile(fileName);
        String[] contentLines = cutContentIntoLines(fileContents);

        return getIDsFromString(logManager, contentLines);
    }

    public static List<SegmentedNationalID> getIDsFromString(LogManager logManager, String... lines) {
        final ArrayList<SegmentedNationalID> IDList = new ArrayList<>();

        Arrays.stream(lines).forEach(Line -> {
            try {
                long ID;
                ID = Long.parseLong(Line);
                IDList.add(new SegmentedNationalID(ID));
            } catch (NumberFormatException ex) {
                logManager.LogMessage(String.format("Could not parse a number out of line: %s", Line));
            }
        });

        return IDList;
    }

    private String[] cutContentIntoLines(String content) {
        // TODO: ensure all types of new liners
        return content.split("\r\n");
    }

}
