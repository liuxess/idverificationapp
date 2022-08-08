package nationalid.verificationapp.Sorters;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class SortingManagerTest {
    @Test
    public void testAddAndApplySort() {
        TestingSorter testingSorterA = new TestingSorter();
        TestingSorter testingSorterB = new TestingSorter();

        SortingManager sortingManager = new SortingManager(testingSorterA);
        sortingManager.AddSorter(testingSorterB);

        sortingManager.ApplySort(new ArrayList<>());

        Assert.assertTrue(testingSorterA.IsSorted);
        Assert.assertTrue(testingSorterB.IsSorted);
    }
}
