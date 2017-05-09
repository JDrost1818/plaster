package github.jdrost1818.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchService {

    private final ConfigurationService configurationService = ServiceProvider.getConfigurationService();

    SearchService() {
        // Do nothing
    }

    /**
     * Finds the system path for classes with the given name, ignoring case
     *
     * @param className
     *          class name to search for
     * @return the java paths for all found class names
     */
    public List<String> findClassesWithName(String className) {
        return findFilesWithName(new File(""), className).stream()
                .map(File::getPath)
                .collect(Collectors.toList());
    }

    private List<File> findFilesWithName(File root, String searchName) {
        List<File> foundFiles = new ArrayList<>();

        File[] files = root.listFiles();
        if (files == null) {
            return foundFiles;
        }

        for (File file : files) {
            if (file.isFile() && file.getName().equalsIgnoreCase(searchName)) {
                foundFiles.add(file);
            } else if (file.isDirectory()) {
                foundFiles.addAll(findFilesWithName(file, searchName));
            }
        }

        return foundFiles;
    }

}
