package items;

import enums.BerryEffect;

public abstract class Berry extends Item {

    private static BerryEffect effect;

    public BerryEffect getEffect() {
        return effect;
    }
}
