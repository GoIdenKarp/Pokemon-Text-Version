package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Spearow extends Pokémon {

    private static final int[] BASE_STATS = {40, 60, 30, 31, 31, 70};
    private static final int CATCH_RATE = 255;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 0, 1};
    private static final int NUMBER = 21;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.KEEN_EYE};
    private static final int XP_YIELD = 112;
    private static final String SPECIES_NAME = "Spearow";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.NORMAL, Type.FLYING));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "1'00";
    private static final double WEIGHT = 4.4;
    private static final String CATEGORY = "Tiny Bird";
    private static final String DEX_ENTRY = "Inept at flying high. However, it can fly around very fast to protect its territory.\n";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;

    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Peck.class};
        Class<? extends Move>[] levelThree = new Class[]{Growl.class};
        Class<? extends Move>[] levelEight = new Class[]{Leer.class};
        Class<? extends Move>[] levelEleven = new Class[]{FocusEnergy.class};
        Class<? extends Move>[] levelSixteen = new Class[]{FuryAttack.class};
        Class<? extends Move>[] levelNineteen= new Class[]{MirrorMove.class};
        Class<? extends Move>[] levelTwentyFour = new Class[]{Roost.class};
        Class<? extends Move>[] levelTwentySeven = new Class[]{Agility.class};
        Class<? extends Move>[] levelThirtyTwo = new Class[]{DrillPeck.class};

        LEARNSET.put(1, levelOne);
        LEARNSET.put(3, levelThree);
        LEARNSET.put(8, levelEight);
        LEARNSET.put(11, levelEleven);
        LEARNSET.put(16, levelSixteen);
        LEARNSET.put(19, levelNineteen);
        LEARNSET.put(24, levelTwentyFour);
        LEARNSET.put(27, levelTwentySeven);
        LEARNSET.put(32, levelThirtyTwo);
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
    public Fearow evolve() {
        return new Fearow(this);
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

    public Spearow(int level, Owner owner) {
        super(level, owner);
    }

    public Spearow(int level, Owner owner, Item item) {
        super(level, owner, item);
    }
}
