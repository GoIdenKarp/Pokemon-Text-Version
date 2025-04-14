package items;

import enums.BallEffect;

public abstract class Ball extends Item {

    private static BallEffect effect;

    public BallEffect getEffect() {
        return effect;
    }

}
