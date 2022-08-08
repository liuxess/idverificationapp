package nationalid.verificationapp.Filters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nationalid.SegmentedNationalID;
import nationalid.enums.Gender;
import nationalid.enums.NationalIDSegmentType;
import nationalid.models.Segments.NationalIDSegmentBase;
import nationalid.models.Segments.Specific.GenderSegment;

/**
 * Will filter against the gender
 * 
 * @see Filterable
 * @see FilterManager
 */
public class GenderFilter implements Filterable {

    Optional<Gender> expectedGender;

    public GenderFilter(Gender expectedGender) {
        this.expectedGender = Optional.of(expectedGender);
    }

    public GenderFilter(String input) {
        this.expectedGender = StringToGender(input);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nationalid.verificationapp.Filters.Filterable#ApplyFilter(nationalid.
     * SegmentedNationalID)
     */
    public Boolean ApplyFilter(SegmentedNationalID ID) {
        if (expectedGender.isEmpty()) {
            return true;
        }

        Optional<NationalIDSegmentBase> optionalSegment = ID.getSegment(NationalIDSegmentType.GENDER);

        if (optionalSegment.isEmpty()) {
            // If You do not have a gender segment, You're definetily not male or female
            return false;
        }

        GenderSegment genderSegment = (GenderSegment) optionalSegment.get();
        Optional<Gender> retrievedGender = genderSegment.getGender();

        if (retrievedGender.isEmpty()) {
            return false;
        }

        return retrievedGender.get() == expectedGender.get();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nationalid.verificationapp.Filters.Filterable#ApplyFilter(java.util.List)
     */
    @Override
    public List<SegmentedNationalID> ApplyFilter(List<SegmentedNationalID> IDs) {
        if (expectedGender.isEmpty()) {
            return IDs;
        }

        IDs.removeIf(ID -> !ApplyFilter(ID));
        return IDs;
    }

    /**
     * tries to conduct a string interpretation of a gender
     * 
     * @param genderValue to be parsed
     * @return Gender if recognized, otherwise empty
     */
    private static Optional<Gender> StringToGender(String genderValue) {
        if (genderValue == null)
            return Optional.empty();
        // TODO: this method should normally be provided by the enum

        return switch (genderValue.toLowerCase()) {
            case "male" -> Optional.of(Gender.MALE);
            case "female" -> Optional.of(Gender.FEMALE);
            default -> Optional.empty();
        };
    }
}
