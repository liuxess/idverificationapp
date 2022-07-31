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

    public List<SegmentedNationalID> ReadIDsFromFile() throws IOException {
        final ArrayList<SegmentedNationalID> IDList = new ArrayList<>();

        String fileContents = FileManager.ReadFromFile(fileName);
        String[] contentLines = cutContentIntoLines(fileContents);

        LogManager logManager = new LogManager(false, new FileLogger(), new ConsoleLogger());

        Arrays.stream(contentLines).forEach(Line -> {
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
