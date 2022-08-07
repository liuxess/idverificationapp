package nationalid.verificationapp.IO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nationalid.SegmentedNationalID;
import nationalid.helpers.FileManager;
import nationalid.loggers.LogManager;

public class FileInputManager {

    private String fileName;
    private LogManager logManager = LogManager.getGlobalInstance();

    public FileInputManager(String fileName, LogManager logManager) {
        this(fileName);
        this.logManager = logManager;
    }

    public FileInputManager(String fileName) {
        this.fileName = fileName;
    }

    public String[] GetContentFromFile() {
        String content = "";
        try {
            content = FileManager.ReadFromFile(fileName);
        } catch (IOException ex) {
            logManager.LogMessage(String.format("Failed to read from file %s ", fileName), ex);
        }

        return cutContentIntoLines(content);
    }

    public static String[] cutContentIntoLines(String content) {
        // TODO: ensure all types of new liners
        return content.split("\r\n");
    }

}
