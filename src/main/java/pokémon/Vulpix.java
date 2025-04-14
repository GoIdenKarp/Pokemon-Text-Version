package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Vulpix extends Pokémon {

    private static final int[] BASE_STATS = {38, 41, 40, 50, 65, 65};
    private static final int CATCH_RATE = 190;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 0, 1};
    private static final int NUMBER = 37;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.FLASH_FIRE};
    private static final int XP_YIELD = 60;
    private static final String SPECIES_NAME = "Vulpix";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.FIRE));
    private static final double genderRatio = 25;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.STONE;
    private static final String HEIGHT = "2'00";
    private static final double WEIGHT = 21.8;
    private static final String CATEGORY = "Fox";
    private static final String DEX_ENTRY = "Both its fur and its tails are beautiful. As it grows, " +
            "the tails split and form more tails. ";

    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;

    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Tackle.class};
        Class<? extends Move>[] levelThree = new Class[]{TailWhip.class};
        Class<? extends Move>[] levelSeven = new Class[]{Ember.class};
        Class<? extends Move>[] levelTen = new Class[]{QuickAttack.class};
        Class<? extends Move>[] levelFourteen = new Class[]{ConfuseRay.class};
        Class<? extends Move>[] levelSeventeen = new Class[]{WillOWisp.class};
        Class<? extends Move>[] levelTwentyOne = new Class[]{FireSpin.class};
        Class<? extends Move>[] levelTwentyFour = new Class[]{Flamethrower.class};
        Class<? extends Move>[] levelTwentyEight = new Class[]{Roar.class};
        Class<? extends Move>[] levelThirtyOne = new Class[]{FireBlast.class};

        LEARNSET.put(1, levelOne);
        LEARNSET.put(3, levelThree);
        LEARNSET.put(7, levelSeven);
        LEARNSET.put(10, levelTen);
        LEARNSET.put(14, levelFourteen);
        LEARNSET.put(17, levelSeventeen);
        LEARNSET.put(21, levelTwentyOne);
        LEARNSET.put(24, levelTwentyFour);
        LEARNSET.put(28, levelTwentyEight);
        LEARNSET.put(31, levelThirtyOne);

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
    public Ninetales evolve() {
        return new Ninetales(this);
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

    public Vulpix(int level, Owner owner, GameFrame.InputHelper inputHelper, GameFrame.GamePrinter gamePrinter) {
        super(level, owner, inputHelper, gamePrinter);
    }

}
