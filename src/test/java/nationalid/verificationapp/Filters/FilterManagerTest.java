package nationalid.verificationapp.Filters;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import nationalid.SegmentedNationalID;

public class FilterManagerTest {

    @Test
    public void testAddAndApplyFilters() {
        TestingFiletrable testingFiletrableA = new TestingFiletrable();
        TestingFiletrable testingFiletrableB = new TestingFiletrable();

        FilterManager filterManager = new FilterManager(testingFiletrableA);
        filterManager.AddFilter(testingFiletrableB);

        List<SegmentedNationalID> testingList = new ArrayList<>();

        filterManager.ApplyFilters(testingList);

        Assert.assertTrue("Filter A was launched", testingFiletrableA.FilterApplied);
        Assert.assertTrue("Filter B was launched", testingFiletrableB.FilterApplied);
    }
}
