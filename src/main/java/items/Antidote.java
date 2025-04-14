package items;

import enums.HealEffect;

public class Antidote extends HealingItem {


    private final static int COST = 100;
    private final static String NAME = "Antidote";
    private final static String ENCODED_NAME = "ANTIDOTE";
    private final static String DESCRIPTION = "A spray-type medicine. It lifts the effect of poison from one Pokémon.\n";
    private final static HealEffect HEALING_EFFECT = HealEffect.HEAL_POISON;

    public Antidote() {

    }

    public HealEffect getHealingEffect() {
        return HEALING_EFFECT;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    public String getEncodedName() {
        return ENCODED_NAME;
    }


}
