package nationalid.verificationapp.Sorters;

import java.util.List;

import nationalid.SegmentedNationalID;
import nationalid.enums.NationalIDSegmentType;

public class BirthDateSorter implements Sortable {

    private Boolean Ascending;

    public BirthDateSorter(String order) {
        // Either the flag is desc, or it's ascending by default anyway
        this(order == null ? true : !order.contentEquals("desc"));
    }

    public BirthDateSorter(Boolean Ascending) {
        this.Ascending = Ascending;
    }

    @Override
    public List<SegmentedNationalID> ApplySort(List<SegmentedNationalID> IDs) {
        int invertIfDesc = Ascending ? 1 : -1;
        IDs.sort((firstID, secondID) -> Compare(firstID, secondID) * invertIfDesc);

        return IDs;
    }

    private int Compare(SegmentedNationalID a, SegmentedNationalID b) {
        int centuryComparison = CompareByCentury(a, b);
        if (centuryComparison != 0)
            return centuryComparison;

        return CompareDates(a, b);
    }

    private int CompareDates(SegmentedNationalID a, SegmentedNationalID b) {
        int birthDateValueA = a.getSegment(NationalIDSegmentType.BIRTH_DATE).getSegmentValue();
        int birthDateValueB = b.getSegment(NationalIDSegmentType.BIRTH_DATE).getSegmentValue();

        // larger date would always come later
        return birthDateValueA - birthDateValueB;
    }

    /**
     * Compares the gender segments as they also represent the century
     * 
     * @param a
     * @param b
     * @return true if a is later, false if b is later, null if they are from the
     *         same age
     */
    private int CompareByCentury(SegmentedNationalID a, SegmentedNationalID b) {
        // TODO: possibly solvable with XOR predicate on IsMale() and value comparison;

        int GenderAValue = a.getSegment(NationalIDSegmentType.GENDER).getSegmentValue();
        int GenderBValue = b.getSegment(NationalIDSegmentType.GENDER).getSegmentValue();

        // Same value, same century;
        if (GenderAValue == GenderBValue)
            return 0;

        // Both less than 5, so 3 and 4 means it's 20th century
        if (GenderAValue < 5 && GenderBValue < 5)
            return 0;

        // Both less than 4, so 5 and 6 means it's 21st century
        if (GenderAValue > 4 && GenderBValue > 4)
            return 0;

        // if they are not from the same century, check which is larger
        return GenderAValue - GenderBValue;
    }

}
