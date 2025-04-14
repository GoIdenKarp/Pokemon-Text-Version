package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class NidoranM extends Pokémon {

    private static final int[] BASE_STATS = {46, 57, 40, 40, 40, 50};
    private static final int CATCH_RATE = 235;
    private static final int[] EV_YIELD = {0, 1, 0, 0, 0, 0};
    private static final int NUMBER = 32;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.POISON_POINT, Ability.RIVALRY};
    private static final int XP_YIELD = 55;
    private static final String SPECIES_NAME = "Nidoran (M)";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.POISON));
    private static final double genderRatio = 100;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.LEVEL;
    private static final String HEIGHT = "1'08";
    private static final double WEIGHT = 19.8;
    private static final String CATEGORY = "Poison Pin";
    private static final String DEX_ENTRY = "Its large ears are always kept upright. " +
            "If it senses danger, it will attack with a poisonous sting.";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;

    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Leer.class, Peck.class};
        Class<? extends Move>[] levelThree = new Class[]{FocusEnergy.class};
        Class<? extends Move>[] levelSix = new Class[]{PoisonSting.class};
        Class<? extends Move>[] levelNine = new Class[]{DoubleKick.class};
        Class<? extends Move>[] levelTwelve = new Class[]{HornAttack.class};
        Class<? extends Move>[] levelFifteen = new Class[]{HelpingHand.class};
        Class<? extends Move>[] levelEighteen = new Class[]{Toxic.class};
        Class<? extends Move>[] levelTwentyOne = new Class[]{FuryAttack.class};
        Class<? extends Move>[] levelTwentyFour = new Class[]{PoisonJab.class};
        Class<? extends Move>[] levelTwentySeven = new Class[]{HornDrill.class};

        LEARNSET.put(1, levelOne);
        LEARNSET.put(3, levelThree);
        LEARNSET.put(6, levelSix);
        LEARNSET.put(9, levelNine);
        LEARNSET.put(12, levelTwelve);
        LEARNSET.put(15, levelFifteen);
        LEARNSET.put(18, levelEighteen);
        LEARNSET.put(21, levelTwentyOne);
        LEARNSET.put(24, levelTwentyFour);
        LEARNSET.put(27, levelTwentySeven);
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
        return 16;
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
    public Nidorino evolve() {
        return new Nidorino(this);
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

    public NidoranM(int level, Owner owner, GameFrame.InputHelper inputHelper, GameFrame.GamePrinter gamePrinter) {
        super(level, owner, inputHelper, gamePrinter);
    }
}
