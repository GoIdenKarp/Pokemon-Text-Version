package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Pidgey extends Pokémon {

    private static final int[] BASE_STATS = {40, 45, 40, 35, 35, 56};
    private static final int CATCH_RATE = 255;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 0, 1};
    private static final int NUMBER = 16;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.KEEN_EYE, Ability.TANGLED_FEET};
    private static final int XP_YIELD = 55;
    private static final String SPECIES_NAME = "Pidgey";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.NORMAL, Type.FLYING));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "1'00";
    private static final double WEIGHT = 4.0;
    private static final String CATEGORY = "Tiny Bird";
    private static final String DEX_ENTRY = "Very docile. " +
            "If attacked, it will often kick up sand to protect itself rather than fight back.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Tackle.class};
        Class<? extends Move>[] levelFive = new Class[]{SandAttack.class};
        Class<? extends Move>[] levelNine = new Class[]{Gust.class};
        Class<? extends Move>[] levelThirteen = new Class[]{QuickAttack.class};
        Class<? extends Move>[] levelSeventeen = new Class[]{Whirlwind.class};
        Class<? extends Move>[] levelTwentyOne = new Class[]{Twister.class};
        Class<? extends Move>[] levelTwentyFive = new Class[]{FeatherDance.class};
        Class<? extends Move>[] levelTwentyNine = new Class[]{Agility.class};
        Class<? extends Move>[] levelThirtyThree = new Class[]{WingAttack.class};
        Class<? extends Move>[] levelThirtySeven = new Class[]{Roost.class};
        Class<? extends Move>[] levelFortyOne = new Class[]{Tailwind.class};
        Class<? extends Move>[] levelFortyFive = new Class[]{MirrorMove.class};
        Class<? extends Move>[] levelFortyNine = new Class[]{AirSlash.class};
        Class<? extends Move>[] levelFiftyThree = new Class[]{Hurricane.class};


        LEARNSET.put(1, levelOne);
        LEARNSET.put(5, levelFive);
        LEARNSET.put(9, levelNine);
        LEARNSET.put(13, levelThirteen);
        LEARNSET.put(17, levelSeventeen);
        LEARNSET.put(21, levelTwentyOne);
        LEARNSET.put(25, levelTwentyFive);
        LEARNSET.put(29, levelTwentyNine);
        LEARNSET.put(33, levelThirtyThree);
        LEARNSET.put(37, levelThirtySeven);
        LEARNSET.put(41, levelFortyOne);
        LEARNSET.put(45, levelFortyFive);
        LEARNSET.put(49, levelFortyNine);
        LEARNSET.put(53, levelFiftyThree);;
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
        return 18;
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
    public Pidgeotto evolve() {
        return new Pidgeotto(this);
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

    public Pidgey(int level, Owner owner) {
        super(level, owner);
    }

    public Pidgey(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

}
