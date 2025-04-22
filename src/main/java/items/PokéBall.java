package items;

import enums.BallEffect;

public class PokéBall extends Ball {

    private static int COST = 200;
    private static final String NAME = "Poké Ball";
    private static final String ENCODED_NAME = "POKEBALL";
    private static final String DESCRIPTION = "A Ball thrown to catch a wild Pokémon. It is designed in a capsule style.\n";
    private static final BallEffect BALL_EFFECT = BallEffect.NONE;

    public PokéBall() {

    }

    public BallEffect getEffect() {
        return BALL_EFFECT;
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
