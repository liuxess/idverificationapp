package nationalid.verificationapp;

import java.util.ArrayList;
import java.util.List;
import nationalid.SegmentedNationalID;

public class IDVerificator {

    public static CategorizedIDLists VerifyIDs(List<SegmentedNationalID> IDs) {
        final ArrayList<SegmentedNationalID> correctIDs;
        final ArrayList<SegmentedNationalID> incorrectIDs;

        correctIDs = new ArrayList<>(
                IDs.stream().parallel().filter(segmentedID -> segmentedID.VerifyIntegrity()).toList());
        incorrectIDs = new ArrayList<>(IDs.stream().parallel().filter(segmentedID -> correctIDs.stream().parallel()
                .noneMatch(correctID -> segmentedID.getNationalID().getID() == correctID.getNationalID().getID()))
                .toList());

        return new CategorizedIDLists(correctIDs, incorrectIDs);
    }

}
