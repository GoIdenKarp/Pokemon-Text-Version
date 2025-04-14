package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Primeape extends Pokémon {

    private static final int[] BASE_STATS = {65, 105, 60, 60, 70, 95};
    private static final int CATCH_RATE = 75;
    private static final int[] EV_YIELD = {0, 2, 0, 0, 0, 0};
    private static final int NUMBER = 57;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.VITAL_SPIRIT, Ability.ANGER_POINT};
    private static final int XP_YIELD = 159;
    private static final String SPECIES_NAME = "Primeape";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.FIGHTING));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "3'03";
    private static final double WEIGHT = 70.5;
    private static final String CATEGORY = "Pig Monkey";
    private static final String DEX_ENTRY = "It stops being angry only when nobody else is around. " +
            "To view this moment is very difficult.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;

    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Rage.class, Encore.class, Counter.class, Leer.class, Scratch.class,
            FocusEnergy.class, Taunt.class};
        Class<? extends Move>[] levelFive = new Class[]{FocusEnergy.class};
        Class<? extends Move>[] levelEight = new Class[]{Taunt.class};
        Class<? extends Move>[] levelThirteen = new Class[]{KarateChop.class};
        Class<? extends Move>[] levelSixteen = new Class[]{FurySwipes.class};
        Class<? extends Move>[] levelTwentyOne = new Class[]{LowKick.class};
        Class<? extends Move>[] levelTwentyFour = new Class[]{SeismicToss.class};
        Class<? extends Move>[] levelThirtyThree = new Class[]{UTurn.class};
        Class<? extends Move>[] levelForty = new Class[]{Screech.class};
        Class<? extends Move>[] levelFortyNine = new Class[]{Thrash.class};
        Class<? extends Move>[] levelFiftySix = new Class[]{Outrage.class};

        LEARNSET.put(1, levelOne);
        LEARNSET.put(5, levelFive);
        LEARNSET.put(8, levelEight);
        LEARNSET.put(13, levelThirteen);
        LEARNSET.put(16, levelSixteen);
        LEARNSET.put(21, levelTwentyOne);
        LEARNSET.put(24, levelTwentyFour);
        LEARNSET.put(33, levelThirtyThree);
        LEARNSET.put(40, levelForty);
        LEARNSET.put(49, levelFortyNine);
        LEARNSET.put(56, levelFiftySix);
    }
    private static final GrowthRate GROWTH_RATE = GrowthRate.MEDIUM_FAST;
    private static final Class<? extends Move>[] learnOnEvolve = new Class[]{Rage.class};


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
    public Raichu evolve() {
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

    public Primeape(int level, Owner owner, GameFrame.InputHelper inputHelper, GameFrame.GamePrinter gamePrinter) {
        super(level, owner, inputHelper, gamePrinter);
    }

    public Primeape(Mankey toEvolve) {
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
