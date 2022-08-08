package nationalid.verificationapp.Filters;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import nationalid.SegmentedNationalID;
import nationalid.enums.Gender;

public class GenderFilterTest {

    private static record GenderedSegmentedIDs(ArrayList<SegmentedNationalID> IDs, Gender gender) {
    };

    private static ArrayList<SegmentedNationalID> getMaleIDs() {
        ArrayList<SegmentedNationalID> returnable = new ArrayList<>();
        returnable.add(new SegmentedNationalID("39905631075"));
        returnable.add(new SegmentedNationalID("59905631075"));
        returnable.add(new SegmentedNationalID("50005631075"));
        return returnable;

    }

    private static ArrayList<SegmentedNationalID> getFemaleIDs() {
        ArrayList<SegmentedNationalID> returnable = new ArrayList<>();
        returnable.add(new SegmentedNationalID("49905631075"));
        returnable.add(new SegmentedNationalID("69905631075"));
        returnable.add(new SegmentedNationalID("40005631075"));
        return returnable;

    }

    private static GenderedSegmentedIDs getMaleGenderedSegmentedIDs() {
        return new GenderedSegmentedIDs(getMaleIDs(), Gender.MALE);
    }

    private static GenderedSegmentedIDs getFemaleGenderedSegmentedIDs() {
        return new GenderedSegmentedIDs(getFemaleIDs(), Gender.FEMALE);
    }

    @Test
    public void testApplyFilter() {
        GenderedSegmentedIDs femaleIDs = getFemaleGenderedSegmentedIDs();
        GenderedSegmentedIDs maleIDs = getMaleGenderedSegmentedIDs();

        ArrayList<SegmentedNationalID> allIDs = new ArrayList<>();
        allIDs.addAll(maleIDs.IDs);
        allIDs.addAll(femaleIDs.IDs);

        // create a copy
        ArrayList<SegmentedNationalID> IDsToFilter = new ArrayList<>(allIDs);

        GenderFilter genderFilterForMales = new GenderFilter(Gender.MALE);
        genderFilterForMales.ApplyFilter(IDsToFilter);

        Assert.assertTrue("No females should be found", IDsToFilter.stream().parallel()
                .noneMatch(FilteredID -> femaleIDs.IDs.stream().anyMatch(femaleID -> femaleID == FilteredID)));
        Assert.assertTrue("Only males should be found", IDsToFilter.stream().parallel()
                .anyMatch(FilteredID -> maleIDs.IDs.stream().anyMatch(maleID -> maleID == FilteredID)));

        // create a copy
        IDsToFilter = new ArrayList<>(allIDs);

        GenderFilter genderFilterForFemales = new GenderFilter(Gender.FEMALE);
        genderFilterForFemales.ApplyFilter(IDsToFilter);

        Assert.assertTrue("No males should be found", IDsToFilter.stream().parallel()
                .noneMatch(FilteredID -> maleIDs.IDs.stream().anyMatch(maleID -> maleID == FilteredID)));
        Assert.assertTrue("Only females should be found", IDsToFilter.stream().parallel()
                .anyMatch(FilteredID -> femaleIDs.IDs.stream().anyMatch(femaleID -> femaleID == FilteredID)));
    }

}
