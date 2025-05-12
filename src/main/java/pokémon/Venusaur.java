package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Venusaur extends Pokémon {

    private static final int[] BASE_STATS = {80, 82, 83, 100, 100, 80};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 0, 0, 2, 1, 0};
    private static final int NUMBER = 3;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.OVERGROW};
    private static final int XP_YIELD = 236;
    private static final String SPECIES_NAME = "Venusaur";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.GRASS, Type.POISON));
    private static final double genderRatio = 87.5;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "6'07";
    private static final double WEIGHT = 220.5;
    private static final String CATEGORY = "Seed";
    private static final String DEX_ENTRY = "The flower on its back catches the sun's rays. " +
            "The sunlight is then absorbed and used for energy.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{PetalDance.class, Tackle.class, Growl.class, LeechSeed.class,
                VineWhip.class};
        Class<? extends Move>[] levelThree = new Class[]{Growl.class};
        Class<? extends Move>[] levelSeven = new Class[]{LeechSeed.class};
        Class<? extends Move>[] levelNine = new Class[]{VineWhip.class};
        Class<? extends Move>[] levelThirteen = new Class[]{PoisonPowder.class, SleepPowder.class};
        Class<? extends Move>[] levelFifteen = new Class[]{TakeDown.class};
        Class<? extends Move>[] levelTwenty = new Class[]{RazorLeaf.class};
        Class<? extends Move>[] levelTwentyThree = new Class[]{SweetScent.class};
        Class<? extends Move>[] levelTwentyEight = new Class[]{Growth.class};
        Class<? extends Move>[] levelThirtyOne = new Class[]{DoubleEdge.class};
        Class<? extends Move>[] levelThirtyNine = new Class[]{WorrySeed.class};
        Class<? extends Move>[] levelFortyFive = new Class[]{Synthesis.class};
        Class<? extends Move>[] levelFifty = new Class[]{PetalBlizzard.class};
        Class<? extends Move>[] levelFiftyThree = new Class[]{SolarBeam.class};
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
        LEARNSET.put(39, levelThirtyNine);
        LEARNSET.put(45, levelFortyFive);
        LEARNSET.put(50, levelFifty);
        LEARNSET.put(53, levelFiftyThree);
    }
    private static final GrowthRate GROWTH_RATE = GrowthRate.MEDIUM_SLOW;
    private static final Class<? extends Move>[] learnOnEvolve = new Class[]{PetalDance.class};



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

    public Venusaur(int level, Owner owner) {
        super(level, owner);
    }

    public Venusaur(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

    public Venusaur(Ivysaur toEvolve) {
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
