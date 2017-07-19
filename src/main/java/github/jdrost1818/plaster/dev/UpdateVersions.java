package github.jdrost1818.plaster.dev;

import com.github.zafarkhaja.semver.Version;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateVersions {

    private enum UpdateType {
        MAJOR(Version::incrementMajorVersion),
        MINOR(Version::incrementMinorVersion),
        PATCH(Version::incrementPatchVersion);

        VersionUpdater updater;
        UpdateType(VersionUpdater versionUpdater) {
            this.updater = versionUpdater;
        }
    }

    private static final String VERSION_REGEX = "\\d\\.\\d\\.\\d";

    public static void main(String[] args) throws IOException {
        UpdateType updateType;
        try {
            updateType = UpdateType.valueOf(StringUtils.upperCase(args[0]));
        } catch (Exception e) {
            System.out.println("Must provide an update type: ['major', 'minor', 'patch']");
            return;
        }

        updatePom(updateType);
        updateReadMe(updateType);
    }

    private static void updatePom(UpdateType updateType) throws IOException {
        String before = "<version>";
        String after = "</version><!--PROJECT_ID-->";
        String filename = "pom.xml";

        updateSemVer(filename, before, after, updateType);
    }

    private static void updateReadMe(UpdateType updateType) throws IOException {
        String before = "\\[!\\[Version for JDrost1818/plaster]\\(https://img\\.shields\\.io/SemVer/";
        String after = "\\.png\\)]\\(\\)";
        String filename = "README.md";

        updateSemVer(filename, before, after, updateType);
    }

    @SuppressWarnings("unchecked")
    private static void updateSemVer(String filename, String before, String after, UpdateType updateType) throws IOException {
        File file = Paths.get(System.getProperty("user.dir"), filename).toFile();

        List<String> convertedLines = (List<String>) FileUtils.readLines(file).stream()
                .map(line -> {
                    String lineStr = line.toString();
                    if (lineStr.trim().matches(before + VERSION_REGEX + after)) {
                        Version version = Version.valueOf(
                                lineStr.trim()
                                        .replaceAll(before, "")
                                        .replaceAll(after, ""));

                        Version newVersion = updateType.updater.updateVersion(version);

                        return lineStr.replaceAll(VERSION_REGEX, newVersion.getNormalVersion());
                    }
                    return line;
                }).collect(Collectors.toList());

        FileUtils.writeLines(file, convertedLines);
    }

    private interface VersionUpdater {

        public Version updateVersion(Version versionToUpdate);

    }

}
