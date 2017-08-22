package github.jdrost1818.plaster.data;

import com.google.common.collect.Lists;
import github.jdrost1818.plaster.exception.PlasterException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;

import static github.jdrost1818.plaster.data.ModeScope.*;
import static java.util.Objects.isNull;

@AllArgsConstructor
public enum Mode {

    GENERATE(
            Lists.newArrayList("g", "gen", "generate"),
            Lists.newArrayList(SCAFFOLD, MODEL, REPOSITORY, SERVICE, CONTROLLER)),

    DELETE(
            Lists.newArrayList("d", "del", "delete"),
            Lists.newArrayList(SCAFFOLD, MODEL, REPOSITORY, SERVICE, CONTROLLER));

    private final List<String> searchTerms;
    private final List<ModeScope> scopes;

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

}
