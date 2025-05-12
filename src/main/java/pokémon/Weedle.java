package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Weedle extends Pokémon {

    private static final int[] BASE_STATS = {40, 35, 30, 20, 20, 50};
    private static final int CATCH_RATE = 255;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 0, 1};
    private static final int NUMBER = 13;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.SHIELD_DUST};
    private static final int XP_YIELD = 52;
    private static final String SPECIES_NAME = "Weedle";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.BUG, Type.POISON));
    private static final double genderRatio = 50;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "1'00";
    private static final double WEIGHT = 7.1;
    private static final String CATEGORY = "Hairy Bug";
    private static final String DEX_ENTRY = "Beware of the sharp stinger on its head. " +
            "It hides in grass and bushes where it eats leaves.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{PoisonSting.class, StringShot.class};
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
    public Kakuna evolve() {
        return new Kakuna(this);
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

    public Weedle(int level, Owner owner) {
        super(level, owner);
    }

    public Weedle(int level, Owner owner, Item item) {
        super(level, owner, item);
    }
}
