package nationalid.verificationapp.Sorters;

import java.util.Arrays;
import java.util.List;

import nationalid.SegmentedNationalID;

/**
 * Compounds and manages sorting from multiple Sortable objects
 * 
 * @see Sortable
 */
public class SortingManager {

    private List<Sortable> registeredSorters;

    public SortingManager(List<Sortable> sorters) {
        registeredSorters = sorters;
    }

    public SortingManager(Sortable... sorters) {
        registeredSorters = Arrays.asList(sorters);
    }

    /**
     * Adds an additional Sortable to the list
     * 
     * @param sorter to add
     * @see Sortable
     */
    public void AddSorter(Sortable sorter) {
        registeredSorters.add(sorter);
    }

    /**
     * Sorts the provided list with each Sortable in the order they were provided in
     * 
     * @param IDs to sort
     */
    public void ApplySort(List<SegmentedNationalID> IDs) {
        for (Sortable sorter : registeredSorters) {
            IDs = sorter.ApplySort(IDs);
        }
    }

}
