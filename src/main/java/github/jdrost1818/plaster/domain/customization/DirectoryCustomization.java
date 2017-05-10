package github.jdrost1818.plaster.domain.customization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class DirectoryCustomization {

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String base;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String model;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String repository;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String controller;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String service;

}
