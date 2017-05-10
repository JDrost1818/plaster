package github.jdrost1818.plaster.service;

import github.jdrost1818.plaster.data.Setting;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationService {

    private Map<Setting, String> configMap = new HashMap<>();

    ConfigurationService() {
        load();
    }

    public String get(Setting setting) {
        return "";
    }

    public boolean getBoolean(Setting setting) {
        return Boolean.valueOf(this.configMap.get(setting));
    }

    public void load() {
        this.loadFromPom();
        this.loadFromSettingsFile();
    }

    /**
     * This loads all that information we can gather from the pom.xml file
     * into our settings configuration
     *
     *  IS_LOMBOK_ENABLED   = true if found in dependencies
     *  MAVEN_GROUP_ID      = found in pom under the
     *
     */
    private void loadFromPom() {

    }

    private void loadFromSettingsFile() {

    }

}
