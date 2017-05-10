package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.Plaster;
import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.util.PathUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ConfigurationService {

    private Map<Setting, String> configMap = new HashMap<>();

    private String applicationRoot;

    public String get(Setting setting) {
        return this.configMap.get(setting);
    }

    public boolean getBoolean(Setting setting) {
        return Boolean.valueOf(this.configMap.get(setting));
    }

    public ConfigurationService load(String applicationRoot) {
        this.loadDefaultSettings(applicationRoot);
        this.loadFromPom();
        this.loadFromSettingsFile();

        return this;
    }

    /**
     * Here we set all the settings to some default values
     * so that we never have an instance where a setting
     * key does not have a corresponding value. The defaults
     * as follows:
     *
     *  KEY                     = id:int
     *  IS_LOMBOK_ENABLED       = false
     *  BASE_PATH               = src/main/java/
     *  SUB_DIR_PATH            =
     *  REL_PATH                = *
     *  MAVEN_GROUP_ID          = *
     *  REL_MODEL_PACKAGE       = model
     *  REL_REPOSITORY_PACKAGE  = repository
     *  REL_SERVICE_PACKAGE     = service
     *  REL_CONTROLLER_PACKAGE  = controller
     *  SHOULD_USE_PRIMITIVES   = false
     *
     *  * = These are not set, but will be set during inspection of the pom. If the
     *      pom cannot be read, the generation will fail, therefore, they do not
     *      need to be set in here.
     */
    private void loadDefaultSettings(String applicationRoot) {
        this.applicationRoot = applicationRoot;

        this.configMap.put(Setting.KEY, "id:int");
        this.configMap.put(Setting.IS_LOMBOK_SUPPORTED, "false");
        this.configMap.put(Setting.BASE_PATH, "src/main/java");
        this.configMap.put(Setting.SUB_DIR_PATH, "");
        this.configMap.put(Setting.REL_MODEL_PACKAGE, "model");
        this.configMap.put(Setting.REL_REPOSITORY_PACKAGE, "repository");
        this.configMap.put(Setting.REL_SERVICE_PACKAGE, "service");
        this.configMap.put(Setting.REL_CONTROLLER_PACKAGE, "controller");
        this.configMap.put(Setting.SHOULD_USE_PRIMITIVES, "false");
    }

    /**
     * This loads all that information we can gather from the pom.xml file
     * into our settings configuration. These are the settings we can fetch
     * by reading information in the pom
     *
     *  IS_LOMBOK_ENABLED   = true if found in dependencies
     *  MAVEN_GROUP_ID      = found in pom under the group-id tag
     *  REL_PATH            = if group id == "com.example.app" rel_path = "src/main/java/com/example/app/
     *
     */
    private void loadFromPom() {
        try {
            File pom = new File(FilenameUtils.concat(this.applicationRoot, "pom.xml"));
            if (!pom.exists()) {
                throw new PlasterException("Cannot find pom.xml, are you sure you are in the root of a project?");
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(pom);

            doc.getDocumentElement().normalize();

            NodeList groupIds = doc.getElementsByTagName("groupId");
            String mavenGroupId = groupIds.item(0).getTextContent();

            this.configMap.put(Setting.MAVEN_GROUP_ID, mavenGroupId);
            this.configMap.put(Setting.REL_PATH, PathUtil.
                    normalize("/src/main/java/" + mavenGroupId.replace(".", "/"), "/"));

            /*
                This part inspects the dependencies to see what we can turn on
             */
            NodeList dependencies = doc.getElementsByTagName("dependency");
            for (int i=0; i < dependencies.getLength(); i++) {
                Element curDependency = (Element) dependencies.item(i);

                // Since we are in a dependency, there should only ever be one
                NodeList curGroupIds = curDependency.getElementsByTagName("groupId");
                if (curGroupIds.getLength() > 0) {

                    // Search for lombok
                    String dependencyId = curGroupIds.item(0).getTextContent();
                    if (dependencyId.contains("org.projectlombok")) {
                        this.configMap.put(Setting.IS_LOMBOK_SUPPORTED, "true");
                    }

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new PlasterException("Could not properly parse pom.xml");
        }

    }

    private void loadFromSettingsFile() {

    }

}
