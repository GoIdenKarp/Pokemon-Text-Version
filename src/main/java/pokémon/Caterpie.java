package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Caterpie extends Pokémon {

    private static final int[] BASE_STATS = {45, 30, 35, 20, 20, 45};
    private static final int CATCH_RATE = 255;
    private static final int[] EV_YIELD = {1, 0, 0, 0, 0, 0};
    private static final int NUMBER = 10;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.SHIELD_DUST};
    private static final int XP_YIELD = 53;
    private static final String SPECIES_NAME = "Caterpie";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.BUG));
    private static final double genderRatio = 50;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "1'00";
    private static final double WEIGHT = 6.4;
    private static final String DEX_ENTRY = "If you touch the feeler on top of its head, " +
            "it will release a horrible stink to protect itself.";
    private static final String CATEGORY = "Worm";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Tackle.class, StringShot.class};
        Class<? extends Move>[] levelNine = new Class[]{BugBite.class};
        LEARNSET.put(1, levelOne);
        LEARNSET.put(9, levelNine);
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
        return 7;
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
    public Metapod evolve() {
        return new Metapod(this);
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

    public Caterpie(int level, Owner owner) {
        super(level, owner);
    }

    public Caterpie(int level, Owner owner, Item item) {
        super(level, owner, item);
    }
}
