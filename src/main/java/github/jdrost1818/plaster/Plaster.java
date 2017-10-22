package github.jdrost1818.plaster;

import github.jdrost1818.plaster.data.Arg;
import github.jdrost1818.plaster.data.Mode;
import github.jdrost1818.plaster.data.ModeScope;
import github.jdrost1818.plaster.data.Setting;
import github.jdrost1818.plaster.domain.Field;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.DeveloperException;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.service.ConfigurationService;
import github.jdrost1818.plaster.service.FieldService;
import github.jdrost1818.plaster.service.ServiceProvider;
import github.jdrost1818.plaster.service.task.TaskService;
import github.jdrost1818.plaster.util.ArgParseUtil;
import lombok.experimental.UtilityClass;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
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
@UtilityClass
public class Plaster {

    private static ConfigurationService configurationService = ServiceProvider.getConfigurationService();

    private static FieldService fieldService = ServiceProvider.getFieldService();

    private static TaskService taskService = ServiceProvider.getTaskService();

    public static void main(String[] args) {
        ArgumentParser parser = ArgParseUtil.getArgParser();
        Namespace parsedArgs;
        try {
            parsedArgs = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            return;
        }

        try {
            ArgParseUtil.validateParsedArgs(parsedArgs);
            setCommandLineArgs(parsedArgs);

            FileInformation fileInformation = buildFileInformation(parsedArgs);
            Mode mode = Mode.getMode(parsedArgs.getString(Arg.MODE.key));
            String modeScope = parsedArgs.getString(Arg.MODE_SCOPE.key);

            taskService.perform(mode, ModeScope.valueOf(StringUtils.upperCase(modeScope)), fileInformation);
        } catch (PlasterException | DeveloperException e) {
            System.out.println(e.getMessage());
        }
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
        configurationService.put(Setting.SUB_DIR_PATH, safeCustomSubDir);
    }

    private static FileInformation buildFileInformation(Namespace parsedArgs) {
        String className = parsedArgs.getString(Arg.CLASS_NAME.key);
        Field key = fieldService.convertToField(configurationService.get(Setting.KEY));
        List<Field> fields = fieldService.convertToFields(parsedArgs.getList(Arg.FIELD.key));

        return new FileInformation(className, key, fields);
    }

}
