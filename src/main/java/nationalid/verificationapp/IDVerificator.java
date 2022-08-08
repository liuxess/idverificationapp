package nationalid.verificationapp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import nationalid.SegmentedNationalID;

/**
 * Wrapper for Verifying and categorizing IDs
 */
public class IDVerificator {

    /**
     * @param IDs to verify and categorize
     * @return a categorized list
     */
    public static CategorizedIDLists VerifyIDs(List<SegmentedNationalID> IDs) {
        final ArrayList<SegmentedNationalID> correctIDs;
        final ArrayList<SegmentedNationalID> incorrectIDs;

        // collect IDs that are correct
        correctIDs = new ArrayList<>(
                IDs.stream().parallel().filter(segmentedID -> segmentedID.VerifyIntegrity()).toList());

        // collect leftover IDs
        incorrectIDs = new ArrayList<>(
                IDs.stream().parallel()
                        .filter(ExistsInList(correctIDs))
                        .toList());

        return new CategorizedIDLists(correctIDs, incorrectIDs);
    }

    private static Predicate<? super SegmentedNationalID> ExistsInList(
            final ArrayList<SegmentedNationalID> IDList) {
        return segmentedID -> IDList.stream().parallel()
                .noneMatch(correctID -> IDsMatch(segmentedID, correctID));
    }

    private static boolean IDsMatch(SegmentedNationalID segmentedID, SegmentedNationalID correctID) {
        return segmentedID.getNationalID().getID() == correctID.getNationalID().getID();
    }

}
