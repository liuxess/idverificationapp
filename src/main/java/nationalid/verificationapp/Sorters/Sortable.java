package nationalid.verificationapp.Sorters;

import java.util.List;

import nationalid.SegmentedNationalID;

/**
 * Indicates the implementation can be used for Sorting NationalIDs
 */
public interface Sortable {

    List<SegmentedNationalID> ApplySort(List<SegmentedNationalID> IDs);

}
