package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Rattata extends Pokémon {

    private static final int[] BASE_STATS = {30, 56, 35, 25, 35, 72};
    private static final int CATCH_RATE = 255;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 0, 1};
    private static final int NUMBER = 19;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.RUN_AWAY, Ability.GUTS};
    private static final int XP_YIELD = 57;
    private static final String SPECIES_NAME = "Rattata";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.NORMAL));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "1'00";
    private static final double WEIGHT = 7.7;
    private static final String CATEGORY = "Mouse";
    private static final String DEX_ENTRY = "Will chew on anything with its fangs. " +
            "If you see one, you can be certain that 40 more live in the area.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Tackle.class, TailWhip.class};
        Class<? extends Move>[] levelFour = new Class[]{QuickAttack.class};
        Class<? extends Move>[] levelSeven = new Class[]{FocusEnergy.class};
        Class<? extends Move>[] levelTen = new Class[]{Bite.class};
        Class<? extends Move>[] levelThirteen = new Class[]{Pursuit.class};
        Class<? extends Move>[] levelSixteen = new Class[]{HyperFang.class};
        Class<? extends Move>[] levelNineteen = new Class[]{Assurance.class};
        Class<? extends Move>[] levelTwentyTwo = new Class[]{Crunch.class};
        Class<? extends Move>[] levelTwentyFive = new Class[]{SuckerPunch.class};
        Class<? extends Move>[] levelTwentyEight = new Class[]{SuperFang.class};
        Class<? extends Move>[] levelThirtyOne = new Class[]{DoubleEdge.class};
        Class<? extends Move>[] levelThirtyFour = new Class[]{Endeavor.class};



        LEARNSET.put(1, levelOne);
        LEARNSET.put(4, levelFour);
        LEARNSET.put(7, levelSeven);
        LEARNSET.put(10, levelTen);
        LEARNSET.put(13, levelThirteen);
        LEARNSET.put(16, levelSixteen);
        LEARNSET.put(19, levelNineteen);
        LEARNSET.put(22, levelTwentyTwo);
        LEARNSET.put(25, levelTwentyFive);
        LEARNSET.put(28, levelTwentyEight);
        LEARNSET.put(31, levelThirtyOne);
        LEARNSET.put(34, levelThirtyFour);

    }
    private static final GrowthRate GROWTH_RATE = GrowthRate.MEDIUM_FAST;


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
        return 20;
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
    public Raticate evolve() {
        return new Raticate(this);
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

    public Rattata(int level, Owner owner) {
        super(level, owner);
    }

    public Rattata(int level, Owner owner, Item item) {
        super(level, owner, item);
    }
}
