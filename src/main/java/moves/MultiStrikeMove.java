package moves;

import java.util.Random;

public abstract class MultiStrikeMove extends Move {


    public MultiStrikeMove() {
        super();
    }

    public int getNumberofHits() {
        return new Random().nextInt(3) + 2;
    }

}
