package nationalid.verificationapp.Sorters;

import java.util.List;

import nationalid.SegmentedNationalID;

public class TestingSorter implements Sortable {

    public boolean IsSorted = false;

    @Override
    public List<SegmentedNationalID> ApplySort(List<SegmentedNationalID> IDs) {
        IsSorted = true;
        return IDs;
    }

}
