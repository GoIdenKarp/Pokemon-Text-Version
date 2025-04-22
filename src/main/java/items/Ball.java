package items;

import enums.BallEffect;

public abstract class Ball extends Item {

    private static BallEffect BALL_EFFECT;

    public BallEffect getEffect() {
        return BALL_EFFECT;
    }

}
