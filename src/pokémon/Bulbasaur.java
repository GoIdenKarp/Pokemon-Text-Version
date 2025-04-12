package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Bulbasaur extends Pokémon {

    private static final int[] BASE_STATS = {45, 49, 49, 65, 65, 45};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 0, 0, 1, 0, 0};
    private static final int NUMBER = 1;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.OVERGROW};
    private static final int XP_YIELD = 64;
    private static final String SPECIES_NAME = "Bulbasaur";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.GRASS, Type.POISON));
    private static final double genderRatio = 87.5;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "2'04";
    private static final double WEIGHT = 15.2;
    private static final String DEX_ENTRY = "It can go for days without eating a single morsel. " +
            "In the bulb on its back, it stores energy.";
    private static final String CATEGORY = "Seed";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Tackle.class};
        Class<? extends Move>[] levelThree = new Class[]{Growl.class};
        Class<? extends Move>[] levelSeven = new Class[]{LeechSeed.class};
        Class<? extends Move>[] levelNine = new Class[]{VineWhip.class};
        Class<? extends Move>[] levelThirteen = new Class[]{PoisonPowder.class, SleepPowder.class};
        Class<? extends Move>[] levelFifteen = new Class[]{TakeDown.class};
        Class<? extends Move>[] levelNineteen = new Class[]{RazorLeaf.class};
        Class<? extends Move>[] levelTwentyOne = new Class[]{SweetScent.class};
        Class<? extends Move>[] levelTwentyFive = new Class[]{Growth.class};
        Class<? extends Move>[] levelTwentySeven = new Class[]{DoubleEdge.class};
        Class<? extends Move>[] levelThirtyOne = new Class[]{WorrySeed.class};
        Class<? extends Move>[] levelThirtyThree = new Class[]{Synthesis.class};
        Class<? extends Move>[] levelThirtySeven = new Class[]{SeedBomb.class};
        LEARNSET.put(1, levelOne);
        LEARNSET.put(3, levelThree);
        LEARNSET.put(7, levelSeven);
        LEARNSET.put(9, levelNine);
        LEARNSET.put(13, levelThirteen);
        LEARNSET.put(15, levelFifteen);
        LEARNSET.put(19, levelNineteen);
        LEARNSET.put(21, levelTwentyOne);
        LEARNSET.put(25, levelTwentyFive);
        LEARNSET.put(27, levelTwentySeven);
        LEARNSET.put(31, levelThirtyOne);
        LEARNSET.put(33, levelThirtyThree);
        LEARNSET.put(37, levelThirtySeven);
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
        return 16;
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
    public Ivysaur evolve() {
        return new Ivysaur(this);
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

    @Override
    public double getGenderRatio() {
        return genderRatio;
    }

    public Bulbasaur(int level, Owner owner, GameFrame.InputHelper inputHelper, GameFrame.GamePrinter gamePrinter) {
        super(level, owner, inputHelper, gamePrinter);
    }

}
