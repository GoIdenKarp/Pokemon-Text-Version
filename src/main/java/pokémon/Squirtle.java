package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Squirtle extends Pokémon {

    private static final int[] BASE_STATS = {44, 48, 65, 50, 64, 43};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 0, 1, 0, 0, 0};
    private static final int NUMBER = 7;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.TORRENT};
    private static final int XP_YIELD = 63;
    private static final String SPECIES_NAME = "Squirtle";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.WATER));
    private static final double genderRatio = 87.5;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "1'08";
    private static final double WEIGHT = 19.7;
    private static final String CATEGORY = "Tiny Turtle";
    private static final String DEX_ENTRY = "Shoots water at prey while in the water. " +
            "Withdraws into its shell when in danger.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Tackle.class};
        Class<? extends Move>[] levelFour = new Class[]{TailWhip.class};
        Class<? extends Move>[] levelSeven = new Class[]{WaterGun.class};
        Class<? extends Move>[] levelTen = new Class[]{Withdraw.class};
        Class<? extends Move>[] levelThirteen = new Class[]{Bubble.class};
        Class<? extends Move>[] levelSixteen = new Class[]{Bite.class};
        Class<? extends Move>[] levelNineteen = new Class[]{RapidSpin.class};
        Class<? extends Move>[] levelTwentyTwo = new Class[]{Protect.class};
        Class<? extends Move>[] levelTwentyFive = new Class[]{WaterPulse.class};
        Class<? extends Move>[] levelTwentyEight = new Class[]{AquaTail.class};
        Class<? extends Move>[] levelThirtyOne = new Class[]{SkullBash.class};
        Class<? extends Move>[] levelThirtyFour = new Class[]{IronDefense.class};
        Class<? extends Move>[] levelThirtySeven = new Class[]{RainDance.class};
        Class<? extends Move>[] levelForty = new Class[]{HydroPump.class};
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
        LEARNSET.put(37, levelThirtySeven);
        LEARNSET.put(40, levelForty);
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
    public double getGenderRatio() {
        return genderRatio;
    }

    @Override
    public Wartortle evolve() {
        return new Wartortle(this);
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

    public Squirtle(int level, Owner owner) {
        super(level, owner);
    }

    public Squirtle(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

}
