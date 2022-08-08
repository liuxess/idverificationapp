package nationalid.verificationapp.Filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nationalid.SegmentedNationalID;

/**
 * Manages and compounds all registered Filterable objects
 * 
 * @see Filterable
 */
public class FilterManager {

    private ArrayList<Filterable> registeredFilters;

    public FilterManager(List<Filterable> registeredFilters) {
        this.registeredFilters = new ArrayList<>(registeredFilters);
    }

    public FilterManager(Filterable... registeredFilters) {
        this.registeredFilters = new ArrayList<>(Arrays.asList(registeredFilters));
    }

    /**
     * Add an additional filtering object
     * 
     * @param filter to add
     */
    public void AddFilter(Filterable filter) {
        registeredFilters.add(filter);
    }

    /**
     * Will Apply filtering from each of the registered filters
     * 
     * @param IDs to filter
     */
    public void ApplyFilters(List<SegmentedNationalID> IDs) {
        for (int i = 0; i < registeredFilters.size(); i++) {
            registeredFilters.get(i).ApplyFilter(IDs);
        }
    }

}
