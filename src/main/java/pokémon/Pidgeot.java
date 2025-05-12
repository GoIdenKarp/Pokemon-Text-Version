package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Pidgeot extends Pokémon {

    private static final int[] BASE_STATS = {63, 60, 55, 50, 50, 71};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 0, 3};
    private static final int NUMBER = 18;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.KEEN_EYE, Ability.TANGLED_FEET};
    private static final int XP_YIELD = 172;
    private static final String SPECIES_NAME = "Pidgeot";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.NORMAL, Type.FLYING));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "4'11";
    private static final double WEIGHT = 87.1;
    private static final String CATEGORY = "Bird";
    private static final String DEX_ENTRY = "This Pokémon flies at Mach 2 speed, seeking prey. " +
            "Its large talons are feared as wicked weapons.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Hurricane.class, Tackle.class, SandAttack.class, Gust.class, QuickAttack.class};
        Class<? extends Move>[] levelFive = new Class[]{SandAttack.class};
        Class<? extends Move>[] levelNine = new Class[]{Gust.class};
        Class<? extends Move>[] levelThirteen = new Class[]{QuickAttack.class};
        Class<? extends Move>[] levelSeventeen = new Class[]{Whirlwind.class};
        Class<? extends Move>[] levelTwentyTwo = new Class[]{Twister.class};
        Class<? extends Move>[] levelTwentySeven = new Class[]{FeatherDance.class};
        Class<? extends Move>[] levelThirtyTwo = new Class[]{Agility.class};
        Class<? extends Move>[] levelThirtyEight = new Class[]{WingAttack.class};
        Class<? extends Move>[] levelFortyFour = new Class[]{Roost.class};
        Class<? extends Move>[] levelFifty = new Class[]{Tailwind.class};
        Class<? extends Move>[] levelFiftySix = new Class[]{MirrorMove.class};
        Class<? extends Move>[] levelSixtyTwo = new Class[]{AirSlash.class};
        Class<? extends Move>[] levelSixtyEight = new Class[]{Hurricane.class};


        LEARNSET.put(1, levelOne);
        LEARNSET.put(5, levelFive);
        LEARNSET.put(9, levelNine);
        LEARNSET.put(13, levelThirteen);
        LEARNSET.put(17, levelSeventeen);
        LEARNSET.put(22, levelTwentyTwo);
        LEARNSET.put(27, levelTwentySeven);
        LEARNSET.put(32, levelThirtyTwo);
        LEARNSET.put(38, levelThirtyEight);
        LEARNSET.put(44, levelFortyFour);
        LEARNSET.put(50, levelFifty);
        LEARNSET.put(56, levelFiftySix);
        LEARNSET.put(62, levelSixtyTwo);
        LEARNSET.put(68, levelSixtyEight);
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

    public Pidgeot(int level, Owner owner) {
        super(level, owner);
    }

    public Pidgeot(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

    public Pidgeot(Pidgeotto toEvolve) {
        super(toEvolve);
    }
}
