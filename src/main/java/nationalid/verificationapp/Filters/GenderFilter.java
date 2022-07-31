package nationalid.verificationapp.Filters;

import java.util.List;

import nationalid.SegmentedNationalID;
import nationalid.enums.NationalIDSegmentType;
import nationalid.models.Segments.Specific.GenderSegment;

public class GenderFilter implements Filterable {

    Boolean expectingMale;

    public GenderFilter(String input) throws Exception {
        if (input.contentEquals("male") || input.contentEquals("female")) {
            expectingMale = input.contentEquals("male");
            return;
        }

        expectingMale = null;
    }

    public GenderFilter(Boolean Male) {
        expectingMale = Male;
    }

    public Boolean ApplyFilter(SegmentedNationalID ID) {
        if (expectingMale == null) {
            return true;
        }

        GenderSegment genderSegment = (GenderSegment) ID.getSegment(NationalIDSegmentType.GENDER);
        if (genderSegment != null) {
            return genderSegment.IsMale() == expectingMale;
        }

        // If You do not have a gender segment, You're definetily not male or female
        return false;
    }

    @Override
    public List<SegmentedNationalID> ApplyFilter(List<SegmentedNationalID> IDs) {
        if (expectingMale == null) {
            return IDs;
        }

        IDs.removeIf(ID -> !ApplyFilter(ID));
        return IDs;
    }

}
