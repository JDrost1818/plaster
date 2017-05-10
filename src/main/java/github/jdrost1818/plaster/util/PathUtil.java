package github.jdrost1818.plaster.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

@UtilityClass
public class PathUtil {

    /**
     * Creates a path with the given separator as the separator and removes any
     * leading/trailing separators.
     *
     * Example
     *      "/src/main/Something.java", "." ->      src.main.Something.java
     *      "/src/main/", "/" ->                    src/main
     *
     * @param path
     *          string to normalize
     * @param separator
     *          separator to use
     * @return the normalized path
     */
    public static String normalize(String path, String separator) {
        return FilenameUtils.separatorsToUnix(path)
                .replaceAll("[/]", " ")
                .replaceAll(" +", " ")
                .trim()
                .replaceAll(" ", separator);
    }

}
