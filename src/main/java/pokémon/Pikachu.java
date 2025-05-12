package pokémon;

import enums.*;
import moves.*;
import items.Item;

import java.util.*;

public class Pikachu extends Pokémon {

    private static final int[] BASE_STATS = {35, 55, 40, 50, 50, 90};
    private static final int CATCH_RATE = 190;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 0, 2};
    private static final int NUMBER = 25;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.STATIC};
    private static final int XP_YIELD = 52;
    private static final String SPECIES_NAME = "Pikachu";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.ELECTRIC));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.STONE;
    private static final String HEIGHT = "1'04";
    private static final double WEIGHT = 13.2;
    private static final String CATEGORY = "Mouse";
    private static final String DEX_ENTRY = "This forest-dwelling Pokémon stores electricity in its cheeks, " +
            "so you'll feel a tingly shock if you touch it.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;

    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Thundershock.class, TailWhip.class};
        Class<? extends Move>[] levelFive = new Class[]{Growl.class};
        Class<? extends Move>[] levelSeven = new Class[]{PlayNice.class};
        Class<? extends Move>[] levelTen = new Class[]{QuickAttack.class};
        Class<? extends Move>[] levelThirteen = new Class[]{ElectroBall.class};
        Class<? extends Move>[] levelEighteen = new Class[]{ThunderWave.class};
        Class<? extends Move>[] levelTwentyOne = new Class[]{Feint.class};
        Class<? extends Move>[] levelTwentyThree = new Class[]{DoubleTeam.class};
        Class<? extends Move>[] levelTwentySix = new Class[]{Spark.class};
        Class<? extends Move>[] levelTwentyNine = new Class[]{Nuzzle.class};
        Class<? extends Move>[] levelThirtyFour = new Class[]{Discharge.class};
        Class<? extends Move>[] levelThirtySeven = new Class[]{Slam.class};
        Class<? extends Move>[] levelFortyTwo = new Class[]{Thunderbolt.class};
        Class<? extends Move>[] levelFortyFive = new Class[]{Agility.class};
        Class<? extends Move>[] levelFifty = new Class[]{WildCharge.class};
        Class<? extends Move>[] levelFiftyThree = new Class[]{LightScreen.class};
        Class<? extends Move>[] levelFiftyEight = new Class[]{Thunder.class};

        LEARNSET.put(1, levelOne);
        LEARNSET.put(5, levelFive);
        LEARNSET.put(7, levelSeven);
        LEARNSET.put(10, levelTen);
        LEARNSET.put(13, levelThirteen);
        LEARNSET.put(18, levelEighteen);
        LEARNSET.put(21, levelTwentyOne);
        LEARNSET.put(23, levelTwentyThree);
        LEARNSET.put(26, levelTwentySix);
        LEARNSET.put(29, levelTwentyNine);
        LEARNSET.put(34, levelThirtyFour);
        LEARNSET.put(37, levelThirtySeven);
        LEARNSET.put(42, levelFortyTwo);
        LEARNSET.put(45, levelFortyFive);
        LEARNSET.put(50, levelFifty);
        LEARNSET.put(53, levelFiftyThree);
        LEARNSET.put(58, levelFiftyEight);
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
    public Raichu evolve() {
        return new Raichu(this);
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

    public Pikachu(int level, Owner owner) {
        super(level, owner);
    }

    public Pikachu(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

}
