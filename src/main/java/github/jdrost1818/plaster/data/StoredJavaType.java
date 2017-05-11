package github.jdrost1818.plaster.data;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.domain.Dependency;
import github.jdrost1818.plaster.domain.Type;
import github.jdrost1818.plaster.exception.EnumSearchException;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public enum StoredJavaType {

    INTEGER(
            new Type("Integer"),
            new Type("int"),
            Lists.newArrayList("int", "integer")),
    LONG(
            new Type("Long"),
            new Type("long"),
            Lists.newArrayList("long")),
    FLOAT(
            new Type("Float"),
            new Type("float"),
            Lists.newArrayList("float")),
    DOUBLE(
            new Type("Double"),
            new Type("double"),
            Lists.newArrayList("double", "dbl")),

    STRING(
            new Type("String"),
            Lists.newArrayList("string", "str")),

    DATE(
            new Type("Date", new Dependency("java.util.Date")),
            Lists.newArrayList("date")),
    TIMESTAMP(
            new Type("Timestamp", new Dependency("java.util.Timestamp")),
            Lists.newArrayList("timestamp")),

    LIST(
            new Type("List", new Dependency("java.util.List")),
            Lists.newArrayList("list"),
            1),
    SET(
            new Type("Set", new Dependency("java.util.Set")),
            Lists.newArrayList("set"),
            1),
    MAP(
            new Type("Map", new Dependency("java.util.Map")),
            Lists.newArrayList("map"),
            2);

    private final Type type;
    private final Type primitiveType;
    private final List<String> searchTerms;
    public final int numTypedArgsRequired;

    StoredJavaType(Type type, List<String> searchTerms) {
        this(type, null, searchTerms);
    }

    StoredJavaType(Type type, List<String> searchTerms, int numTypedArgsRequired) {
        this(type, null, searchTerms, numTypedArgsRequired);
    }

    StoredJavaType(Type type, Type primitiveType, List<String> searchTerms) {
        this(type, primitiveType, searchTerms, 0);
    }

    StoredJavaType(Type type, Type primitiveType, List<String> searchTerms, int numTypedArgsRequired) {
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

    public Type getType(boolean shouldUsePrimitive) {
        return nonNull(this.primitiveType) && shouldUsePrimitive ? this.primitiveType : this.type;
    }

}
