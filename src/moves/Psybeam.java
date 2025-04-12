package moves;

import enums.Category;
import enums.Effect;
import enums.Target;
import enums.Type;

public class Psybeam extends Move {

    private static final int BASE_POWER = 65;
    private static final int ACCURACY = 100;
    private static final int PRIORITY = 0;
    private static final int CRIT_STAGE = 0;
    private static final String NAME = "Psybeam";
    private static final int STARTING_MAX_PP = 20;
    private static final int FINAL_MAX_PP = 32;
    private static final Boolean MAKES_CONTACT = false;
    private static final String DESCRIPTION = "A peculiar ray is shot at the foe. It may leave the foe confused.";


    private static final Type TYPE = Type.PSYCHIC;
    private static final Effect EFFECT = Effect.CONFUSE_TEN;
    private static final Category CATEGORY = Category.SPECIAL;
    private static final Target TARGET = Target.SINGLE_TARGET;



    public Psybeam() {
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
