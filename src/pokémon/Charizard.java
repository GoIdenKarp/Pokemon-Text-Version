package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Charizard extends Pokémon {

    private static final int[] BASE_STATS = {78, 84, 78, 109, 85, 100};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 0, 0, 3, 0, 0};
    private static final int NUMBER = 6;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.BLAZE};
    private static final int XP_YIELD = 240;
    private static final String SPECIES_NAME = "Charizard";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.FIRE, Type.FLYING));
    private static final double genderRatio = 87.5;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "5'07";
    private static final double WEIGHT = 199.5;
    private static final String DEX_ENTRY = "When this Pokémon expels a blast of superhot fire, " +
            "the red flame at the tip of its tails burns more intensely.";
    private static final String CATEGORY = "Flame";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{WingAttack.class, FlareBlitz.class, HeatWave.class,
                DragonClaw.class, ShadowClaw.class, AirSlash.class,  Scratch.class, Growl.class, Ember.class};
        Class<? extends Move>[] levelSeven = new Class[]{Ember.class};
        Class<? extends Move>[] levelTen = new Class[]{Smokescreen.class};
        Class<? extends Move>[] levelSeventeen = new Class[]{DragonRage.class};
        Class<? extends Move>[] levelTwentyOne = new Class[]{ScaryFace.class};
        Class<? extends Move>[] levelTwentyEight = new Class[]{FireFang.class};
        Class<? extends Move>[] levelThirtyTwo = new Class[]{FlameBurst.class};
        Class<? extends Move>[] levelFortyOne = new Class[]{Slash.class};
        Class<? extends Move>[] levelFortySeven = new Class[]{Flamethrower.class};
        Class<? extends Move>[] levelFiftySix = new Class[]{FireSpin.class};
        Class<? extends Move>[] levelSixtyTwo = new Class[]{Inferno.class};
        Class<? extends Move>[] levelSeventyOne = new Class[]{HeatWave.class};
        Class<? extends Move>[] levelSeventySeven = new Class[]{FlareBlitz.class};
        LEARNSET.put(1, levelOne);
        LEARNSET.put(7, levelSeven);
        LEARNSET.put(10, levelTen);
        LEARNSET.put(17, levelSeventeen);
        LEARNSET.put(21, levelTwentyOne);
        LEARNSET.put(28, levelTwentyEight);
        LEARNSET.put(32, levelThirtyTwo);
        LEARNSET.put(41, levelFortyOne);
        LEARNSET.put(47, levelFortySeven);
        LEARNSET.put(56, levelFiftySix);
        LEARNSET.put(62, levelSixtyTwo);
        LEARNSET.put(71, levelSeventyOne);
        LEARNSET.put(77, levelSeventySeven);

    }
    private static final GrowthRate GROWTH_RATE = GrowthRate.MEDIUM_SLOW;
    private static final Class<? extends Move>[] learnOnEvolve = new Class[]{WingAttack.class};



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

    public Charizard(int level, Owner owner, GameFrame.InputHelper inputHelper, GameFrame.GamePrinter gamePrinter) {
        super(level, owner, inputHelper, gamePrinter);
    }

    public Charizard(Charmeleon toEvolve) {
        super(toEvolve);
        try {
            attemptLearn(learnOnEvolve);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
