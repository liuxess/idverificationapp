package nationalid.verificationapp.enums;

/**
 * Shows the sorting order and provides simplistic parsing methods
 */
public enum SortingOrder {
    ASC("asc"), DESC("desc");

    private String value;

    private SortingOrder(String value) {
        this.value = value;
    }

    /**
     * @param value to parse
     * @return SortingOrder based on value
     */
    public static SortingOrder OutOfString(String value) {
        // Ascending by default
        return value.toLowerCase() == "desc" ? DESC : ASC;
    }

    /**
     * @param Ascending whether the order should be Ascending or descending
     * @return the SortingOrder
     */
    public static SortingOrder OutOfBoolean(boolean Ascending) {
        return Ascending ? ASC : DESC;
    }

    /**
     * @return sortingOrder as String
     */
    public String ToString() {
        return value;
    }

    /**
     * @return positive or negative integer to indicate ordering
     */
    public int ToInt() {
        return value == "asc" ? 1 : -1;
    }
}
