package nationalid.verificationapp.Sorters;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import nationalid.SegmentedNationalID;

public class BirthDateSorterTest {

    @Test
    public void testApplySort() {

        SegmentedNationalID earliestID = new SegmentedNationalID("30001010000");
        SegmentedNationalID earlyMidID = new SegmentedNationalID("40101010000");
        SegmentedNationalID lateMidID = new SegmentedNationalID("50201010000");
        SegmentedNationalID latestID = new SegmentedNationalID("60301010000");

        List<SegmentedNationalID> allIDs = Arrays.asList(lateMidID, earliestID, earlyMidID, latestID);

        BirthDateSorter birthDateSorterAsc = new BirthDateSorter(true);
        birthDateSorterAsc.ApplySort(allIDs);

        Assert.assertTrue("Earliest is first", earliestID == allIDs.get(0));
        Assert.assertTrue("Mid early is second", earlyMidID == allIDs.get(1));
        Assert.assertTrue("Mid late is third", lateMidID == allIDs.get(2));
        Assert.assertTrue("Latest is last", latestID == allIDs.get(3));

        BirthDateSorter birthDateSorterDesc = new BirthDateSorter(false);
        birthDateSorterDesc.ApplySort(allIDs);

        Assert.assertTrue("Latest is first", latestID == allIDs.get(0));
        Assert.assertTrue("Mid late is second", lateMidID == allIDs.get(1));
        Assert.assertTrue("Mid early is third", earlyMidID == allIDs.get(2));
        Assert.assertTrue("Earliest is last", earliestID == allIDs.get(3));

    }
}
