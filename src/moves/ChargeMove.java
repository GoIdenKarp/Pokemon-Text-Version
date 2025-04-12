package moves;

import enums.Effect;

/**
 * Created by AriG on 6/12/17.
 */
public abstract class ChargeMove extends Move {


    protected boolean partOneDone;
    protected Effect currentEffect;


    public ChargeMove() {
        super();
        partOneDone = false;
    }

    public void togglePartOneDone() {
        partOneDone = !partOneDone;
        Effect effectOne = getTurnOneEffect();
        Effect effectTwo = getTurnTwoEffect();
        if (currentEffect == effectOne) {
            currentEffect = effectTwo;
        } else {
            currentEffect = effectOne;
        }
    }

    public boolean getPartOneDone() {
        return partOneDone;
    }

    public abstract String getChargeMessage();

    public abstract Effect getTurnOneEffect();

    public abstract Effect getTurnTwoEffect();

}
