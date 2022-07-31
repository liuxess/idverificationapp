package nationalid.verificationapp;

import java.util.List;

import nationalid.SegmentedNationalID;

public class CategorizedIDLists {

    private List<SegmentedNationalID> correctIDs;
    private List<SegmentedNationalID> incorrectIDs;

    public CategorizedIDLists(List<SegmentedNationalID> correctIDs, List<SegmentedNationalID> incorrectIDs) {
        this.correctIDs = correctIDs;
        this.incorrectIDs = incorrectIDs;
    }

    public List<SegmentedNationalID> getCorrect() {
        return correctIDs;
    }

    public List<SegmentedNationalID> getIncorrect() {
        return incorrectIDs;
    }

}
