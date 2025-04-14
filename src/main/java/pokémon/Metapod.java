package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Metapod extends Pokémon {

    private static final int[] BASE_STATS = {50, 20, 55, 25, 25, 30};
    private static final int CATCH_RATE = 120;
    private static final int[] EV_YIELD = {0, 0, 2, 0, 0, 0};
    private static final int NUMBER = 11;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.SHED_SKIN};
    private static final int XP_YIELD = 71;
    private static final String SPECIES_NAME = "Metapod";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.BUG));
    private static final double genderRatio = 50;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "2'04";
    private static final double WEIGHT = 21.8;
    private static final String CATEGORY = "Cocoon";
    private static final String DEX_ENTRY = "Hardens its shell to protect itself. " +
            "However, a large impact may cause it to pop out of its shell.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Harden.class};
        LEARNSET.put(1, levelOne);
    }
    private static final GrowthRate GROWTH_RATE = GrowthRate.MEDIUM_FAST;
    private static final Class<? extends Move>[] learnOnEvolve = new Class[]{Harden.class};




    @Override
    public int[] getBaseStats() {
        return BASE_STATS;
    }

    @Override
    public int getCatchRate() {
        return CATCH_RATE;
    }

    @Override
    public int[] getEvYield() {
        return EV_YIELD;
    }

    @Override
    public int getNumber() {
        return NUMBER;
    }

    @Override
    public Ability[] getPotentialAbilities() {
        return POTENTIAL_ABILITIES;
    }

    @Override
    public int getXpYield() {
        return XP_YIELD;
    }

    @Override
    public String getSpeciesName() {
        return SPECIES_NAME;
    }

    @Override
    public EvolveMethod getEvolveMethod() {
        return EVOLVE_METHOD;
    }

    @Override
    public int getEvolveNumber() {
        return 10;
    }

    @Override
    public List<Type> getType() {
        return TYPES;
    }

    @Override
    public Map<Integer, Class<? extends Move>[]> getLearnset() {
        return LEARNSET;
    }

    @Override
    public GrowthRate getGrowthRate() {
        return GROWTH_RATE;
    }

    @Override
    public double getGenderRatio() {
        return genderRatio;
    }

    @Override
    public Butterfree evolve() {
        return new Butterfree(this);
    }

    @Override
    public String getDexEntry() {
        return DEX_ENTRY;
    }

    @Override
    public String getCategory() {
        return CATEGORY;
    }

    @Override
    public String getHeight() {
        return HEIGHT;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    public Metapod(int level, Owner owner, GameFrame.InputHelper inputHelper, GameFrame.GamePrinter gamePrinter) {
        super(level, owner, inputHelper, gamePrinter);
    }

    public Metapod(Caterpie toEvolve) {
        super(toEvolve);
        try {
            attemptLearn(learnOnEvolve);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
