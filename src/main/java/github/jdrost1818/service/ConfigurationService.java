package github.jdrost1818.service;

import github.jdrost1818.data.Setting;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationService {

    Map<Setting, String> configMap = new HashMap<>();

    ConfigurationService() {
        load();
    }

    public void load() {

    }

    public String get(Setting setting) {
        return "";
    }

    public boolean getBoolean(Setting setting) {
        return Boolean.valueOf(this.configMap.get(setting));
    }

}
