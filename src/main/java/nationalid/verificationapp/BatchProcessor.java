package nationalid.verificationapp;

import java.util.ArrayList;
import java.util.List;

import nationalid.SegmentedNationalID;
import nationalid.models.NationalID;
import nationalid.verificationapp.Filters.FilterManager;
import nationalid.verificationapp.Filters.Filterable;
import nationalid.verificationapp.Sorters.SortingManager;

public class BatchProcessor {

    private FilterManager filterManager;
    private SortingManager sortingManager;

    public BatchProcessor() {
        this.filterManager = new FilterManager(new ArrayList<Filterable>());
        this.sortingManager = new SortingManager(new ArrayList<>());
    }

    public BatchProcessor(FilterManager filterManager, SortingManager sortingManager) {
        this.filterManager = filterManager;
        this.sortingManager = sortingManager;
    }

    public static List<NationalID> ToNationalIDs(List<String> IDs) {
        return IDs.stream().parallel().map(ID -> new NationalID(ID)).toList();
    }

    public static List<SegmentedNationalID> ToSegmentedNationalIDs(List<String> IDs) {
        return new ArrayList<>(IDs.stream().parallel().map(ID -> new SegmentedNationalID(ID)).toList());
    }

    public static List<SegmentedNationalID> SegmentNationalIDs(List<NationalID> nationalIDs) {
        return new ArrayList<>(nationalIDs.stream().parallel().map(nationalID -> nationalID.Segment()).toList());
    }

    public void SortAndFilter(List<SegmentedNationalID> segmentedNationalIDs) {
        filterManager.ApplyFilters(segmentedNationalIDs);
        sortingManager.ApplySort(segmentedNationalIDs);
    }

    public static List<String> getIDsFromSegmentation(List<SegmentedNationalID> IDs) {
        return IDs.stream().parallel().map(ID -> ID.getNationalID().getID()).toList();
    }

}
