package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Sandslash extends Pokémon {

    private static final int[] BASE_STATS = {75, 100, 110, 45, 55, 65};
    private static final int CATCH_RATE = 90;
    private static final int[] EV_YIELD = {0, 0, 2, 0, 0, 0};
    private static final int NUMBER = 27;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.SAND_VEIL};
    private static final int XP_YIELD = 158;
    private static final String SPECIES_NAME = "Sandslash";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.GROUND));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "3'03";
    private static final double WEIGHT = 65.0;
    private static final String CATEGORY = "Mouse";
    private static final String DEX_ENTRY = "It is skilled at slashing enemies with its claws. If broken, " +
            "they start to grow back in a day.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Counter.class, Scratch.class, DefenseCurl.class,
                PoisonSting.class, SandAttack.class};
        Class<? extends Move>[] levelFour = new Class[]{PoisonSting.class};
        Class<? extends Move>[] levelEight = new Class[]{SandAttack.class};
        Class<? extends Move>[] levelTwelve = new Class[]{Swift.class};
        Class<? extends Move>[] levelSixteen = new Class[]{FurySwipes.class};
        Class<? extends Move>[] levelTwenty = new Class[]{Dig.class};
        Class<? extends Move>[] levelTwentyEight = new Class[]{Protect.class};
        Class<? extends Move>[] levelThirtySix = new Class[]{SwordsDance.class};
        Class<? extends Move>[] levelFortyFour = new Class[]{Slash.class};
        Class<? extends Move>[] levelFiftyTwo = new Class[]{Earthquake.class};

        LEARNSET.put(1, levelOne);
        LEARNSET.put(4, levelFour);
        LEARNSET.put(8, levelEight);
        LEARNSET.put(12, levelTwelve);
        LEARNSET.put(16, levelSixteen);
        LEARNSET.put(20, levelTwenty);
        LEARNSET.put(28, levelTwentyEight);
        LEARNSET.put(36, levelThirtySix);
        LEARNSET.put(44, levelFortyFour);
        LEARNSET.put(52, levelFiftyTwo);
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

    public Sandslash(int level, Owner owner) {
        super(level, owner);
    }

    public Sandslash(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

    public Sandslash(Sandshrew toEvolve) {
        super(toEvolve);
    }
}
