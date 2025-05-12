package pokémon;

import enums.*;
import moves.*;
import ui.GameFrame;

import java.util.*;

public class Nidoking extends Pokémon {

    private static final int[] BASE_STATS = {81, 92, 77, 85, 75, 85};
    private static final int CATCH_RATE = 45;
    private static final int[] EV_YIELD = {0, 3, 0, 0, 0, 0};
    private static final int NUMBER = 34;
    private static final Ability[] POTENTIAL_ABILITIES = {Ability.POISON_POINT, Ability.RIVALRY};
    private static final int XP_YIELD = 227;
    private static final String SPECIES_NAME = "Nidoking";
    private static final List<Type> TYPES = new ArrayList<>(Arrays.asList(Type.POISON, Type.GROUND));
    private static final double genderRatio = 100;
    private static final EvolveMethod EVOLVE_METHOD = EvolveMethod.NONE;
    private static final String HEIGHT = "4'07";
    private static final double WEIGHT = 136.7;
    private static final String DEX_ENTRY = "Its steel-like hide adds to its powerful tackle. Its horns are so hard, they can pierce a diamond.";
    private static final String CATEGORY = "Drill";
    private static final Map<Integer, Class<? extends Move>[]> LEARNSET;
    static {
        LEARNSET = new TreeMap<>();
        Class<? extends Move>[] levelOne = new Class[]{Thrash.class, Supersonic.class, Counter.class, Leer.class,
                Peck.class, FocusEnergy.class, PoisonSting.class};
        Class<? extends Move>[] levelFiftyFive = new Class[]{Megahorn.class};

        LEARNSET.put(1, levelOne);
        LEARNSET.put(55, levelFiftyFive);
    }
    private static final GrowthRate GROWTH_RATE = GrowthRate.MEDIUM_SLOW;
    private static final Class<? extends Move>[] learnOnEvolve = new Class[]{Thrash.class};




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

    public Nidoking(int level, Owner owner) {
        super(level, owner);
    }

    public Nidoking(int level, Owner owner, Item item) {
        super(level, owner, item);
    }

    public Nidoking(Nidorino toEvolve) {
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
