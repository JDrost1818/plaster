package github.jdrost1818.plaster.data;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;
import github.jdrost1818.plaster.service.ServiceProvider;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;

import static github.jdrost1818.plaster.data.ModeScope.*;
import static java.util.Objects.isNull;

public enum Mode {

    GENERATE(
            Lists.newArrayList("g", "gen", "generate"),
            Lists.newArrayList(SCAFFOLD, MODEL, REPOSITORY, SERVICE, CONTROLLER),
            (s, f) -> s.modify(ServiceProvider.getGenerateService(), f)),

    MODIFY(
            Lists.newArrayList("m", "mod", "modify"),
            Lists.newArrayList(MODEL),
            (s, f) -> s.modify(ServiceProvider.getEditService(), f));

    private final List<String> searchTerms;
    private final List<ModeScope> scopes;
    private final Perform performFunction;
    Mode(List<String> searchTerms, List<ModeScope> scopes, Perform performFunction) {
        this.searchTerms = searchTerms;
        this.scopes = scopes;
        this.performFunction = performFunction;
    }

    /**
     * Gets the mode for the provided term
     *
     * @param searchTerm
     *          term to search for a mode with
     * @return the mode corresponding to the search term provided
     * @throws NoSuchElementException  if no matching mode
     */
    public static Mode getMode(String searchTerm) {
        if (isNull(searchTerm)) {
            throw new IllegalArgumentException("searchTerm cannot be null");
        }

        String lowerSearch = searchTerm.toLowerCase();
        for (Mode mode : Mode.values()) {
            if (mode.searchTerms.contains(lowerSearch)) {
                return mode;
            }
        }

        throw new PlasterException("Cannot find mode for: " + searchTerm);
    }

    /**
     * Gets the scope from the mode
     *
     * @param scopeKey
     *          name of the scope to get
     */
    public void perform(String scopeKey, FileInformation fileInformation) {
        ModeScope scope = ModeScope.valueOf(StringUtils.upperCase(scopeKey));

        if (!this.scopes.contains(scope)) {
            throw new PlasterException(this.name() + " does not support: " + scope.name());
        }

        this.performFunction.execute(scope, fileInformation);
    }

    private interface Perform {

        void execute(ModeScope scope, FileInformation fileInformation);

    }

}
