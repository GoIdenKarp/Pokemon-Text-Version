package items;

import enums.BattleItemEffect;

public abstract class BattleItem extends Item {

    private static BattleItemEffect effect;

    public BattleItemEffect getEffect() {
        return effect;
    }

}

