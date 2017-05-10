package github.jdrost1818.plaster.domain.customization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class LombokCustomization {

    @JsonIgnoreProperties(ignoreUnknown = true)
    private Boolean enable;

}
