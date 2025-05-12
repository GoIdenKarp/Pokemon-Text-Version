package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Butterfree extends Pokémon {

    //Gen VI+ stats
    private static final int[] BASE_STATS = {60, 45, 50, 90, 80, 70};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 0, 0, 2, 1, 0};
    private static final int NUMBER = 12;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.COMPOUND_EYES};
    private static final int XP_YIELD = 160;
    private static final String SPECIES_NAME = "Butterfree";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.BUG, Type.FLYING));
    private static final double genderRatio = 50;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "3'07";
    private static final double WEIGHT = 70.5;
    private static final String DEX_ENTRY = "Its wings, covered with poisonous powder, repel water. " +
            "This allows it to fly in the rain.";
    private static final String CATEGORY = "Butterfly";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Gust.class, Confusion.class};
        Class<? extends Move>[] levelEleven = new Class[]{Confusion.class};
        Class<? extends Move>[] levelThirteen = new Class[]{PoisonPowder.class, StunSpore.class, SleepPowder.class};
        Class<? extends Move>[] levelSeventeen = new Class[]{Psybeam.class};
        Class<? extends Move>[] levelNineteen = new Class[]{SilverWind.class};
        Class<? extends Move>[] levelTwentyThree = new Class[]{Supersonic.class};
        Class<? extends Move>[] levelTwentyFive = new Class[]{Safeguard.class};
        Class<? extends Move>[] levelTwentyNine = new Class[]{Whirlwind.class};
        Class<? extends Move>[] levelThirtyOne = new Class[]{BugBuzz.class};
        Class<? extends Move>[] levelThirtySeven = new Class[]{Captivate.class};
        Class<? extends Move>[] levelFortyOne = new Class[]{Tailwind.class};
        Class<? extends Move>[] levelFortyThree = new Class[]{AirSlash.class};
        Class<? extends Move>[] levelFortySeven = new Class[]{QuiverDance.class};
        LEARNSET.put(1, levelOne);
        LEARNSET.put(11, levelEleven);
        LEARNSET.put(13, levelThirteen);
        LEARNSET.put(17, levelSeventeen);
        LEARNSET.put(19, levelNineteen);
        LEARNSET.put(23, levelTwentyThree);
        LEARNSET.put(25, levelTwentyFive);
        LEARNSET.put(29, levelTwentyNine);
        LEARNSET.put(31, levelThirtyOne);
        LEARNSET.put(37, levelThirtySeven);
        LEARNSET.put(41, levelFortyOne);
        LEARNSET.put(43, levelFortyThree);
        LEARNSET.put(47, levelFortySeven);

    }
    private static final GrowthRate GROWTH_RATE = GrowthRate.MEDIUM_SLOW;
    private static final Class<? extends Move>[] learnOnEvolve = new Class[]{Gust.class};



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

    public Butterfree(int level, Owner owner) {
        super(level, owner);
    }

    public Butterfree(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

    public Butterfree(Metapod toEvolve) {
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
