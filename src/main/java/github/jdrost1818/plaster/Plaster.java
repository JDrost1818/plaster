package github.jdrost1818.plaster;

import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.util.ArgParseUtil;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

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
        ArgumentParser parser = ArgParseUtil.getArgParser();
        Namespace parsedArgs = null;
        try {
            parsedArgs = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        ArgParseUtil.validateParsedArgs(parsedArgs);

        FileInformation fileInformation = new FileInformation();


        System.out.println(parsedArgs.toString());
    }

}
