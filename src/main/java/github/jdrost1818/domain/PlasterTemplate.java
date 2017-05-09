package github.jdrost1818.domain;

/**
 * Classes that contribute to generating the content in a file must implement this class.
 */
public interface PlasterTemplate {

    /**
     * Defines how an object should be represented in a file
     *
     * @return the string version of itself as represented in a Java file
     */
    String getTemplate();

}
