package github.jdrost1818.plaster.service;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.StoredJavaType;
import github.jdrost1818.plaster.domain.JavaDependency;
import github.jdrost1818.plaster.exception.EnumSearchException;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.util.PathUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class DependencyService {

    @Setter
    private ConfigurationService configurationService = ServiceProvider.getConfigurationService();

    @Setter
    private SearchService searchService = ServiceProvider.getSearchService();

    public List<JavaDependency> fetchDependencies(String className) {
        if (StringUtils.isEmpty(className)) {
            return Lists.newArrayList();
        }

        // Map<String, List<String>> -> ["Map", "String", "List", "String"]
        String[] individualClasses = className
                .replaceAll("[<>,]", " ")
                .replaceAll("[ +]", " ")
                .trim()
                .split(" ");

        return Arrays.stream(individualClasses)
                .distinct()
                .filter(StringUtils::isNotBlank)
                .map(this::fetchDependency)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Converts a system path to a java path.
     *
     * Example:
     *  project/src/main/java/com/example/Something.java -> com.example.Something
     *
     * @param systemPath
     *          path on system
     * @return path that Java can use to find resource
     */
    public String convertSystemPathToJavaPath(String systemPath) {
        if (StringUtils.isEmpty(systemPath)) {
            return systemPath;
        }

        // The following code takes a full system path
        //      ie "project/module/src/main/java/com/example/Something.java"
        // and converts it into the portion needed for an import
        //      ie "com/example/Something.java
        // also converts all paths to unix style
        String unixSystemPath = FilenameUtils.separatorsToUnix(systemPath);
        String basePath = this.configurationService.get(Setting.BASE_PATH);
        String[] parts = unixSystemPath.split(basePath);
        if (parts.length == 1) {
            throw new PlasterException(
                    "Could not convert path ['" + systemPath + "'] into Java path. Does not contain project root");
        }

        return FilenameUtils.removeExtension(PathUtil.normalize(parts[1], "."));
    }

    private JavaDependency fetchDependency(String className) {
        StoredJavaType storedJavaType = null;
        try {
            storedJavaType = StoredJavaType.getStoredJavaType(className);
        } catch (EnumSearchException e) {
            // This really isn't an error in this case. The type
            // isn't guaranteed to be a stored type here
        }

        // We are making some safe assumptions here:
        //      (1) -   We can use primitives regardless here,
        //              since we are getting the dependencies, there are no
        //              objects related to primitives that need to be imported
        //      (2) -   That if a stored java type has dependencies, there is only
        //              ever going to be one. Stored dependencies are only ever just
        //              one class and not typed eg "List" or "Integer" or "Map", these
        //              can therefore never have more than one dependency
        if (nonNull(storedJavaType)) {
            List<JavaDependency> dependencies = storedJavaType.getType(true).getDependencies();
            return CollectionUtils.isNotEmpty(dependencies) ? dependencies.get(0) : null;
        }
        return this.fetchCustomDependency(className);
    }

    private JavaDependency fetchCustomDependency(String className) {
        List<String> matchingClassPaths = this.searchService.findClassesWithName(className);

        if (matchingClassPaths.size() > 1) {
            String possibleChoices = StringUtils.join(matchingClassPaths, ",\n\t");
            throw new PlasterException("Could not decide which type to import. Options: " + possibleChoices);
        } else if (matchingClassPaths.isEmpty()) {
            throw new PlasterException("Could not find custom type: " + className);
        }

        return new JavaDependency(this.convertSystemPathToJavaPath(matchingClassPaths.get(0)));
    }

}
