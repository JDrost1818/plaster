package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.data.StoredJavaType;
import github.jdrost1818.plaster.domain.Dependency;
import github.jdrost1818.plaster.exception.EnumSearchException;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.util.PathUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class DependencyService {

    @Setter
    private ConfigurationService configurationService = ServiceProvider.getConfigurationService();

    @Setter
    private SearchService searchService = ServiceProvider.getSearchService();


    public Dependency fetchDependency(String className) {
        if (StringUtils.isBlank(className)) {
            return null;
        }

        StoredJavaType storedJavaType = null;
        try {
            storedJavaType = StoredJavaType.getStoredJavaType(className);
        } catch (EnumSearchException e) {
            // This really isn't an error in this case. The type
            // isn't guaranteed to be a stored type here
        }

        // We are making a safe assumptions here:
        //      We can use primitives regardless of configuration here.
        //      Since we are getting the dependencies, there are no
        //      objects related to primitives that need to be imported
        return nonNull(storedJavaType)
                ? storedJavaType.getType(true).getDependency() : this.fetchCustomDependency(className);
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

    private Dependency fetchCustomDependency(String className) {
        List<String> matchingClassPaths = this.searchService.findClassesWithName(className);

        if (matchingClassPaths.size() > 1) {
            String possibleChoices = StringUtils.join(matchingClassPaths, ",\n\t");
            throw new PlasterException("Could not decide which type to import. Options: " + possibleChoices);
        } else if (matchingClassPaths.isEmpty()) {
            throw new PlasterException("Could not find custom type: " + className);
        }

        return new Dependency(this.convertSystemPathToJavaPath(matchingClassPaths.get(0)));
    }

}
