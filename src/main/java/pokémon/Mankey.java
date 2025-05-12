package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Mankey extends Pokémon {

    private static final int[] BASE_STATS = {40, 80, 35, 35, 45, 70};
    private static final int CATCH_RATE = 190;
    private static final int[] EV_YIELD = {0, 1, 0, 0, 0, 0};
    private static final int NUMBER = 56;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.VITAL_SPIRIT, Ability.ANGER_POINT};
    private static final int XP_YIELD = 61;
    private static final String SPECIES_NAME = "Mankey";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.FIGHTING));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "1'08";
    private static final double WEIGHT = 61.7;
    private static final String CATEGORY = "Pig Monkey";
    private static final String DEX_ENTRY = "An agile Pokémon that lives in trees. " +
            "It angers easily and will not hesitate to attack anything.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Leer.class, Scratch.class};
        Class<? extends Move>[] levelFive = new Class[]{FocusEnergy.class};
        Class<? extends Move>[] levelEight = new Class[]{Taunt.class};
        Class<? extends Move>[] levelThirteen = new Class[]{KarateChop.class};
        Class<? extends Move>[] levelSixteen = new Class[]{FurySwipes.class};
        Class<? extends Move>[] levelTwentyOne = new Class[]{LowKick.class};
        Class<? extends Move>[] levelTwentyFour = new Class[]{SeismicToss.class};
        Class<? extends Move>[] levelTwentyNine = new Class[]{UTurn.class};
        Class<? extends Move>[] levelThirtyTwo = new Class[]{Screech.class};
        Class<? extends Move>[] levelThirtySeven = new Class[]{Thrash.class};
        Class<? extends Move>[] levelForty = new Class[]{Outrage.class};

        LEARNSET.put(1, levelOne);
        LEARNSET.put(5, levelFive);
        LEARNSET.put(8, levelEight);
        LEARNSET.put(13, levelThirteen);
        LEARNSET.put(16, levelSixteen);
        LEARNSET.put(21, levelTwentyOne);
        LEARNSET.put(24, levelTwentyFour);
        LEARNSET.put(29, levelTwentyNine);
        LEARNSET.put(32, levelThirtyTwo);
        LEARNSET.put(37, levelThirtySeven);
        LEARNSET.put(40, levelForty);
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
        return 28;
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
    public Primeape evolve() {
        return new Primeape(this);
    }

    @Override
    public String getDexEntry() {
        return DEX_ENTRY;
    }

    @Override
    public String getHeight() {
        return HEIGHT;
    }


    @Override
    public String getCategory() {
        return CATEGORY;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    public Mankey(int level, Owner owner) {
        super(level, owner);
    }

    public Mankey(int level, Owner owner, Item item) {
        super(level, owner, item);
    }
}
