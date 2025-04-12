package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Ivysaur extends Pokémon {

    private static final int[] BASE_STATS = {60, 62, 63, 80, 80, 60};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 0, 0, 1, 1, 0};
    private static final int NUMBER = 2;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.OVERGROW};
    private static final int XP_YIELD = 142;
    private static final String SPECIES_NAME = "Ivysaur";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.GRASS, Type.POISON));
    private static final double genderRatio = 87.5;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "3'03";
    private static final double WEIGHT = 28.7;
    private static final String CATEGORY = "Seed";
    private static final String DEX_ENTRY = "The bud on its back grows by drawing energy. " +
            "It gives off an aroma when it is ready to bloom.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Tackle.class, Growl.class, LeechSeed.class};
        Class<? extends Move>[] levelThree = new Class[]{Growl.class};
        Class<? extends Move>[] levelSeven = new Class[]{LeechSeed.class};
        Class<? extends Move>[] levelNine = new Class[]{VineWhip.class};
        Class<? extends Move>[] levelThirteen = new Class[]{PoisonPowder.class, SleepPowder.class};
        Class<? extends Move>[] levelFifteen = new Class[]{TakeDown.class};
        Class<? extends Move>[] levelTwenty = new Class[]{RazorLeaf.class};
        Class<? extends Move>[] levelTwentyThree = new Class[]{SweetScent.class};
        Class<? extends Move>[] levelTwentyEight = new Class[]{Growth.class};
        Class<? extends Move>[] levelThirtyOne = new Class[]{DoubleEdge.class};
        Class<? extends Move>[] levelThirtySix = new Class[]{WorrySeed.class};
        Class<? extends Move>[] levelThirtyNine = new Class[]{Synthesis.class};
        Class<? extends Move>[] levelFortyFour = new Class[]{SolarBeam.class};
        LEARNSET.put(1, levelOne);
        LEARNSET.put(3, levelThree);
        LEARNSET.put(7, levelSeven);
        LEARNSET.put(9, levelNine);
        LEARNSET.put(13, levelThirteen);
        LEARNSET.put(15, levelFifteen);
        LEARNSET.put(20, levelTwenty);
        LEARNSET.put(23, levelTwentyThree);
        LEARNSET.put(28, levelTwentyEight);
        LEARNSET.put(31, levelThirtyOne);
        LEARNSET.put(36, levelThirtySix);
        LEARNSET.put(39, levelThirtyNine);
        LEARNSET.put(44, levelFortyFour);
    }
    private static final GrowthRate GROWTH_RATE = GrowthRate.MEDIUM_SLOW;



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
        return 32;
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
    public Venusaur evolve() {
        return new Venusaur(this);
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

    public Ivysaur(int level, Owner owner, GameFrame.InputHelper inputHelper, GameFrame.GamePrinter gamePrinter) {
        super(level, owner, inputHelper, gamePrinter);
    }

    public Ivysaur(Bulbasaur toEvolve) {
        super(toEvolve);
    }

}
