package nationalid.verificationapp;

import java.util.ArrayList;
import java.util.List;

import nationalid.SegmentedNationalID;
import nationalid.models.NationalID;
import nationalid.verificationapp.Filters.FilterManager;
import nationalid.verificationapp.Sorters.SortingManager;

/**
 * Manages Batch Processing for National IDs
 */
public class BatchProcessor {

    private FilterManager filterManager;
    private SortingManager sortingManager;

    /**
     * Creates a batch processor for empty filtering and empty sorting
     */
    public BatchProcessor() {
        this.filterManager = new FilterManager(new ArrayList<>());
        this.sortingManager = new SortingManager(new ArrayList<>());
    }

    /**
     * Create a batch Processor with provided filtering and sorting
     * 
     * @param filterManager
     * @param sortingManager
     */
    public BatchProcessor(FilterManager filterManager, SortingManager sortingManager) {
        this.filterManager = filterManager;
        this.sortingManager = sortingManager;
    }

    /**
     * Sorts and filters the provided list
     * 
     * @param segmentedNationalIDs to process
     */
    public void SortAndFilter(List<SegmentedNationalID> segmentedNationalIDs) {
        filterManager.ApplyFilters(segmentedNationalIDs);
        sortingManager.ApplySort(segmentedNationalIDs);
    }

    // region: static methods
    /**
     * Transforms Strings into NationalIDs
     * 
     * @param IDs to parse
     * @return list of NationalIDs
     * @see nationalid.models.NationalID
     */
    public static List<NationalID> ToNationalIDs(List<String> IDs) {
        return IDs.stream().parallel().map(ID -> new NationalID(ID)).toList();
    }

    /**
     * Transforms Strings into SegmentedNationalIDs
     * 
     * @param IDs to parse
     * @return list of SegmentedNationalID
     * @see nationalid.SegmentedNationalID
     */
    public static List<SegmentedNationalID> ToSegmentedNationalIDs(List<String> IDs) {
        return new ArrayList<>(IDs.stream().parallel().map(ID -> new SegmentedNationalID(ID)).toList());
    }

    /**
     * Transforms NationalIDs into SegmentedNationalIDs
     * 
     * @param IDs to parse
     * @return list of SegmentedNationalID
     * @see nationalid.SegmentedNationalID
     */
    public static List<SegmentedNationalID> SegmentNationalIDs(List<NationalID> nationalIDs) {
        return new ArrayList<>(nationalIDs.stream().parallel().map(nationalID -> nationalID.Segment()).toList());
    }

    /**
     * Extracts IDs from SemgentedNationalIDs
     * 
     * @param IDs to process
     * @return ID strings
     */
    public static List<String> getIDsFromSegmentation(List<SegmentedNationalID> IDs) {
        return IDs.stream().parallel().map(ID -> ID.getNationalID().getID()).toList();
    }
    // endregion

}
