package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Pidgeotto extends Pokémon {

    private static final int[] BASE_STATS = {63, 60, 55, 50, 50, 71};
    private static final int CATCH_RATE = 120;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 0, 2};
    private static final int NUMBER = 17;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.KEEN_EYE, Ability.TANGLED_FEET};
    private static final int XP_YIELD = 113;
    private static final String SPECIES_NAME = "Pidgeotto";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.NORMAL, Type.FLYING));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "3'07";
    private static final double WEIGHT = 66.1;
    private static final String CATEGORY = "Bird";
    private static final String DEX_ENTRY = "This Pokémon is full of vitality. " +
            "It constantly flies around its large territory in search of prey.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Tackle.class, SandAttack.class, Gust.class};
        Class<? extends Move>[] levelFive = new Class[]{SandAttack.class};
        Class<? extends Move>[] levelNine = new Class[]{Gust.class};
        Class<? extends Move>[] levelThirteen = new Class[]{QuickAttack.class};
        Class<? extends Move>[] levelSeventeen = new Class[]{Whirlwind.class};
        Class<? extends Move>[] levelTwentyTwo = new Class[]{Twister.class};
        Class<? extends Move>[] levelTwentySeven = new Class[]{FeatherDance.class};
        Class<? extends Move>[] levelThirtyTwo = new Class[]{Agility.class};
        Class<? extends Move>[] levelThirtySeven = new Class[]{WingAttack.class};
        Class<? extends Move>[] levelFortyTwo = new Class[]{Roost.class};
        Class<? extends Move>[] levelFortySeven = new Class[]{Tailwind.class};
        Class<? extends Move>[] levelFiftyTwo = new Class[]{MirrorMove.class};
        Class<? extends Move>[] levelFiftySeven = new Class[]{AirSlash.class};
        Class<? extends Move>[] levelSixtyTwo = new Class[]{Hurricane.class};


        LEARNSET.put(1, levelOne);
        LEARNSET.put(5, levelFive);
        LEARNSET.put(9, levelNine);
        LEARNSET.put(13, levelThirteen);
        LEARNSET.put(17, levelSeventeen);
        LEARNSET.put(22, levelTwentyTwo);
        LEARNSET.put(27, levelTwentySeven);
        LEARNSET.put(32, levelThirtyTwo);
        LEARNSET.put(37, levelThirtySeven);
        LEARNSET.put(42, levelFortyTwo);
        LEARNSET.put(47, levelFortySeven);
        LEARNSET.put(52, levelFiftyTwo);
        LEARNSET.put(57, levelFiftySeven);
        LEARNSET.put(62, levelSixtyTwo);
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
        return 36;
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
    public Pidgeot evolve() {
        return new Pidgeot(this);
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

    public Pidgeotto(int level, Owner owner) {
        super(level, owner);
    }

    public Pidgeotto(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

    public Pidgeotto(Pidgey toEvolve) {
        super(toEvolve);
    }
}
