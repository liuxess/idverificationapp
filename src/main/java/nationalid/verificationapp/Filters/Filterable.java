package nationalid.verificationapp.Filters;

import java.util.List;
import nationalid.SegmentedNationalID;

public interface Filterable {

    /**
     * @param ID to check out
     * @return whether the ID applies to the filter
     */
    Boolean ApplyFilter(SegmentedNationalID ID);

    /**
     * Will filter the reference based on implementation and return it
     * 
     * @param ID list of IDs to filter
     * @return a filtered list
     */
    List<SegmentedNationalID> ApplyFilter(List<SegmentedNationalID> ID);
}
