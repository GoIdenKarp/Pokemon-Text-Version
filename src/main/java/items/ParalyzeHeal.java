package items;

import enums.HealEffect;

public class ParalyzeHeal extends HealingItem {


    private final static int COST = 200;
    private final static String NAME = "Paralyze Heal";
    private final static String ENCODED_NAME = "PARALYZHL";
    private final static String DESCRIPTION = "A spray-type medicine. It eliminates paralysis from a single Pokémon.\n";
    private final static HealEffect HEALING_EFFECT = HealEffect.HEAL_PARALYZE;

    public ParalyzeHeal() {

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
