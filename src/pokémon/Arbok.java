package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Arbok extends Pokémon {

    private static final int[] BASE_STATS = {60, 85, 69, 65, 79, 80};
    private static final int CATCH_RATE = 90;
    private static final int[] EV_YIELD = {0, 2, 0, 0, 0, 0};
    private static final int NUMBER = 24;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.INTIMIDATE, Ability.SHED_SKIN};
    private static final int XP_YIELD = 157;
    private static final String SPECIES_NAME = "Arbok";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.POISON));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "11'06";
    private static final double WEIGHT = 143.3;
    private static final String CATEGORY = "Cobra";
    private static final String DEX_ENTRY = "The frightening patterns on its belly have been studied. Six variations have been confirmed.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Crunch.class, SuckerPunch.class, Wrap.class,
                PoisonSting.class, Leer.class, Acid.class};
        Class<? extends Move>[] levelFour = new Class[]{Leer.class};
        Class<? extends Move>[] levelNine = new Class[]{Acid.class};
        Class<? extends Move>[] levelTwelve = new Class[]{Bite.class};
        Class<? extends Move>[] levelSixteen = new Class[]{Haze.class};
        Class<? extends Move>[] levelTwenty = new Class[]{Glare.class};
        Class<? extends Move>[] levelTwentyEight = new Class[]{PoisonJab.class};
        Class<? extends Move>[] levelThirtySix = new Class[]{Slam.class};
        Class<? extends Move>[] levelFortyFour = new Class[]{Screech.class};
        Class<? extends Move>[] levelFiftyTwo = new Class[]{Toxic.class};

        LEARNSET.put(1, levelOne);
        LEARNSET.put(4, levelFour);
        LEARNSET.put(9, levelNine);
        LEARNSET.put(12, levelTwelve);
        LEARNSET.put(16, levelSixteen);
        LEARNSET.put(20, levelTwenty);
        LEARNSET.put(28, levelTwentyEight);
        LEARNSET.put(36, levelThirtySix);
        LEARNSET.put(44, levelFortyFour);
        LEARNSET.put(52, levelFiftyTwo);
    }
    private static final GrowthRate GROWTH_RATE = GrowthRate.MEDIUM_FAST;
    private static final Class<? extends Move>[] learnOnEvolve = new Class[]{Crunch.class};


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
        return 0;
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
    public Pokémon evolve() {
        return null;
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

    public Arbok(int level, Owner owner, GameFrame.InputHelper inputHelper, GameFrame.GamePrinter gamePrinter) {
        super(level, owner, inputHelper, gamePrinter);
    }

    public Arbok(Ekans toEvolve) {
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
