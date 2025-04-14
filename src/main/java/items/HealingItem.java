package items;

import enums.HealEffect;

public abstract class HealingItem extends Item implements Usable {

    private static HealEffect effect;

    public HealEffect getEffect() {
        return effect;
    }


}
