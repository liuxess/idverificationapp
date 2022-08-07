package nationalid.verificationapp.Sorters;

import java.util.Arrays;
import java.util.List;

import nationalid.SegmentedNationalID;

public class SortingManager {

    private List<Sortable> registeredSorters;

    public SortingManager(List<Sortable> sorters) {
        registeredSorters = sorters;
    }

    public SortingManager(Sortable... sorters) {
        registeredSorters = Arrays.asList(sorters);
    }

    public void AddSorter(Sortable sorter) {
        registeredSorters.add(sorter);
    }

    public void ApplySort(List<SegmentedNationalID> IDs) {
        for (Sortable sorter : registeredSorters) {
            IDs = sorter.ApplySort(IDs);
        }
    }

}
