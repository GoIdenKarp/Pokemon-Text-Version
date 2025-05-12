package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Charmeleon extends Pokémon {

    private static final int[] BASE_STATS = {58, 64, 58, 80, 65, 80};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 0, 0, 1, 0, 1};
    private static final int NUMBER = 5;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.BLAZE};
    private static final int XP_YIELD = 142;
    private static final String SPECIES_NAME = "Charmeleon";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.FIRE));
    private static final double genderRatio = 87.5;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "3'07";
    private static final double WEIGHT = 41.9;
    private static final String DEX_ENTRY = "Tough fights could excite this Pokémon. " +
            "When excited, it may breathe out bluish-white flames.";
    private static final String CATEGORY = "Flame";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Scratch.class, Growl.class, Ember.class};
        Class<? extends Move>[] levelSeven = new Class[]{Ember.class};
        Class<? extends Move>[] levelTen = new Class[]{Smokescreen.class};
        Class<? extends Move>[] levelSeventeen = new Class[]{DragonRage.class};
        Class<? extends Move>[] levelTwentyOne = new Class[]{ScaryFace.class};
        Class<? extends Move>[] levelTwentyEight = new Class[]{FireFang.class};
        Class<? extends Move>[] levelThirtyTwo = new Class[]{FlameBurst.class};
        Class<? extends Move>[] levelThirtyNine = new Class[]{Slash.class};
        Class<? extends Move>[] levelFortyThree = new Class[]{Flamethrower.class};
        Class<? extends Move>[] levelFifty = new Class[]{FireSpin.class};
        Class<? extends Move>[] levelFiftyFour = new Class[]{Inferno.class};
        LEARNSET.put(1, levelOne);
        LEARNSET.put(7, levelSeven);
        LEARNSET.put(10, levelTen);
        LEARNSET.put(17, levelSeventeen);
        LEARNSET.put(21, levelTwentyOne);
        LEARNSET.put(28, levelTwentyEight);
        LEARNSET.put(32, levelThirtyTwo);
        LEARNSET.put(39, levelThirtyNine);
        LEARNSET.put(43, levelFortyThree);
        LEARNSET.put(50, levelFifty);
        LEARNSET.put(54, levelFiftyFour);
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
    public Charizard evolve() {
        return new Charizard(this);
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

    public Charmeleon(int level, Owner owner) {
        super(level, owner);
    }

    public Charmeleon(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

    public Charmeleon(Charmander toEvolve) {
        super(toEvolve);
    }

}
