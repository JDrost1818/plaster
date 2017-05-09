package github.jdrost1818.data;

import com.google.common.collect.Lists;
import github.jdrost1818.domain.JavaType;
import github.jdrost1818.exception.EnumSearchException;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public enum StoredJavaType {

    INTEGER(
            new JavaType("Integer"),
            new JavaType("int"),
            Lists.newArrayList("int", "integer")),
    LONG(
            new JavaType("Long"),
            new JavaType("long"),
            Lists.newArrayList("long")),
    FLOAT(
            new JavaType("Float"),
            new JavaType("float"),
            Lists.newArrayList("float")),
    DOUBLE(
            new JavaType("Double"),
            new JavaType("double"),
            Lists.newArrayList("double", "dbl")),

    STRING(
            new JavaType("String"),
            Lists.newArrayList("string", "str")),

    DATE(
            new JavaType("Date", Lists.newArrayList("java.util.Date")),
            Lists.newArrayList("date")),
    TIMESTAMP(
            new JavaType("Timestamp", Lists.newArrayList("java.util.Timestamp")),
            Lists.newArrayList("timestamp")),

    LIST(
            new JavaType("List", Lists.newArrayList("java.util.List")),
            Lists.newArrayList("list"),
            1),
    SET(
            new JavaType("Set", Lists.newArrayList("java.util.Set")),
            Lists.newArrayList("set"),
            1),
    MAP(
            new JavaType("Map", Lists.newArrayList("java.util.Map")),
            Lists.newArrayList("map"),
            2);

    private final JavaType type;
    private final JavaType primitiveType;
    private final List<String> searchTerms;
    public final int numTypedArgsRequired;

    StoredJavaType(JavaType type, List<String> searchTerms) {
        this(type, null, searchTerms);
    }

    StoredJavaType(JavaType type, List<String> searchTerms, int numTypedArgsRequired) {
        this(type, null, searchTerms, numTypedArgsRequired);
    }

    StoredJavaType(JavaType type, JavaType primitiveType, List<String> searchTerms) {
        this(type, primitiveType, searchTerms, 0);
    }

    StoredJavaType(JavaType type, JavaType primitiveType, List<String> searchTerms, int numTypedArgsRequired) {
        this.type = type;
        this.primitiveType = primitiveType;
        this.searchTerms = searchTerms;
        this.numTypedArgsRequired = numTypedArgsRequired;
    }

    public static StoredJavaType getStoredJavaType(String searchTerm) throws EnumSearchException {
        if (isNull(searchTerm)) {
            throw new IllegalArgumentException("searchTerm cannot be null");
        }

        String lowerSearch = searchTerm.toLowerCase();
        for (StoredJavaType storedJavaType : StoredJavaType.values()) {
            if (storedJavaType.searchTerms.contains(lowerSearch)) {
                return storedJavaType;
            }
        }

        throw new EnumSearchException("Cannot find java type for: " + searchTerm);
    }

    public JavaType getType(boolean shouldUsePrimitive) {
        return nonNull(this.primitiveType) && shouldUsePrimitive ? this.primitiveType : type;
    }

}
