package github.jdrost1818.service;

import com.google.common.collect.Lists;
import github.jdrost1818.data.Regex;
import github.jdrost1818.data.Setting;
import github.jdrost1818.data.StoredJavaType;
import github.jdrost1818.domain.JavaType;
import github.jdrost1818.exception.EnumSearchException;
import github.jdrost1818.exception.PlasterException;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

public class TypeService {

    private static final Pattern POP_PATTERN = Pattern.compile("[>,]");

    @Setter
    private ConfigurationService configurationService = ServiceProvider.getConfigurationService();

    @Setter
    private SearchService searchService = ServiceProvider.getSearchService();

    TypeService() {
        // Do nothing
    }

    public boolean validateType(String typeString) {
        String trimmedString = typeString.replace(" ", "");

        return typeString.contains("<")
                ? this.validateComplexType(trimmedString, new Stack<>())
                : this.validateString(trimmedString, true);
    }

    /**
     * Transforms a string into an object which describes the type and
     * its dependencies if it can find a matching class.
     *
     * @param typeString
     * @return
     */
    public JavaType convertToType(String typeString) {
        StoredJavaType storedJavaType = null;
        try {
            storedJavaType = StoredJavaType.getStoredJavaType(typeString);
        } catch (EnumSearchException e) {
            // This really isn't an error in this case. The type
            // isn't guaranteed to be a stored type here
        }

        boolean shouldUsePrimitive = this.configurationService.getBoolean(Setting.SHOULD_USE_PRIMITIVES);
        return nonNull(storedJavaType) ? storedJavaType.getType(shouldUsePrimitive) : fetchCustomType(typeString);
    }

    private JavaType fetchCustomType(String typeString) {
        List<String> matchingClasses = this.searchService.findClass(typeString);

        if (matchingClasses.size() > 1) {
            String possibleChoices = StringUtils.join(matchingClasses, ",\n\t");
            throw new PlasterException("Could not decide which type to use. Options: " + possibleChoices);
        }

        return matchingClasses.isEmpty()
                ? null : new JavaType(matchingClasses.get(0), this.fetchDependencies(matchingClasses.get(0)));
    }

    private List<String> fetchDependencies(String className) {
        return Lists.newArrayList();
    }

    private boolean validateComplexType(String typeString, Stack<String> stack) {
        if (typeString.isEmpty()) {
            return stack.size() == 1 && validateString(stack.pop(), false);
        } else if (typeString.isEmpty()
                || POP_PATTERN.matcher(typeString.substring(0, 1)).matches()) {
            String newTypeString = typeString.substring(1);
            return validateString(stack.pop(), false)
                    && validateComplexType(newTypeString, stack);
        } else {
            String strToAddToStack;
            int leftIndex = !typeString.contains("<") ? Integer.MAX_VALUE : typeString.indexOf("<");
            int rightIndex = !typeString.contains(">") ? Integer.MAX_VALUE : typeString.indexOf(">");
            int commaIndex = !typeString.contains(",") ? Integer.MAX_VALUE : typeString.indexOf(",");

            if (leftIndex < commaIndex && leftIndex < rightIndex) {
                strToAddToStack = typeString.substring(0, leftIndex);
                typeString = typeString.substring(leftIndex + 1);
            } else if (rightIndex < commaIndex) {
                strToAddToStack = typeString.substring(0, rightIndex);
                typeString = typeString.substring(rightIndex);
            } else if (commaIndex != Integer.MAX_VALUE){
                strToAddToStack = typeString.substring(0, commaIndex);
                typeString = typeString.substring(commaIndex);
            } else {
                strToAddToStack = typeString;
                typeString = stack.size() > 0 ? ">" : "";
            }

            stack.add(strToAddToStack);
            return validateComplexType(typeString, stack);
        }
    }

    private boolean validateString(String str, boolean allowForPrimitives) {
        return StringUtils.isNotBlank(str)
                && allowForPrimitives
                    ? Regex.JAVA_CLASS_NAME_WITH_PRIMITIVES.matcher(str).matches()
                    : Regex.JAVA_CLASS_NAME.matcher(str).matches();
    }
}
