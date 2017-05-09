package github.jdrost1818.service;

import github.jdrost1818.data.StoredJavaType;
import github.jdrost1818.domain.JavaType;
import github.jdrost1818.exception.EnumSearchException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class TypeService {

    public JavaType convertToType(String typeString) {
        StoredJavaType storedJavaType = null;
        try {
            storedJavaType = StoredJavaType.getStoredJavaType(typeString);
        } catch (EnumSearchException e) {
            e.printStackTrace();
        }


        return null;
    }

}
