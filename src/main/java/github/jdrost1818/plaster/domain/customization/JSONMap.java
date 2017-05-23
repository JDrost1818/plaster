package github.jdrost1818.plaster.domain.customization;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * A construct which can be used to represent the parsed result of a
 * JSON String.
 */
public class JSONMap extends LinkedHashMap {

    /**
     * Gets the string found at the end of the composite path. If the
     * path cannot be fully traced, null will be returned.
     *
     * The composite key must utilize periods to separate the different keys.
     * Example:
     *
     *  subObject.key.value
     *
     * @param compositeKey
     *          the composite key which defines the path to find the desired value
     * @return the value found. If not found, null.
     */
    public String getEndValue(String compositeKey) {
        if (StringUtils.isEmpty(compositeKey)) {
            return null;
        }

        List<String> keys = Arrays.asList(compositeKey.split("\\."));
        String finalKey = keys.get(keys.size() - 1);

        LinkedHashMap mapToIterate = this;
        for (String key : keys.subList(0, keys.size() - 1)) {
            Object foundObj = mapToIterate.get(key);
            if (foundObj instanceof LinkedHashMap) {
                mapToIterate = (LinkedHashMap) foundObj;
            } else {
                return null;
            }
        }

        Object lastObject = mapToIterate.get(finalKey);
        if (lastObject instanceof LinkedHashMap) {
            throw new IllegalArgumentException("This is wrong");
        }

        return nonNull(lastObject) ? lastObject.toString() : null;
    }

}
