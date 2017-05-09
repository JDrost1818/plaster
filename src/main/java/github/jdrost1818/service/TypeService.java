package github.jdrost1818.service;

import com.google.common.collect.Lists;
import github.jdrost1818.data.Setting;
import github.jdrost1818.data.StoredJavaType;
import github.jdrost1818.domain.JavaType;
import github.jdrost1818.exception.EnumSearchException;
import github.jdrost1818.exception.PlasterException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
public class TypeService {

    @Setter
    private ConfigurationService configurationService = ServiceProvider.getConfigurationService();

    @Setter
    private SearchService searchService = ServiceProvider.getSearchService();

    TypeService() {
        // Do nothing
    }

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

        int numClassesFound = CollectionUtils.size(matchingClasses);

        if (numClassesFound > 1) {
            String possibleChoices = StringUtils.join(matchingClasses, ",\n");
            throw new PlasterException("Could not decide which type to use. Options: " + possibleChoices);
        }

        return matchingClasses.isEmpty()
                ? null : new JavaType(matchingClasses.get(0), this.fetchDependencies(matchingClasses.get(0)));
    }

    private List<String> fetchDependencies(String className) {
        return Lists.newArrayList();
    }

}
