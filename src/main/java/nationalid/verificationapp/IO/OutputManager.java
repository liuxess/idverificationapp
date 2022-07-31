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
import nationalid.verificationapp.CategorizedIDLists;

public class OutputManager {

    final LogManager logManager;

    public OutputManager(Logable... loggers) {
        logManager = new LogManager(loggers);
    }

    private static String generateFileNameforIDs(Boolean valid) {
        return valid ? "Valid.txt" : "Invalid.txt";
    }

    public void OutputMessage(CategorizedIDLists IDLists) {
        outputCorrectIDs(IDLists.getCorrect());
        outputIncorrectIDs(IDLists.getIncorrect());

        int validSize = IDLists.getCorrect().size();
        int totalSize = validSize + IDLists.getIncorrect().size();

        String outputMessage = String.format("\r\nRecognized %d valid IDs out of %d IDs", validSize, totalSize);
        System.out.println(outputMessage);
    }

    private void outputCorrectIDs(List<SegmentedNationalID> IDs) {
        final String fileName = generateFileNameforIDs(true);

        if (FileManager.FileExists(fileName))
            FileManager.DeleteFile(fileName);

        List<String> stringifiedIDs = new ArrayList<>();

        IDs.forEach(ID -> stringifiedIDs.add(String.valueOf(ID.getID().getID())));

        try {
            FileManager.ForceWriteToFile(fileName, String.join("\r\n", stringifiedIDs));
        } catch (IOException ex) {
            logManager.LogMessage(String.format("Failed logging to file %s", fileName), ex);
        }

    }

    private void outputIncorrectIDs(List<SegmentedNationalID> IDs) {
        final String fileName = generateFileNameforIDs(false);

        if (FileManager.FileExists(fileName))
            FileManager.DeleteFile(fileName);

        IDs.stream().forEach(ID -> {
            String outputMessage = String.format(
                    "\r\nID %s has shown the following problems: {\r\n\t%s\r\n}",
                    ID.getID().getID(),
                    String.join("\r\n\t", ID.getProblemList()));

            try {
                FileManager.ForceWriteToFile(fileName, outputMessage);
            } catch (IOException ex) {
                logManager.LogMessage(String.format("Failed logging to file %s", fileName), ex);
            }
        });
    }

}
