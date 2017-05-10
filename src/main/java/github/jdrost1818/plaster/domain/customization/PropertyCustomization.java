package github.jdrost1818.plaster.domain.customization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class PropertyCustomization {

    @JsonIgnoreProperties(ignoreUnknown = true)
    private Boolean enablePrimitives;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String key;

}
