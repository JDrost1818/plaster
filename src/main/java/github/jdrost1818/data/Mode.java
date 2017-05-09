package github.jdrost1818.data;

import com.google.common.collect.Lists;
import github.jdrost1818.exception.EnumSearchException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;

import static github.jdrost1818.data.ModeScope.*;
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
    public static Mode getMode(String searchTerm) throws EnumSearchException {
        if (isNull(searchTerm)) {
            throw new IllegalArgumentException("searchTerm cannot be null");
        }

        String lowerSearch = searchTerm.toLowerCase();
        for (Mode mode : Mode.values()) {
            if (mode.searchTerms.contains(lowerSearch)) {
                return mode;
            }
        }

        throw new EnumSearchException("Cannot find mode for: " + searchTerm);
    }

    /**
     * Gets the scope from the mode
     *
     * @param scopeKey
     *          name of the scope to get
     * @return the scope
     */
    public ModeScope getScope(String scopeKey) {
        ModeScope scope = ModeScope.valueOf(StringUtils.upperCase(scopeKey));

        if (this.scopes.contains(scope)) {
            return scope;
        } else {
            throw new UnsupportedOperationException(this.name() + " does not support: " + scope.name());
        }
    }

}
