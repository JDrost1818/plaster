package github.jdrost1818.plaster.dev;

import com.github.zafarkhaja.semver.Version;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateVersions {

    public static void main(String[] args) throws IOException {
        updatePom();
        updateReadMe();
    }

    private static void updatePom() throws IOException {
        String before = "<version>";
        String after = "</version><!--PROJECT_ID-->";
        String filename = "pom.xml";

        updateSemVer(filename, before, after);
    }

    private static void updateReadMe() throws IOException {
        String before = "IDK";
        String after = "IDK";
        String filename = "README.md";
    }

    @SuppressWarnings("unchecked")
    private static void updateSemVer(String filename, String before, String after) throws IOException {
        File file = Paths.get(System.getProperty("user.dir"), filename).toFile();


        List<String> convertedLines = (List<String>) FileUtils.readLines(file).stream()
                .map(Object::toString)
                .map(line -> {
                    String lineStr = line.toString();
                    if (lineStr.trim().matches(before + "\\d\\.\\d\\.\\d" + after)) {
                        Version version = Version.valueOf(lineStr.trim().replace(before, "").replace(after, ""));
                        Version newVersion = version.incrementMinorVersion();

                        return lineStr.replaceAll("\\d\\.\\d\\.\\d", newVersion.getNormalVersion());
                    }
                    return line;
                }).collect(Collectors.toList());

        FileUtils.writeLines(file, convertedLines);
    }

}
