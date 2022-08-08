package nationalid.verificationapp.IO;

import java.io.IOException;
import nationalid.helpers.FileManager;
import nationalid.loggers.LogManager;

/**
 * Wrapper to read from a file and can cut the contet to lines
 */
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

    /**
     * @return file content cut into lines
     */
    public String[] GetContentFromFile() {
        String content = "";
        try {
            content = FileManager.ReadFromFile(fileName);
        } catch (IOException ex) {
            logManager.LogMessage(String.format("Failed to read from file %s ", fileName), ex);
        }

        return cutContentIntoLines(content);
    }

    /**
     * Cuts provided string line by line
     * 
     * @param content to split
     * @return array of lines from content
     */
    public static String[] cutContentIntoLines(String content) {
        // TODO: ensure all types of new liners
        return content.split("\r\n");
    }

}
