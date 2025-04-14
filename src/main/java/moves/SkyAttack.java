package moves;

import enums.Category;
import enums.Effect;
import enums.Target;
import enums.Type;

public class SkyAttack extends ChargeMove {

    private static final String CHARGE_MESSAGE = "%s is glowing!\n";
    //Uses pre-LGPE power
    private static final int BASE_POWER = 140;
    private static final int ACCURACY = 90;
    private static final int PRIORITY = 0;
    private static final int CRIT_STAGE = 1;
    private static final String NAME = "Sky Attack";
    private static final int STARTING_MAX_PP = 5;
    private static final int FINAL_MAX_PP = 8;
    private static final Boolean MAKES_CONTACT = false;
    private static final String DESCRIPTION = "A second-turn attack move where critical hits land more easily. This may also make the target flinch.";


    private static final Type TYPE = Type.FLYING;
    private static final Category CATEGORY = Category.PHYSICAL;
    private static final Target TARGET = Target.SINGLE_TARGET;

    private static final Effect effectOne = Effect.NONE;
    private static final Effect effectTwo = Effect.FLINCH_THIRTY;


    public SkyAttack() {
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
