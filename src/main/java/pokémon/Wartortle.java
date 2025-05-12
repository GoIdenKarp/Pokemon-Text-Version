package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Wartortle extends Pokémon {

    private static final int[] BASE_STATS = {59, 63, 80, 65, 80, 58};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 0, 1, 0, 1, 0};
    private static final int NUMBER = 8;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.TORRENT};
    private static final int XP_YIELD = 142;
    private static final String SPECIES_NAME = "Wartortle";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.WATER));
    private static final double genderRatio = 87.5;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "3'03";
    private static final double WEIGHT = 49.6;
    private static final String CATEGORY = "Turtle";
    private static final String DEX_ENTRY = "When tapped on its head, this Pokémon will pull it in, " +
            "but its tail will stick out a little bit.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Tackle.class, TailWhip.class, WaterGun.class};
        Class<? extends Move>[] levelFour = new Class[]{TailWhip.class};
        Class<? extends Move>[] levelSeven = new Class[]{WaterGun.class};
        Class<? extends Move>[] levelTen = new Class[]{Withdraw.class};
        Class<? extends Move>[] levelThirteen = new Class[]{Bubble.class};
        Class<? extends Move>[] levelSeventeen = new Class[]{Bite.class};
        Class<? extends Move>[] levelTwentyOne = new Class[]{RapidSpin.class};
        Class<? extends Move>[] levelTwentyFive = new Class[]{Protect.class};
        Class<? extends Move>[] levelTwentyNine = new Class[]{WaterPulse.class};
        Class<? extends Move>[] levelThirtyThree = new Class[]{AquaTail.class};
        Class<? extends Move>[] levelThirtySeven = new Class[]{SkullBash.class};
        Class<? extends Move>[] levelFortyOne = new Class[]{IronDefense.class};
        Class<? extends Move>[] levelFortyFive = new Class[]{RainDance.class};
        Class<? extends Move>[] levelFortyNine = new Class[]{HydroPump.class};
        LEARNSET.put(1, levelOne);
        LEARNSET.put(4, levelFour);
        LEARNSET.put(7, levelSeven);
        LEARNSET.put(10, levelTen);
        LEARNSET.put(13, levelThirteen);
        LEARNSET.put(17, levelSeventeen);
        LEARNSET.put(21, levelTwentyOne);
        LEARNSET.put(25, levelTwentyFive);
        LEARNSET.put(29, levelTwentyNine);
        LEARNSET.put(33, levelThirtyThree);
        LEARNSET.put(37, levelThirtySeven);
        LEARNSET.put(41, levelFortyOne);
        LEARNSET.put(45, levelFortyFive);
        LEARNSET.put(49, levelFortyNine);
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
        return 36;
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
    public Blastoise evolve() {
        return new Blastoise(this);
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

    public Wartortle(int level, Owner owner) {
        super(level, owner);
    }

    public Wartortle(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

    public Wartortle(Squirtle toEvolve) {
        super(toEvolve);
    }

}
