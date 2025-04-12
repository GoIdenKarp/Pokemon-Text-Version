package items;

import enums.HealEffect;

public class SuperPotion extends HealingItem {

    private final int COST = 700;
    private final static String NAME = "Super Potion";
    private final static String ENCODED_NAME = "SUPOTION";
    private final static String DESCRIPTION = "A spray-type wound medicine. It restores the HP of one Pokémon by 50 points.\n";
    private final static HealEffect HEALING_AMOUNT = HealEffect.HEAL_FIFTY;

    public SuperPotion() {

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
