package github.jdrost1818.plaster.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.domain.customization.DirectoryCustomization;
import github.jdrost1818.plaster.domain.customization.LombokCustomization;
import github.jdrost1818.plaster.domain.customization.PlasterCustomization;
import github.jdrost1818.plaster.domain.customization.PropertyCustomization;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.util.PathUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ConfigurationService {

    private Map<Setting, String> configMap = new HashMap<>();

    private String applicationRoot;

    /**
     * This should not be used anywhere but when we are parsing the command
     * line arguments in {@link github.jdrost1818.plaster.Plaster}
     *
     * @param setting
     *          setting to change
     * @param value
     *
     */
    public void put(Setting setting, String value) {
        this.configMap.put(setting, value);
    }

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
     *  PROJECT_PATH            =
     *  SUB_DIR_PATH            = *
     *  REL_PATH                = **
     *  MAVEN_GROUP_ID          = **
     *  REL_MODEL_PACKAGE       = model
     *  REL_REPOSITORY_PACKAGE  = repository
     *  REL_SERVICE_PACKAGE     = service
     *  REL_CONTROLLER_PACKAGE  = controller
     *  SHOULD_USE_PRIMITIVES   = false
     *
     * *  = This can only be configured at invocation as a command-line argumnet
     *
     * ** = These are not set, but will be set during inspection of the pom. If the
     *      pom cannot be read, the generation will fail, therefore, they do not
     *      need to be set in here.
     */
    private void loadDefaultSettings(String applicationRoot) {
        this.applicationRoot = applicationRoot;

        this.configMap.put(Setting.KEY, "id:int");
        this.configMap.put(Setting.IS_LOMBOK_ENABLED, "false");
        this.configMap.put(Setting.PROJECT_PATH, "");
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
     *  BASE_PATH           = "src/main/java" in most (nearly all) cases
     *  APP_PATH            = if group id == "com.example.app" app_path = "com/example/app/
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
            this.configMap.put(Setting.APP_PATH, PathUtil.
                    normalize(mavenGroupId.replace(".", "/"), "/"));

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
                        this.configMap.put(Setting.IS_LOMBOK_ENABLED, "true");
                    }

                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new PlasterException("Could not properly parse pom.xml");
        }

    }

    private void loadFromSettingsFile() {
        File plasterYaml = new File(FilenameUtils.concat(this.applicationRoot, "plaster.yml"));
        if (!plasterYaml.exists()) {
            return;
        }

        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        try {
            PlasterCustomization customization = yamlMapper.readValue(plasterYaml, PlasterCustomization.class);

            loadProperties(customization.getProperty());
            loadDirectories(customization.getDirectory());
            loadLombok(customization.getLombok());
        } catch (IOException e) {
            // This just means the file is empty and isn't really an error
            // Todo: make this more reliable
            if (!e.getMessage().contains("No content to map due to end-of-input")) {
                throw new PlasterException("Plaster.yml file is malformed", e);
            }
        }

    }

    /**
     * Loads the customizations from the property tag
     *
     * Supported configurations:
     *
     *      allowPrimitives:boolean ->  should int be used in place of Integer, etc
     *      key:string ->               the name:type pair to be used instead of id:int
     *
     * @param customization
     *          object containing the customizations allowed in the property tag
     */
    private void loadProperties(PropertyCustomization customization) {
        if (isNull(customization)) {
            return;
        }

        // Set a custom key
        if (StringUtils.isNotBlank(customization.getKey())) {
            this.configMap.put(Setting.KEY , customization.getKey());
        }

        // Set custom primitive config
        if (nonNull(customization.getEnablePrimitives())) {
            this.configMap.put(Setting.SHOULD_USE_PRIMITIVES, customization.getEnablePrimitives().toString());
        }

    }

    /**
     * Loads the customizations from the directory tag
     *
     * Supported configurations:
     *
     *      base:string ->              what to use instead of src/main/java
     *      model:string ->             defines base model package location
     *      repository:string ->        defines base repository package location
     *      controller:string ->        defines base controller package location
     *      service:string ->           defines base service package location
     *
     * @param customization
     *          object containing the customizations from the directory tag
     */
    private void loadDirectories(DirectoryCustomization customization) {
        if (isNull(customization)) {
            return;
        }

        if (nonNull(customization.getBase())) {
            String mavenGroupId = this.get(Setting.MAVEN_GROUP_ID);

            this.configMap.put(Setting.BASE_PATH, customization.getBase());
            this.configMap.put(Setting.APP_PATH, PathUtil.
                    normalize(mavenGroupId.replace(".", "/"), "/"));
        }

        Map<Setting, String> dirMap = new HashMap<>();
        dirMap.put(Setting.REL_MODEL_PACKAGE, customization.getModel());
        dirMap.put(Setting.REL_REPOSITORY_PACKAGE, customization.getRepository());
        dirMap.put(Setting.REL_CONTROLLER_PACKAGE, customization.getController());
        dirMap.put(Setting.REL_SERVICE_PACKAGE, customization.getService());

        // Safely put in all the paths into the settings
        for (Map.Entry<Setting, String> curEntry : dirMap.entrySet()) {
            if (StringUtils.isNotBlank(curEntry.getValue())) {
                String unixPath = FilenameUtils.separatorsToUnix(curEntry.getValue());
                this.configMap.put(curEntry.getKey(), unixPath);
            }
        }

    }

    /**
     * Loads the customizations from the lombok tag
     *
     * Supported configurations:
     *
     *      enable:boolean ->           whether to use lombok annotations or getters and setters
     *
     * @param customization
     *          object containing the customizations from the lombok tag
     */
    private void loadLombok(LombokCustomization customization) {
        if (isNull(customization)) {
            return;
        }

        if (nonNull(customization.getEnable())) {
            this.configMap.put(Setting.IS_LOMBOK_ENABLED, customization.getEnable().toString());
        }
    }

}
