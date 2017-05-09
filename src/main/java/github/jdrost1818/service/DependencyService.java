package github.jdrost1818.service;

import com.google.common.collect.Lists;
import github.jdrost1818.domain.JavaDependency;

import java.util.List;

public class DependencyService {

    DependencyService() {
        // Do nothing
    }

    public List<JavaDependency> fetchDependencies(String className) {
        return Lists.newArrayList();
    }

    /**
     * Converts a system path to a java path.
     *
     * Example:
     *  project/src/main/java/com/example/Something.java -> com.example.Something.java
     *
     * @param systemPath
     *          path on system
     * @return path that Java can use to find resource
     */
    public String convertSystemPathToJavaPath(String systemPath) {
        return "";
    }

}
