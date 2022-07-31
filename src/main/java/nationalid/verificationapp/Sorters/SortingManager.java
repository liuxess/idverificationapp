package nationalid.verificationapp.Sorters;

import java.util.List;

import nationalid.SegmentedNationalID;

public class SortingManager {

    private List<Sortable> registeredSorters;

    public SortingManager(List<Sortable> sorters) {
        registeredSorters = sorters;
    }

    public void ApplySort(List<SegmentedNationalID> IDs) {
        for (Sortable sorter : registeredSorters) {
            sorter.ApplySort(IDs);
        }
    }

}
