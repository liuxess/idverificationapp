package nationalid.verificationapp.Sorters;

import java.util.List;
import java.util.Optional;

import nationalid.SegmentedNationalID;
import nationalid.enums.NationalIDSegmentType;
import nationalid.models.Segments.NationalIDSegmentBase;
import nationalid.models.Segments.Specific.BirthDateSegment;
import nationalid.models.Segments.Specific.GenderSegment;
import nationalid.verificationapp.enums.SortingOrder;

/**
 * Will control sorting based on the date
 */
public class BirthDateSorter implements Sortable {

    private SortingOrder sortingOrder;

    /**
     * Will set what Order to set in.
     * 
     * @param order to sort in
     */
    public BirthDateSorter(String order) {
        this.sortingOrder = SortingOrder.OutOfString(order);
    }

    /**
     * @param Ascending whether it should short ascending
     */
    public BirthDateSorter(Boolean Ascending) {
        this.sortingOrder = SortingOrder.OutOfBoolean(Ascending);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nationalid.verificationapp.Sorters.Sortable#ApplySort(java.util.List)
     */
    @Override
    public List<SegmentedNationalID> ApplySort(List<SegmentedNationalID> IDs) {
        int invertIfDesc = sortingOrder.ToInt();
        IDs.sort((firstID, secondID) -> Compare(firstID, secondID) * invertIfDesc);
        return IDs;
    }

    /**
     * @param a
     * @param b
     * @return positive or negative for sorting the compared elements
     */
    private int Compare(SegmentedNationalID a, SegmentedNationalID b) {
        int centuryComparison = CompareByCentury(a, b);
        if (centuryComparison != 0)
            return centuryComparison;

        return CompareDates(a, b);
    }

    /**
     * @param a
     * @param b
     * @return positive or negative for sorting the compared elements based on
     *         BirthDates
     */
    private int CompareDates(SegmentedNationalID a, SegmentedNationalID b) {

        Optional<NationalIDSegmentBase> optionalBirthDateAValue = a.getSegment(NationalIDSegmentType.BIRTH_DATE);
        Optional<NationalIDSegmentBase> optionalBirthDateBValue = b.getSegment(NationalIDSegmentType.BIRTH_DATE);

        // If one doesn't have Birth Date,
        // push down the list as they will likely be incorrect
        if (optionalBirthDateAValue.isEmpty() || optionalBirthDateBValue.isEmpty())
            return 0;

        if (optionalBirthDateAValue.isEmpty())
            return -1; // Send a to the end

        if (optionalBirthDateBValue.isEmpty())
            return 1; // Send b to the end

        BirthDateSegment birthDateSegmentA = (BirthDateSegment) optionalBirthDateAValue.get();
        BirthDateSegment birthDateSegmentB = (BirthDateSegment) optionalBirthDateBValue.get();

        int birthDateValueA = birthDateSegmentA.getSegmentValue();
        int birthDateValueB = birthDateSegmentB.getSegmentValue();

        // larger date would always come later

        return birthDateValueA - birthDateValueB;
    }

    /**
     * Compares the gender segments as they also represent the century
     * 
     * @param a
     * @param b
     * @return positive if a is later, negative if b is later, 0 if they are from
     *         the
     *         same age
     */
    private int CompareByCentury(SegmentedNationalID a, SegmentedNationalID b) {

        Optional<NationalIDSegmentBase> optionalGenderAValue = a.getSegment(NationalIDSegmentType.GENDER);
        Optional<NationalIDSegmentBase> optionalGenderBValue = b.getSegment(NationalIDSegmentType.GENDER);

        // If one doesn't have gender, push down the list as they will likely be
        // incorrect
        if (optionalGenderAValue.isEmpty() || optionalGenderBValue.isEmpty())
            return 0;

        if (optionalGenderAValue.isEmpty())
            return -1; // Send a to the end

        if (optionalGenderBValue.isEmpty())
            return 1; // Send b to the end

        GenderSegment genderSegmentA = (GenderSegment) optionalGenderAValue.get();
        GenderSegment genderSegmentB = (GenderSegment) optionalGenderBValue.get();

        // Check if century is recognizable
        Optional<Integer> optionalCenturyA = genderSegmentA.getCentury();
        Optional<Integer> optionalCenturyB = genderSegmentB.getCentury();

        return ZeroIfNotPresent(optionalCenturyA)
                - ZeroIfNotPresent(optionalCenturyB);
    }

    private static Integer ZeroIfNotPresent(Optional<Integer> value) {
        return (value.isPresent() ? value.get() : 0);
    }

}
