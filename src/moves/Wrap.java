package moves;

import enums.Category;
import enums.Effect;
import enums.Target;
import enums.Type;

public class Wrap extends Move {

    private static final int BASE_POWER = 15;
    private static final int ACCURACY = 90;
    private static final int PRIORITY = 0;
    private static final int CRIT_STAGE = 0;
    private static final String NAME = "Wrap";
    private static final int STARTING_MAX_PP = 20;
    private static final int FINAL_MAX_PP = 32;
    private static final Boolean MAKES_CONTACT = true;
    private static final String DESCRIPTION = "A long body, vines, or the like are used to wrap and squeeze the target for four to five turns.";


    private static final Type TYPE = Type.NORMAL;
    private static final Effect EFFECT = Effect.WRAP;
    private static final Category CATEGORY = Category.PHYSICAL;
    private static final Target TARGET = Target.SINGLE_TARGET;



    public Wrap() {
        super();
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
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

    public int getStartingMaxPP() {
        return STARTING_MAX_PP;
    }

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
    public Effect getEffect() {
        return EFFECT;
    }

    public Target getTarget() {return TARGET;}

    /**
     * Attempts to raise the maximum PP of the Move
     * @param amt the amount to raise the max PP by
     * @return true if PP limit could be raised; false otherwise
     */
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

    @Override
    public Category getCategory() {
        return CATEGORY;
    }
}
