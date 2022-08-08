package nationalid.verificationapp;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import nationalid.SegmentedNationalID;
import nationalid.models.NationalID;
import nationalid.verificationapp.Filters.FilterManager;
import nationalid.verificationapp.Filters.TestingFiletrable;
import nationalid.verificationapp.Sorters.SortingManager;
import nationalid.verificationapp.Sorters.TestingSorter;

public class BatchProcessorTest {

    public static List<String> getIDs() {
        ArrayList<String> IDs = new ArrayList<>();
        IDs.add("45545020154");
        IDs.add("78552216548");
        IDs.add("75000182168");
        IDs.add("31244872567");
        return IDs;
    }

    @Test
    public void testToNationalIDs() {
        List<String> IDs = getIDs();
        List<NationalID> nationalIDs = BatchProcessor.ToNationalIDs(IDs);
        Assert.assertTrue("Processed number is the same", IDs.size() == nationalIDs.size());
    }

    @Test
    public void testToSegmentedNationalIDs() {
        List<String> IDs = getIDs();
        List<SegmentedNationalID> nationalIDs = BatchProcessor.ToSegmentedNationalIDs(IDs);
        Assert.assertTrue("Processed number is the same", IDs.size() == nationalIDs.size());
    }

    @Test
    public void testGetIDsFromSegmentation() {
        List<String> IDs = getIDs();
        List<SegmentedNationalID> nationalIDs = BatchProcessor.ToSegmentedNationalIDs(IDs);
        List<String> parsedIDs = BatchProcessor.getIDsFromSegmentation(nationalIDs);
        Assert.assertTrue(IDs.containsAll(parsedIDs));
        Assert.assertTrue(parsedIDs.containsAll(IDs));
    }

    @Test
    public void testSegmentNationalIDs() {
        List<String> IDs = getIDs();
        List<NationalID> nationalIDs = BatchProcessor.ToNationalIDs(IDs);
        List<SegmentedNationalID> segmentedNationalIDs = BatchProcessor.SegmentNationalIDs(nationalIDs);
        Assert.assertTrue("All segmentations have corresponding ID",
                segmentedNationalIDs.stream().parallel()
                        .allMatch(segmented -> IDs.stream().anyMatch(ID -> ID == segmented.getNationalID().getID())));
    }

    @Test
    public void testSortAndFilter() {
        TestingFiletrable testingFiletrable = new TestingFiletrable();
        TestingSorter testingSorter = new TestingSorter();
        FilterManager filterManager = new FilterManager(testingFiletrable);
        SortingManager sortingManager = new SortingManager(testingSorter);

        BatchProcessor batchProcessor = new BatchProcessor(filterManager, sortingManager);

        batchProcessor.SortAndFilter(new ArrayList<>());

        Assert.assertTrue(testingFiletrable.FilterApplied);
        Assert.assertTrue(testingSorter.IsSorted);

    }

}
