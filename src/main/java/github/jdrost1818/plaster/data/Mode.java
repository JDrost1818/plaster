package github.jdrost1818.plaster.data;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.domain.FileInformation;
import github.jdrost1818.plaster.exception.PlasterException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;

import static github.jdrost1818.plaster.data.ModeScope.*;
import static java.util.Objects.isNull;

public enum Mode {

    GENERATE(
            Lists.newArrayList("g", "gen", "generate"),
            Lists.newArrayList(SCAFFOLD, CONTROLLER, SERVICE, REPOSITORY, FIELDS)),

    DELETE(
            Lists.newArrayList("r", "rm", "remove", "d", "del", "delete"),
            Lists.newArrayList(SCAFFOLD, CONTROLLER, SERVICE, REPOSITORY));

    private final List<String> searchTerms;
    private final List<ModeScope> scopes;
    Mode(List<String> searchTerms, List<ModeScope> scopes) {
        this.searchTerms = searchTerms;
        this.scopes = scopes;
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

        // Todo: I don't like this. Make this better
        if (this == GENERATE) {
            scope.generate(fileInformation);
        } else if (this == DELETE) {
            scope.delete(fileInformation);
        } else {
            throw new PlasterException("I don't know how you got here and frankly, I'm impressed");
        }
    }

}
