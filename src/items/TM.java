package items;

import moves.Move;

public abstract class TM extends Item {

    private static Class<? extends Move> move;

    public Class<? extends Move> getMove() {
        return move;
    }

}
