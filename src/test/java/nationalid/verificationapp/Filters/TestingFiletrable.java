package nationalid.verificationapp.Filters;

import java.util.List;

import nationalid.SegmentedNationalID;

public class TestingFiletrable implements Filterable {

    public Boolean FilterApplied;

    @Override
    public Boolean ApplyFilter(SegmentedNationalID ID) {
        FilterApplied = true;
        return true;
    }

    @Override
    public List<SegmentedNationalID> ApplyFilter(List<SegmentedNationalID> IDs) {
        FilterApplied = true;
        return IDs;
    }

}
