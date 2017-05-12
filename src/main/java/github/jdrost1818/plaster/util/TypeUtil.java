package github.jdrost1818.plaster.util;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TypeUtil {

    private static final String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";

    /**
     * Converts potentially weirdly-formed, yet valid type declarations into something
     * standard.
     *
     * Example:
     *
     *      "map<  string,Integer>" -> "Map<String, Integer>"
     *
     * @param typeString
     *          string to format
     * @return the formatted string
     */
    public static String normalizeTypeString(String typeString) {
        if (StringUtils.isBlank(typeString)) {
            return "";
        }

        String noSpacesString = typeString.replaceAll(" ", "");

        return Arrays.stream(noSpacesString.split(String.format(WITH_DELIMITER, "<")))
                .map(s -> s.split(String.format(WITH_DELIMITER, ",")))
                .flatMap(Arrays::stream)
                .map(s -> s.split(String.format(WITH_DELIMITER, ">")))
                .flatMap(Arrays::stream)
                .map(TypeUtil::normalizeSingleType)
                .collect(Collectors.joining()).replaceAll(",", ", ");
    }

    /**
     * This transforms the text given into a variable name that follows standard Java practices
     *
     * Example:
     *
     *      someVar -> someVar
     *      some_var -> someVar
     *      SomeVar -> someVar
     *      Some_Var -> someVar
     *
     * @param variableName
     *          name to change
     * @return java variable name
     */
    public static String normalizeVariableName(String variableName) {
        if (StringUtils.isBlank(variableName)) {
            return "";
        }

        String varName = Arrays.stream(variableName.split("[_ ]"))
                .map(StringUtils::capitalize)
                .collect(Collectors.joining(""));

        return StringUtils.uncapitalize(varName).replaceAll(" ", "");
    }

    /**
     * Takes in a type string and returns the types found withing
     *
     * Example:
     *
     *  Map<String, Integer> -> [Map, String, Integer]
     *
     * @param typeString
     *          string from which to extract type strings
     * @return the list of types found within the given string
     */
    public static List<String> splitToIndividualTypes(String typeString) {
        if (StringUtils.isBlank(typeString)) {
            return Lists.newArrayList();
        }

        // Map<String, List<Something>> -> Map String List Something
        return Arrays.asList(typeString
                .replaceAll("[<>,]", " ")
                .replaceAll(" +", " ")
                .trim()
                .split(" "));
    }

    private static String normalizeSingleType(String type) {
        if (StringUtils.isBlank(type)) {
            return "";
        } else if (type.matches("[<,>]")) {
            return type;
        }
        return StringUtils.capitalize(normalizeVariableName(type));
    }

}
