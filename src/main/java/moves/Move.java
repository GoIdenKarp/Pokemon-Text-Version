package moves;


import enums.Category;
import enums.Effect;
import enums.Target;
import enums.Type;

public abstract class Move{
	
//	protected int basePower;
//	protected int accuracy;
//    protected int priority;
//    protected int critChance;
//    protected String name;
//    protected int startingMaxPP;
//    protected int finalMaxPP;
//    protected Boolean makesContact;
//
//    protected Type type;
//    protected Category category;
//    protected Effect effect;

	protected int currPP;
	protected int currMaxPP;


	public Move() {
        currMaxPP = getStartingMaxPP();
        currPP = currMaxPP;
	}

    public abstract int getBasePower();

    public abstract int getAccuracy();

    public abstract int getPriority();

    public abstract int getCritStage();

    public abstract String getName();

    public abstract int getStartingMaxPP();

    public abstract int getFinalMaxPP();

    public abstract Boolean getMakesContact();

    public  abstract Type getType();

    public abstract Category getCategory();

    public abstract Effect getEffect();

    public abstract Target getTarget();

    public int getCurrPP() {
        return currPP;
    }

    public int getCurrMaxPP() {
        return currMaxPP;
    }

    public void decreasePP() {
        currPP -= 1;
    }

    public Boolean hasPPLeft() {
        return currPP != 0;
    }

    public abstract String getDescription();

    public void restorePP(int amt) {
	    currPP += amt;
	    if (currPP > currMaxPP) {
	        currPP = currMaxPP;
        }
    }

    public abstract boolean increaseMaxPP(int amt);

    public void refillPP() {
        currPP = currMaxPP;
    }

    public void setCurrPP(int pp) {
        currPP = pp;
    }

    public void setCurrMaxPP(int maxPP) {
        currMaxPP = maxPP;
    }

    public String toString() {
        return getName();
    }

}
