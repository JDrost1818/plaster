package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.util.PathUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SearchService {

    @Setter
    private ConfigurationService configurationService = ServiceProvider.getConfigurationService();

    /**
     * Finds the system path for classes with the given name, ignoring case
     *
     * @param className
     *          class name to search for
     * @return the java paths for all found class names
     */
    public List<String> findClassesWithName(String className) {
        String basePath = this.configurationService.get(Setting.PROJECT_PATH);

        return this.findFilesWithName(new File(basePath), className + ".java").stream()
                .map(File::getPath)
                .map(p -> p.split(basePath)[1])
                .map(p -> PathUtil.normalize(p, "/"))
                .collect(Collectors.toList());
    }

    /**
     * Finds all file recursively from the root file given with the given search name.
     *
     * @param root
     *          place to start search
     * @param searchName
     *          name of file to look for
     * @return all files with the given name
     */
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
