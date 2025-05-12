package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Beedrill extends Pokémon {

    //Gen VI+ stats
    private static final int[] BASE_STATS = {65, 90, 40, 45, 80, 75};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 2, 0, 0, 1, 0};
    private static final int NUMBER = 15;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.SWARM};
    private static final int XP_YIELD = 159;
    private static final String SPECIES_NAME = "Beedrill";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.BUG, Type.POISON));
    private static final double genderRatio = 50;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "3'03";
    private static final double WEIGHT = 65.0;
    private static final String DEX_ENTRY = "It has three poisonous stingers on its forelegs and its tail. " +
            "They are used to jab its enemy repeatedly.";
    private static final String CATEGORY = "Poison Bee";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Twineedle.class, FuryAttack.class};
        Class<? extends Move>[] levelEleven = new Class[]{FuryAttack.class};
        Class<? extends Move>[] levelFourteen = new Class[]{Rage.class};
        Class<? extends Move>[] levelSeventeen = new Class[]{Pursuit.class};
        Class<? extends Move>[] levelTwenty = new Class[]{FocusEnergy.class};
        Class<? extends Move>[] levelTwentyThree = new Class[]{Venoshock.class};
        Class<? extends Move>[] levelTwentySix = new Class[]{Assurance.class};
        Class<? extends Move>[] levelTwentyNine = new Class[]{ToxicSpikes.class};
        Class<? extends Move>[] levelThirtyTwo = new Class[]{PinMissle.class};
        Class<? extends Move>[] levelThirtyFive = new Class[]{PoisonJab.class};
        Class<? extends Move>[] levelThirtyEight = new Class[]{Agility.class};
        Class<? extends Move>[] levelFortyOne = new Class[]{Endeavor.class};
        Class<? extends Move>[] levelFortyFour = new Class[]{FellStinger.class};
        LEARNSET.put(1, levelOne);
        LEARNSET.put(11, levelEleven);
        LEARNSET.put(14, levelFourteen);
        LEARNSET.put(17, levelSeventeen);
        LEARNSET.put(20, levelTwenty);
        LEARNSET.put(23, levelTwentyThree);
        LEARNSET.put(26, levelTwentySix);
        LEARNSET.put(29, levelTwentyNine);
        LEARNSET.put(32, levelThirtyTwo);
        LEARNSET.put(35, levelThirtyFive);
        LEARNSET.put(38, levelThirtyEight);
        LEARNSET.put(41, levelFortyOne);
        LEARNSET.put(44, levelFortyFour);

    }
    private static final GrowthRate GROWTH_RATE = GrowthRate.MEDIUM_SLOW;
    private static final Class<? extends Move>[] learnOnEvolve = new Class[]{Twineedle.class};



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

    public Beedrill(int level, Owner owner) {
        super(level, owner);
    }

    public Beedrill(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

    public Beedrill(Kakuna toEvolve) {
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
