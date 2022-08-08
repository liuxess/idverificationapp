package nationalid.verificationapp;

import java.util.List;

import nationalid.SegmentedNationalID;

/**
 * stores Categorized IDs by correct and incorrect ones
 */
public class CategorizedIDLists {

    private List<SegmentedNationalID> correctIDs;
    private List<SegmentedNationalID> incorrectIDs;

    /**
     * @param correctIDs   IDs without errors
     * @param incorrectIDs IDs with errors
     */
    public CategorizedIDLists(List<SegmentedNationalID> correctIDs, List<SegmentedNationalID> incorrectIDs) {
        this.correctIDs = correctIDs;
        this.incorrectIDs = incorrectIDs;
    }

    /**
     * @return IDs that are correct
     */
    public List<SegmentedNationalID> getCorrect() {
        return correctIDs;
    }

    /**
     * @return IDs that are incorrect
     */
    public List<SegmentedNationalID> getIncorrect() {
        return incorrectIDs;
    }

    /**
     * @return a message to explain results
     */
    public String getResultMessage() {
        int totalGood = correctIDs.size();
        int total = totalGood + incorrectIDs.size();

        return String.format("\r\nRecognized %d valid IDs out of %d IDs", totalGood, total);
    }

}
