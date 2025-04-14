package moves;

import enums.Category;
import enums.Effect;
import enums.Target;
import enums.Type;

public class Dig extends ChargeMove {

    private static final String CHARGE_MESSAGE = "%s burrowed it's way under the ground!\n";
    //Uses Gen IV+ power
    private static final int BASE_POWER = 80;
    private static final int ACCURACY = 100;
    private static final int PRIORITY = 0;
    private static final int CRIT_STAGE = 0;
    private static final String NAME = "Dig";
    private static final int STARTING_MAX_PP = 10;
    private static final int FINAL_MAX_PP = 16;
    private static final Boolean MAKES_CONTACT = true;
    private static final String DESCRIPTION = "The user burrows, then attacks on the next turn.";


    private static final Type TYPE = Type.GROUND;
    private static final Category CATEGORY = Category.PHYSICAL;
    private static final Target TARGET = Target.SINGLE_TARGET;

    private static final Effect effectOne = Effect.DIG;
    private static final Effect effectTwo = Effect.NONE;


    public Dig() {
        super();
        currentEffect = effectOne;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getChargeMessage() {
        return CHARGE_MESSAGE;
    }

    @Override
    public Effect getTurnOneEffect() {
        return effectOne;
    }

    @Override
    public Effect getTurnTwoEffect() {
        return effectTwo;
    }

    @Override
    public int getBasePower() {
        return BASE_POWER;
    }

    @Override
    public int getAccuracy() {
        return ACCURACY;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public int getCritStage() {
        return CRIT_STAGE;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getStartingMaxPP() {
        return STARTING_MAX_PP;
    }

    @Override
    public int getFinalMaxPP() {
        return FINAL_MAX_PP;
    }

    @Override
    public Boolean getMakesContact() {
        return MAKES_CONTACT;
    }

    @Override
    public Type getType() {
        return TYPE;
    }

    @Override
    public Category getCategory() {
        return CATEGORY;
    }

    @Override
    public Effect getEffect() {
        return currentEffect;
    }

    @Override
    public Target getTarget() {
        return TARGET;
    }

    @Override
    public boolean increaseMaxPP(int amt) {
        if (currMaxPP == FINAL_MAX_PP) {
            return false;
        } else {
            currMaxPP += amt;
            if (currMaxPP > FINAL_MAX_PP) {
                currMaxPP = FINAL_MAX_PP;
            }
            return true;
        }
    }

}
