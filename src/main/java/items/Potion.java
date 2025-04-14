package items;

import enums.HealEffect;

public class Potion extends HealingItem {

    private static int COST = 300;
    private final static String NAME = "Potion";
    private final static String ENCODED_NAME = "POTION";
    private final static String DESCRIPTION = "A spray-type wound medicine. It restores the HP of one Pokémon by 20 points.\n";
    private final static HealEffect HEALING_AMOUNT = HealEffect.HEAL_TWENTY;

    public Potion() {

    }

    public HealEffect getHealingEffect() {
        return HEALING_AMOUNT;
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
