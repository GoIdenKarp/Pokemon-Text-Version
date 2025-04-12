package moves;

import java.util.concurrent.ThreadLocalRandom;

public abstract class MultiTurnMove extends Move {

    protected static final int TURNS_LOWER_BOUND = 2;
    protected static final int TURNS_UPPER_BOUND = 3;

    protected int turns;
    protected int turnsLeft;

    public MultiTurnMove() {
        super();
        turns = generateTurns();
        turnsLeft = turns;
    }

    /**
     * Generate randomly a number of turns for this move to last
     * @return How many turns the move will last for (2 or 3)
     */
    protected int generateTurns() {
        return ThreadLocalRandom.current().nextInt(TURNS_LOWER_BOUND, TURNS_UPPER_BOUND + 1);
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }

    public void decrementTurnsLeft() {
        turnsLeft--;
    }

    public boolean isFirstTurn() {
        return turns == turnsLeft;
    }

    public boolean moveIsDone() {
        if (turnsLeft == 0) {
            turns = generateTurns();
            turnsLeft = turns;
            return true;
        } else {
            return false;
        }
    }

}
