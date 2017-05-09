package github.jdrost1818;

import github.jdrost1818.service.ConfigurationService;

/**
 * Main entry point
 *
 * The way to interact with this application is through command line.
 *
 * plaster MODE MODE_SCOPE CLASS_NAME [FIELD:TYPE...]
 * plaster g scaffold Something name:string
 */
public class Plaster {

    public static void main(String[] args) {
        ConfigurationService.load();
        System.out.println("Hello World");
    }

}
