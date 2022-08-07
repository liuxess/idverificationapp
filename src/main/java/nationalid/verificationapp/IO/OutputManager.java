package nationalid.verificationapp.IO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nationalid.SegmentedNationalID;
import nationalid.helpers.FileManager;
import nationalid.interfaces.Logable;
import nationalid.loggers.ConsoleLogger;
import nationalid.loggers.FileLogger;
import nationalid.loggers.LogManager;
import nationalid.verificationapp.BatchProcessor;
import nationalid.verificationapp.CategorizedIDLists;

public class OutputManager {

    final LogManager logManager;

    public OutputManager(LogManager logManager) {
        this.logManager = logManager;
    }

    public OutputManager() {
        this.logManager = LogManager.getGlobalInstance();
    }

    private static String generateFileNameforIDs(Boolean valid) {
        return valid ? "Valid.txt" : "Invalid.txt";
    }

    public void Output(CategorizedIDLists IDLists) {
        outputCorrectIDsToFile(IDLists.getCorrect());
        outputIncorrectIDsToFile(IDLists.getIncorrect());
        System.out.println(IDLists.getResultMessage());
    }

    private void outputCorrectIDsToFile(List<SegmentedNationalID> IDs) {
        final String fileName = generateFileNameforIDs(true);

        DeleteFileIfExists(fileName);

        List<String> stringifiedIDs = BatchProcessor.getIDsFromSegmentation(IDs);

        try {
            FileManager.ForceWriteToFile(fileName, String.join("\r\n", stringifiedIDs));
        } catch (IOException ex) {
            logManager.LogMessage(String.format("Failed logging to file %s", fileName), ex);
        }
    }

    private void outputIncorrectIDsToFile(List<SegmentedNationalID> IDs) {
        final String fileName = generateFileNameforIDs(false);

        DeleteFileIfExists(fileName);

        IDs.stream().forEach(ID -> {
            if (ID == null) {
                return;
            }

            String outputMessage = String.format(
                    "\r\nID %s has shown the following problems: {\r\n\t%s\r\n}",
                    ID.getNationalID().getID(),
                    String.join("\r\n\t", ID.getProblemList()));

            try {
                FileManager.ForceWriteToFile(fileName, outputMessage);
            } catch (IOException ex) {
                logManager.LogMessage(String.format("Failed logging to file %s", fileName), ex);
            }
        });
    }

    private void DeleteFileIfExists(String fileName) {
        if (FileManager.FileExists(fileName))
            FileManager.DeleteFile(fileName);

    }

}
