package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Raticate extends Pokémon {

    private static final int[] BASE_STATS = {55, 81, 60, 50, 70, 97};
    private static final int CATCH_RATE = 127;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 0, 2};
    private static final int NUMBER = 20;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.RUN_AWAY, Ability.GUTS};
    private static final int XP_YIELD = 57;
    private static final String SPECIES_NAME = "Raticate";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.NORMAL));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "2'04";
    private static final double WEIGHT = 40.8;
    private static final String CATEGORY = "Mouse";
    private static final String DEX_ENTRY = "It makes its Rattata underlings gather food for it, " +
            "dining solely on the most nutritious and delicious fare.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{SwordsDance.class, Tackle.class, TailWhip.class, QuickAttack.class, FocusEnergy.class};
        Class<? extends Move>[] levelFour = new Class[]{QuickAttack.class};
        Class<? extends Move>[] levelSeven = new Class[]{FocusEnergy.class};
        Class<? extends Move>[] levelTen = new Class[]{Bite.class};
        Class<? extends Move>[] levelThirteen = new Class[]{Pursuit.class};
        Class<? extends Move>[] levelSixteen = new Class[]{HyperFang.class};
        Class<? extends Move>[] levelNineteen = new Class[]{Assurance.class};
        Class<? extends Move>[] levelTwentyFour = new Class[]{Crunch.class};
        Class<? extends Move>[] levelTwentyNine = new Class[]{SuckerPunch.class};
        Class<? extends Move>[] levelThirtyFour = new Class[]{SuperFang.class};
        Class<? extends Move>[] levelThirtyNine = new Class[]{DoubleEdge.class};
        Class<? extends Move>[] levelFortyFour = new Class[]{Endeavor.class};



        LEARNSET.put(1, levelOne);
        LEARNSET.put(4, levelFour);
        LEARNSET.put(7, levelSeven);
        LEARNSET.put(10, levelTen);
        LEARNSET.put(13, levelThirteen);
        LEARNSET.put(16, levelSixteen);
        LEARNSET.put(19, levelNineteen);
        LEARNSET.put(24, levelTwentyFour);
        LEARNSET.put(29, levelTwentyNine);
        LEARNSET.put(34, levelThirtyFour);
        LEARNSET.put(39, levelThirtyNine);
        LEARNSET.put(44, levelFortyFour);

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

    public Raticate(int level, Owner owner, GameFrame.InputHelper inputHelper, GameFrame.GamePrinter gamePrinter) {
        super(level, owner, inputHelper, gamePrinter);
    }

    public Raticate(Rattata toEvolve) {
        super(toEvolve);
    }
}
