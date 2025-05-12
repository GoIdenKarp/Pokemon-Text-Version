package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Sandshrew extends Pokémon {

    private static final int[] BASE_STATS = {50, 75, 85, 20, 30, 40};
    private static final int CATCH_RATE = 255;
    private static final int[] EV_YIELD = {0, 0, 1, 0, 0, 0};
    private static final int NUMBER = 27;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.SAND_VEIL};
    private static final int XP_YIELD = 60;
    private static final String SPECIES_NAME = "Sandshrew";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.GROUND));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "2'00";
    private static final double WEIGHT = 26.5;
    private static final String CATEGORY = "Mouse";
    private static final String DEX_ENTRY = "Its body is dry. When it gets cold at night, " +
            "its hide is said to become coated with a fine dew.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;

    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Scratch.class, DefenseCurl.class};
        Class<? extends Move>[] levelFour = new Class[]{PoisonSting.class};
        Class<? extends Move>[] levelEight = new Class[]{SandAttack.class};
        Class<? extends Move>[] levelTwelve = new Class[]{Swift.class};
        Class<? extends Move>[] levelSixteen = new Class[]{FurySwipes.class};
        Class<? extends Move>[] levelTwenty = new Class[]{Dig.class};
        Class<? extends Move>[] levelTwentyFour = new Class[]{Protect.class};
        Class<? extends Move>[] levelTwentyEight = new Class[]{SwordsDance.class};
        Class<? extends Move>[] levelThirtyTwo = new Class[]{Slash.class};
        Class<? extends Move>[] levelThirtySix = new Class[]{Earthquake.class};

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
    public Sandslash evolve() {
        return new Sandslash(this);
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

    public Sandshrew(int level, Owner owner) {
        super(level, owner);
    }

    public Sandshrew(int level, Owner owner, Item item) {
        super(level, owner, item);
    }
}
