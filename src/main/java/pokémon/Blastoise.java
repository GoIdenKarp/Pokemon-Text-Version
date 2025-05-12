package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Blastoise extends Pokémon {

    private static final int[] BASE_STATS = {79, 83, 100, 85, 105, 78};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 3, 0};
    private static final int NUMBER = 9;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.TORRENT};
    private static final int XP_YIELD = 239;
    private static final String SPECIES_NAME = "Blastoise";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.WATER));
    private static final double genderRatio = 87.5;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "5'03";
    private static final double WEIGHT = 188.5;
    private static final String DEX_ENTRY = "Once it takes aim at its enemy, it blasts out water with even more force than a fire hose.";
    private static final String CATEGORY = "Shellfish";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{FlashCannon.class, Tackle.class, TailWhip.class, WaterGun.class,
        Withdraw.class};
        Class<? extends Move>[] levelFour = new Class[]{TailWhip.class};
        Class<? extends Move>[] levelSeven = new Class[]{WaterGun.class};
        Class<? extends Move>[] levelTen = new Class[]{Withdraw.class};
        Class<? extends Move>[] levelThirteen = new Class[]{Bubble.class};
        Class<? extends Move>[] levelSeventeen = new Class[]{Bite.class};
        Class<? extends Move>[] levelTwentyOne = new Class[]{RapidSpin.class};
        Class<? extends Move>[] levelTwentyFive = new Class[]{Protect.class};
        Class<? extends Move>[] levelTwentyNine = new Class[]{WaterPulse.class};
        Class<? extends Move>[] levelThirtyThree = new Class[]{AquaTail.class};
        Class<? extends Move>[] levelForty = new Class[]{SkullBash.class};
        Class<? extends Move>[] levelFortySeven = new Class[]{IronDefense.class};
        Class<? extends Move>[] levelFiftyFour = new Class[]{RainDance.class};
        Class<? extends Move>[] levelSixty = new Class[]{HydroPump.class};
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
        LEARNSET.put(40, levelForty);
        LEARNSET.put(47, levelFortySeven);
        LEARNSET.put(54, levelFiftyFour);
        LEARNSET.put(60, levelSixty);
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

    public Blastoise(int level, Owner owner) {
        super(level, owner);
    }

    public Blastoise(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

    public Blastoise(Wartortle toEvolve) {
        super(toEvolve);
    }


}
