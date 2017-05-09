package github.jdrost1818.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class JavaType {

    private String type;

    private List<String> dependencies = new ArrayList<>();

    public JavaType(String type) {
        this.type = type;
    }

}
