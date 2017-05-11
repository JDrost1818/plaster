package github.jdrost1818.plaster;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.data.Arg;
import github.jdrost1818.plaster.data.Mode;
import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.domain.Field;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.FieldService;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.util.ArgParseUtil;
import lombok.Setter;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Main entry point
 *
 * The way to interact with this application is through command line.
 *
 * plaster MODE MODE_SCOPE CLASS_NAME [FIELD:TYPE...]
 * plaster g scaffold Something name:string
 */
public class Plaster {

    @Setter
    private static ConfigurationService configurationService = ServiceProvider.getConfigurationService();

    @Setter
    private static FieldService fieldService = ServiceProvider.getFieldService();

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
        setCommandLineArgs(parsedArgs);

        FileInformation fileInformation = buildFileInformation(parsedArgs);
        Mode mode = Mode.getMode(parsedArgs.getString(Arg.MODE.key));
        String modeScope = parsedArgs.getString(Arg.MODE_SCOPE.key);

        mode.perform(modeScope, fileInformation);
    }

    private static void setCommandLineArgs(Namespace parsedArgs) {
        /*
            Set the key if provided
         */
        String customKey = parsedArgs.getString(Arg.KEY.key);
        String keyString = StringUtils.isNotBlank(customKey) ? customKey : configurationService.get(Setting.KEY);

        configurationService.put(Setting.KEY, keyString);

        /*
            Set the custom directory if provided
         */
        String customSubDir = parsedArgs.get(Arg.DIR.key);
        String safeCustomSubDir = StringUtils.isBlank(customSubDir) ? "" : "/" + customSubDir;
        List<Setting> directories = Lists.newArrayList(
                Setting.REL_MODEL_PACKAGE, Setting.REL_CONTROLLER_PACKAGE, Setting.REL_SERVICE_PACKAGE, Setting.REL_REPOSITORY_PACKAGE);

        for (Setting dir : directories) {
            String currentDir = configurationService.get(dir);
            String customDir = FilenameUtils.concat(currentDir, safeCustomSubDir);

            configurationService.put(dir, customDir);
        }
    }

    private static FileInformation buildFileInformation(Namespace parsedArgs) {
        String className = parsedArgs.getString(Arg.CLASS_NAME.key);
        Field key = fieldService.convertToField(configurationService.get(Setting.KEY));
        List<Field> fields = fieldService.convertToFields(parsedArgs.getList(Arg.FIELD.key));

        return new FileInformation(className, key, fields);
    }

}
