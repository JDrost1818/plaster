package github.jdrost1818.plaster.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class TypeUtil {

    /**
     * Converts potentially weirdly-formed, yet valid type declarations into something
     * standard.
     *
     * Example:
     *
     *      "Map<  String,Integer>" -> "Map<String, Integer>"
     *
     * @param typeString
     *          string to format
     * @return the formatted string
     */
    public static String normalizeTypeString(String typeString) {
        return typeString
                .replaceAll(" ", "")
                .replaceAll(",", ", ");
    }

    public static List<String> splitToIndividualTypes(String typeString) {
        // Map<String, List<Something>> -> Map String List Something
        return Arrays.asList(typeString
                .replaceAll("[<>,]", " ")
                .replaceAll(" +", " ")
                .trim()
                .split(" "));
    }

}