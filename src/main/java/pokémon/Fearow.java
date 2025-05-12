package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Fearow extends Pokémon{

    private static final int[] BASE_STATS = {65, 90, 65, 61, 61, 100};
    private static final int CATCH_RATE = 90;
    private static final int[] EV_YIELD = {0, 0, 0, 0, 0, 2};
    private static final int NUMBER = 155;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.KEEN_EYE};
    private static final int XP_YIELD = 112;
    private static final String SPECIES_NAME = "Fearow";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.NORMAL, Type.FLYING));
    private static final double genderRatio = 50.0;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "3'11";
    private static final double WEIGHT = 83.8;
    private static final String CATEGORY = "Beak";
    private static final String DEX_ENTRY = "A Pokémon that dates back many years. If it senses danger, it flies high and away, instantly.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;

    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{SkyAttack.class, DrillRun.class, QuickAttack.class, Peck.class,
        Growl.class, Leer.class, FocusEnergy.class};
        Class<? extends Move>[] levelThree = new Class[]{Growl.class};
        Class<? extends Move>[] levelEight = new Class[]{Leer.class};
        Class<? extends Move>[] levelEleven = new Class[]{FocusEnergy.class};
        Class<? extends Move>[] levelSixteen = new Class[]{FuryAttack.class};
        Class<? extends Move>[] levelNineteen= new Class[]{MirrorMove.class};
        Class<? extends Move>[] levelTwentyNine = new Class[]{Roost.class};
        Class<? extends Move>[] levelThirtySeven = new Class[]{Agility.class};
        Class<? extends Move>[] levelFortySeven = new Class[]{DrillPeck.class};

        LEARNSET.put(1, levelOne);
        LEARNSET.put(3, levelThree);
        LEARNSET.put(8, levelEight);
        LEARNSET.put(11, levelEleven);
        LEARNSET.put(16, levelSixteen);
        LEARNSET.put(19, levelNineteen);
        LEARNSET.put(29, levelTwentyNine);
        LEARNSET.put(27, levelThirtySeven);
        LEARNSET.put(32, levelFortySeven);
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

    public Fearow(int level, Owner owner) {
        super(level, owner);
    }

    public Fearow(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

    public Fearow(Spearow toEvolve) {
        super(toEvolve);
    }
}
