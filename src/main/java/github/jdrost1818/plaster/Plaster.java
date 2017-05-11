package github.jdrost1818.plaster;

import github.jdrost1818.plaster.data.ModeScope;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.Arrays;

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
        ArgumentParser parser = getArgParser();
        Namespace parsedArgs = null;
        try {
            parsedArgs = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        System.out.println(parsedArgs.toString());
    }

    /**
     * Fetches the parser to be used to parse the command line arguments.
     *
     *      -d/--dir -  defines where to generate/look for a class. So if we are generating a
     *                  model, and provide "custom/dir", instead of generating in SETTING.REL_MODEL_PATH,
     *                  it will generate in SETTING.REL_MODEL_PATH/custom/dir. This is only active during
     *                  this instance of the run and is not persisted
     *
     *      -k/--key -  overwrites the way to generate the id of a model.
     *                  This overwrites the SETTING.KEY property for the current run
     *
     *      pos1     -  mode: Are we generating or removing? {@link github.jdrost1818.plaster.data.Mode}
     *
     *      pos2     -  modeScope: How much are we generating or removing? {@link ModeScope}
     *
     *      pos3     -  className: name of class we are focused on during this run
     *
     *      pos4     -  fields: list of name:type pairs we will be using to generate or remove.
     *
     * @return the argument parser
     */
    private static ArgumentParser getArgParser() {
        ArgumentParser parser = ArgumentParsers.newArgumentParser("Plaster", true);

        /*
            Defines the mode argument
         */
        parser
                .addArgument("mode")
                .choices("g", "gen", "generate", "d", "del", "delete")
                .help("Whether to generate or remove code");

        /*
            Defines the scope of the mode argument
         */
        String[] modeScopes = Arrays.stream(ModeScope.values())
                .map(ModeScope::name)
                .map(String::toLowerCase)
                .toArray(String[]::new);
        parser
                .addArgument("modeScope")
                .choices(modeScopes)
                .help("Determines what to generate/delete");

        /*
            Defines the class name argument
         */
        parser
                .addArgument("className")
                .help("Name of class");

        /*
            Defines the field argument(s)
         */
        parser
                .addArgument("field")
                .nargs("*")
                .help("name:type pair(s) defining fields");

        /*
            Define the custom sub-directory option
         */
        parser
                .addArgument("-d", "--dir")
                .nargs("?")
                .help("sub directory in which to put/find generated classes");

        /*
            Define the custom id name:type pair option
         */
        parser
                .addArgument("-k", "--key")
                .nargs("?")
                .help("name:type pair indicating the name/type of the key for the class");

        return parser;
    }

}
