package events;

import enums.Species;

import java.util.List;
import java.util.stream.Collectors;

public class PokémonChoiceEvent extends SubEvent {

    private List<Species> species;
    private String prompt;
    private int level;

    public PokémonChoiceEvent(String prompt, List<Species> species, int level,
                              List<String> beforeMain, List<String> afterMain) {
        super(beforeMain, afterMain);
        this.prompt = prompt;
        this.species = species;
        this.level = level;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getLevel() {
        return level;
    }

    public List<Species> getSpecies() {
        return species;
    }

    public void setSpecies(List<Species> species) {
        this.species = species;
    }

    public List<String> getOptions() {
        return species.stream().map(Species::toString).collect(Collectors.toList());
    }
}
