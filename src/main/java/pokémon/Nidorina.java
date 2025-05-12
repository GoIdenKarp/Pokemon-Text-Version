package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Nidorina extends Pokémon {

    private static final int[] BASE_STATS = {70, 62, 67, 55, 55, 56};
    private static final int CATCH_RATE = 120;
    private static final int[] EV_YIELD = {2, 0, 0, 0, 0, 0};
    private static final int NUMBER = 30;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.POISON_POINT, Ability.RIVALRY};
    private static final int XP_YIELD = 128;
    private static final String SPECIES_NAME = "Nidorina";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.POISON));
    private static final double genderRatio = 0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.STONE;
    private static final String HEIGHT = "2'07";
    private static final double WEIGHT = 44.1;
    private static final String CATEGORY = "Poison Pin";
    private static final String DEX_ENTRY = "When resting deep in its burrow, its barbs always retract. " +
            "This is proof that it is relaxed.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Growl.class, Scratch.class, TailWhip.class, PoisonSting.class};
        Class<? extends Move>[] levelThree = new Class[]{TailWhip.class};
        Class<? extends Move>[] levelSix = new Class[]{PoisonSting.class};
        Class<? extends Move>[] levelNine = new Class[]{DoubleKick.class};
        Class<? extends Move>[] levelTwelve = new Class[]{Bite.class};
        Class<? extends Move>[] levelFifteen = new Class[]{HelpingHand.class};
        Class<? extends Move>[] levelTwentyThree = new Class[]{Toxic.class};
        Class<? extends Move>[] levelThirtyOne = new Class[]{FurySwipes.class};
        Class<? extends Move>[] levelThirtyNine = new Class[]{Crunch.class};
        Class<? extends Move>[] levelFortySeven = new Class[]{SuperFang.class};

        LEARNSET.put(1, levelOne);
        LEARNSET.put(3, levelThree);
        LEARNSET.put(6, levelSix);
        LEARNSET.put(9, levelNine);
        LEARNSET.put(12, levelTwelve);
        LEARNSET.put(15, levelFifteen);
        LEARNSET.put(23, levelTwentyThree);
        LEARNSET.put(31, levelThirtyOne);
        LEARNSET.put(39, levelThirtyNine);
        LEARNSET.put(47, levelFortySeven);
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
    public Nidoqueen evolve() {
        return new Nidoqueen(this);
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

    public Nidorina(int level, Owner owner) {
        super(level, owner);
    }

    public Nidorina(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

    public Nidorina(NidoranF toEvolve) {
        super(toEvolve);
    }
}
