package nationalid.verificationapp.Filters;

import java.util.List;

import nationalid.SegmentedNationalID;

public class FilterManager {

    private List<Filterable> registeredFilters;

    public FilterManager(List<Filterable> registeredFilters) {
        this.registeredFilters = registeredFilters;
    }

    public void ApplyFilters(List<SegmentedNationalID> IDs) {
        for (Filterable filter : registeredFilters) {
            filter.ApplyFilter(IDs);
        }
    }

}
