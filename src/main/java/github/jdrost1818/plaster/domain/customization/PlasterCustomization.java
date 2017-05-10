package github.jdrost1818.plaster.domain.customization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class PlasterCustomization {

    @JsonIgnoreProperties(ignoreUnknown = true)
    LombokCustomization lombok;

    @JsonIgnoreProperties(ignoreUnknown = true)
    DirectoryCustomization directory;

    @JsonIgnoreProperties(ignoreUnknown = true)
    PropertyCustomization property;

}
