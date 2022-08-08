package nationalid.verificationapp;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Test;

import nationalid.SegmentedNationalID;
import nationalid.models.Calculators.GlobalCodeCalculator;
import nationalid.models.Calculators.LithuanianCodeCalculator;

public class IDVerificatorTest {

    public ArrayList<SegmentedNationalID> getGoodIDs() {
        ArrayList<SegmentedNationalID> segmentedNationalIDs = new ArrayList<>();
        segmentedNationalIDs.add(new SegmentedNationalID("40002263113"));
        segmentedNationalIDs.add(new SegmentedNationalID("61101173640"));
        segmentedNationalIDs.add(new SegmentedNationalID("69607223159"));
        return segmentedNationalIDs;
    }

    public ArrayList<SegmentedNationalID> getBadIDs() {
        ArrayList<SegmentedNationalID> segmentedNationalIDs = new ArrayList<>();
        segmentedNationalIDs.add(new SegmentedNationalID("69901144839"));
        segmentedNationalIDs.add(new SegmentedNationalID("46002268805"));
        segmentedNationalIDs.add(new SegmentedNationalID("63004198119"));
        return segmentedNationalIDs;
    }

    @Test
    public void testVerifyIDs() {
        GlobalCodeCalculator.setGlobalInstance(new LithuanianCodeCalculator());

        ArrayList<SegmentedNationalID> goodIDs = getGoodIDs();
        ArrayList<SegmentedNationalID> badIDs = getBadIDs();

        ArrayList<SegmentedNationalID> segmentedNationalIDs = new ArrayList<>();
        segmentedNationalIDs.addAll(goodIDs);
        segmentedNationalIDs.addAll(badIDs);

        CategorizedIDLists categorizedIDLists = IDVerificator.VerifyIDs(segmentedNationalIDs);

        Assert.assertTrue("All Valid exist", categorizedIDLists.getCorrect().stream().parallel()
                .allMatch(MatchAnyFromList(goodIDs)));

        Assert.assertTrue("All Invalid exist",
                categorizedIDLists.getIncorrect().stream().parallel()
                        .allMatch(MatchAnyFromList(badIDs)));

    }

    private Predicate<? super SegmentedNationalID> MatchAnyFromList(ArrayList<SegmentedNationalID> IDList) {
        return providedID -> IDList.stream().anyMatch(IDFromList -> IDFromList == providedID);
    }
}
