package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Ekans extends Pokémon {

    private static final int[] BASE_STATS = {35, 60, 44, 40, 54, 55};
    private static final int CATCH_RATE = 255;
    private static final int[] EV_YIELD = {0, 1, 0, 0, 0, 2};
    private static final int NUMBER = 23;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.INTIMIDATE, Ability.SHED_SKIN};
    private static final int XP_YIELD = 58;
    private static final String SPECIES_NAME = "Ekans";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.POISON));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "6'07";
    private static final double WEIGHT = 15.2;
    private static final String CATEGORY = "Snake";
    private static final String DEX_ENTRY = "The older it gets, the longer it grows. At night, it wraps its long body around tree branches to rest.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;

    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Wrap.class, PoisonSting.class};
        Class<? extends Move>[] levelFour = new Class[]{Leer.class};
        Class<? extends Move>[] levelEight = new Class[]{Acid.class};
        Class<? extends Move>[] levelTwelve = new Class[]{Bite.class};
        Class<? extends Move>[] levelSixteen = new Class[]{Haze.class};
        Class<? extends Move>[] levelTwenty = new Class[]{Glare.class};
        Class<? extends Move>[] levelTwentyFour = new Class[]{PoisonJab.class};
        Class<? extends Move>[] levelTwentyEight = new Class[]{Slam.class};
        Class<? extends Move>[] levelThirtyTwo = new Class[]{Screech.class};
        Class<? extends Move>[] levelThirtySix = new Class[]{Toxic.class};

        LEARNSET.put(1, levelOne);
        LEARNSET.put(4, levelFour);
        LEARNSET.put(8, levelEight);
        LEARNSET.put(12, levelTwelve);
        LEARNSET.put(16, levelSixteen);
        LEARNSET.put(20, levelTwenty);
        LEARNSET.put(24, levelTwentyFour);
        LEARNSET.put(28, levelTwentyEight);
        LEARNSET.put(32, levelThirtyTwo);
        LEARNSET.put(36, levelThirtySix);
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
        return 22;
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
    public Arbok evolve() {
        return new Arbok(this);
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

    public Ekans(int level, Owner owner) {
        super(level, owner);
    }

    public Ekans(int level, Owner owner, Item item) {
        super(level, owner, item);
    }
}
