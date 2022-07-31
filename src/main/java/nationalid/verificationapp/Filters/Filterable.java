package nationalid.verificationapp.Filters;

import java.util.List;

import nationalid.SegmentedNationalID;

public interface Filterable {

    Boolean ApplyFilter(SegmentedNationalID ID);

    List<SegmentedNationalID> ApplyFilter(List<SegmentedNationalID> ID);
}
