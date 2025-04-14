package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Charmander extends Pokémon {

    private static final int[] BASE_STATS = {39, 52, 43, 60, 50, 65};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 0, 1};
    private static final int NUMBER = 4;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.BLAZE};
    private static final int XP_YIELD = 62;
    private static final String SPECIES_NAME = "Charmander";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.FIRE));
    private static final double genderRatio = 87.5;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "2'00";
    private static final double WEIGHT = 18.7;
    private static final String DEX_ENTRY = "The flame at the tip of its tail makes a sound as it burns. " +
            "You can only hear it in quiet places.";
    private static final String CATEGORY = "Lizard";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Scratch.class, Growl.class};
        Class<? extends Move>[] levelSeven = new Class[]{Ember.class};
        Class<? extends Move>[] levelTen = new Class[]{Smokescreen.class};
        Class<? extends Move>[] levelSixteen = new Class[]{DragonRage.class};
        Class<? extends Move>[] levelNineteen = new Class[]{ScaryFace.class};
        Class<? extends Move>[] levelTwentyFive = new Class[]{FireFang.class};
        Class<? extends Move>[] levelTwentyEight = new Class[]{FlameBurst.class};
        Class<? extends Move>[] levelThirtyFour = new Class[]{Slash.class};
        Class<? extends Move>[] levelThirtySeven = new Class[]{Flamethrower.class};
        Class<? extends Move>[] levelFortyThree = new Class[]{FireSpin.class};
        Class<? extends Move>[] levelFortySix = new Class[]{Inferno.class};
        LEARNSET.put(1, levelOne);
        LEARNSET.put(7, levelSeven);
        LEARNSET.put(10, levelTen);
        LEARNSET.put(16, levelSixteen);
        LEARNSET.put(19, levelNineteen);
        LEARNSET.put(25, levelTwentyFive);
        LEARNSET.put(28, levelTwentyEight);
        LEARNSET.put(34, levelThirtyFour);
        LEARNSET.put(37, levelThirtySeven);
        LEARNSET.put(43, levelFortyThree);
        LEARNSET.put(46, levelFortySix);
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
        return 16;
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
    public Charmeleon evolve() {
        return new Charmeleon(this);
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

    public Charmander(int level, Owner owner, GameFrame.InputHelper inputHelper, GameFrame.GamePrinter gamePrinter) {
        super(level, owner, inputHelper, gamePrinter);
    }

}
