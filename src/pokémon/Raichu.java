package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Raichu extends Pokémon {

    //Uses Gen VI stats
    private static final int[] BASE_STATS = {60, 90, 55, 90, 80, 110};
    private static final int CATCH_RATE = 75;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 0, 3};
    private static final int NUMBER = 26;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.STATIC};
    private static final int XP_YIELD = 218;
    private static final String SPECIES_NAME = "Raichu";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.ELECTRIC));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "2'07";
    private static final double WEIGHT = 66.1;
    private static final String CATEGORY = "Mouse";
    private static final String DEX_ENTRY = "When electricity builds up inside its body, it becomes feisty. " +
            "It also glows in the dark.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Thundershock.class, TailWhip.class, QuickAttack.class,
            Thunderbolt.class};
        LEARNSET.put(1, levelOne);
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

    public Raichu(int level, Owner owner, GameFrame.InputHelper inputHelper, GameFrame.GamePrinter gamePrinter) {
        super(level, owner, inputHelper, gamePrinter);
    }

    public Raichu(Pikachu toEvolve) {
        super(toEvolve);
    }
}
