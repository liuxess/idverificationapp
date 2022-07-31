package nationalid.verificationapp;

import java.util.ArrayList;
import java.util.List;
import nationalid.SegmentedNationalID;
import nationalid.models.NationalID;

public class IDVerificator {

    public CategorizedIDLists VerifyIDs(List<SegmentedNationalID> IDs) {
        final List<SegmentedNationalID> correctIDs = new ArrayList<>();
        final List<SegmentedNationalID> incorrectIDs = new ArrayList<>();

        IDs.parallelStream().forEach(segmentedID -> {
            if (segmentedID.VerifyIntegrity())
                correctIDs.add(segmentedID);
            else
                incorrectIDs.add(segmentedID);
        });

        return new CategorizedIDLists(correctIDs, incorrectIDs);
    }

}
