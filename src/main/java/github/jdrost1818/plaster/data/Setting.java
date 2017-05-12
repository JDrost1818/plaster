package github.jdrost1818.plaster.data;

/**
 * Defines all the keys for the settings that
 * can be configured for this application
 */
public enum Setting {

    /**
     * name:type string defining how to generate the id
     *
     * This is determined via inspecting the plaster.yml file and command line arg
     *
     * Example:
     *
     *      id:string
     */
    KEY,

    /**
     * boolean string. If true, will not generate getters and setters, and will rather
     * annotate model class with lombok.
     *
     * This is determined via inspecting the pom and the plaster.yml file
     */
    IS_LOMBOK_ENABLED,

    /**
     * string defining where the src directory is from the root of the repository.
     * Most commonly and empty string.
     *
     * This is not configurable yet.
     */
    PROJECT_PATH,

    /**
     * string defining the path to get to the app's code.
     *
     * This can be configured via plaster.yml file
     *
     * Example:
     *
     *      src/main/java
     */
    BASE_PATH,

    /**
     * string defining the qualified app path. Will be the maven group id in most cases
     *
     * This is determined by inspecting the pom and plaster.yml
     *
     * Example:
     *
     *      com/example/app
     */
    APP_PATH,

    /**
     * string defining a path to append to the generation relative paths for the current generation.
     * Meaning, if provided, generation will not occur at rel_*_package, but rather rel_*_package/sub_dir_path
     *
     * This is determined by inspecting command line args
     *
     * Example:
     *
     *      somewhere/different
     */
    SUB_DIR_PATH,

    /**
     * string defining the maven group id for the project.
     *
     * This is determined by inspecting the pom
     *
     * Example:
     *
     *      com.example.app
     */
    MAVEN_GROUP_ID,

    /**
     * string defining custom package, from the {@link Setting#APP_PATH} to generate models
     *
     * This is determined by inspecting the plaster.yml file
     *
     * Example:
     *
     *      somewhere/different
     */
    REL_MODEL_PACKAGE,

    /**
     * string defining custom package, from the {@link Setting#APP_PATH} to generate repositories
     *
     * This is determined by inspecting the plaster.yml file
     *
     * Example:
     *
     *      somewhere/different
     */
    REL_REPOSITORY_PACKAGE,

    /**
     * string defining custom package, from the {@link Setting#APP_PATH} to generate services
     *
     * This is determined by inspecting the plaster.yml file
     *
     * Example:
     *
     *      somewhere/different
     */
    REL_SERVICE_PACKAGE,

    /**
     * string defining custom package, from the {@link Setting#APP_PATH} to generate controllers
     *
     * This is determined by inspecting the plaster.yml file
     *
     * Example:
     *
     *      somewhere/different
     */
    REL_CONTROLLER_PACKAGE,

    /**
     * boolean string which signifies whether or not to use primitive types when possible.
     * If enabled, Integers will be generated as ints.
     *
     * This is determined by inspecting the plaster.yml file
     */
    SHOULD_USE_PRIMITIVES,

}
