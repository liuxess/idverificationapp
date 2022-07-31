package nationalid.verificationapp.Sorters;

import java.util.List;

import nationalid.SegmentedNationalID;

public interface Sortable {

    List<SegmentedNationalID> ApplySort(List<SegmentedNationalID> IDs);

}
