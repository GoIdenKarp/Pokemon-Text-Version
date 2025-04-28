package pokémon;
import enums.*;
import items.Item;
import moves.ChargeMove;
import moves.Pursuit;
import ui.GameFrame.GamePrinter;
import ui.GameFrame.InputHelper;
import moves.Move;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public abstract class Pokémon implements Cloneable {

    private static final String NEW_MOVE_PROMPT = "%s wants to learn %s. But, %s already knows four moves. Should %s forget a move to learn %s?";

    protected static final int MAX_IV = 31;
    protected static final int MIN_SLEEP_TIME = 1;
    protected static final int MAX_SLEEP_TIME = 5;
    protected static final int MAX_EV_SINGLE_STAT = 252;
    protected static final int MAX_EV_TOTAL = 510;

    public static final int HP_INDEX = 0;
    public static final int ATK_INDEX = 1;
    public static final int DEF_INDEX = 2;
    public static final int SPATK_INDEX = 3;
    public static final int SPDEF_INDEX = 4;
    public static final int SPD_INDEX = 5;

    public Pokémon copy() throws CloneNotSupportedException {
        Pokémon clone = (Pokémon) this.clone();
        return clone;

    }

    protected int currentHP;
	protected int currentXP;
	protected int level;
	protected int toxicCounter;
	protected int critStage;
	protected int sleepTimer;
	protected int abilitySlot;
	protected boolean evolveTrigger;
	protected int uniqueID;

	protected String nickname;
	protected Nature nature;
    protected Ability ability;
    protected Status status;
    protected Owner owner;
    protected Gender gender;



    protected int[] EVs = new int[6];
	protected int[] IVs = new int[6];
	protected int totalEVs;
	protected int[] stats;
	protected List<Move> moveSet;
	protected Item item;

	protected InputHelper inputHelper;
	protected GamePrinter gamePrinter;

    public abstract int[] getBaseStats();

    public abstract int getCatchRate();

    public abstract int[] getEvYield();

    public abstract int getNumber();

    public abstract Ability[] getPotentialAbilities();

    public abstract int getXpYield();

    public abstract String getSpeciesName();

    public abstract EvolveMethod getEvolveMethod();

    //This can be a level, a friendship threshold, etc. Returns 0 if the species doesn't evolve
    public abstract int getEvolveNumber();

    public abstract List<Type> getType();

    public abstract Map<Integer, Class<? extends Move>[]> getLearnset();

    public abstract GrowthRate getGrowthRate();

    public abstract double getGenderRatio();

    public abstract Pokémon evolve();

    public abstract String getDexEntry();

    public abstract String getCategory();

    public abstract String getHeight();

    public abstract double getWeight();

    public int[] getEVs() {
        return EVs;
    }

    public int[] getIVs() {
        return IVs;
    }

    public int[] getStats() {
        return stats;
    }

    public List<Move> getMoveSet() {
        return moveSet;
    }

    public void setMoveSet(List<Move> moveSet) {
        this.moveSet = moveSet;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getCurrentXP() {
        return currentXP;
    }

    public int getLevel() {
        return level;
    }

    public int getToxicCounter() {
        return toxicCounter;
    }

    public int getCritStage() {
        return critStage;
    }

    public void addCritStage(int stages) { critStage += stages; }

    public int getSleepTimer() {
        return sleepTimer;
    }

    public String getNickname() {
        return nickname;
    }

    public Nature getNature() {
        return nature;
    }

    public Ability getAbility() {
        return ability;
    }

    public Status getStatus() {
        return status;
    }

    public int getAbilitySlot() {return abilitySlot; }

    public int getTotalEVs() {
        return totalEVs;
    }


    public String getGenderString() {
        switch (gender) {
            case MALE:
                return "Male";
            case FEMALE:
                return "Female";
            default:
                return "None";
        }
    }

    public void setStatus(Status status) {
        this.status = status;
        if (status == Status.ASLEEP) {
            sleepTimer = ThreadLocalRandom.current().nextInt(MIN_SLEEP_TIME, MAX_SLEEP_TIME + 1);
        }
    }

    //returns true if awakens this turn, false otherwise
    public boolean deceaseSlpTimer() {
        sleepTimer--;
        if (sleepTimer == 0) {
            this.status = Status.NONE;
        }
        return sleepTimer == 0;
    }

    protected static int getXPAtLevel(int level, GrowthRate growthRate) {
	    switch (growthRate) {
            case ERRATIC:
                if (level <= 50) {
                    return (int) ((Math.pow(level, 3)*(100 - level))/50.0);
                } else if (level <= 68) {
                    return (int) ((Math.pow(level, 3)*(150 - level))/100.0);
                } else if (level <= 98) {
                    return (int) ((Math.pow(level, 3)*Math.floor((1911 - 10*level)/3.0))/500.0);
                } else {
                    return (int) ((Math.pow(level, 3)*(160-level))/100.0);
                }
            case FAST:
                return (int) ((4*Math.pow(level, 3))/5.0);
            case MEDIUM_FAST:
                return (int) Math.pow(level, 3);
            case MEDIUM_SLOW:
                return (int) ((6.0/5.0)*Math.pow(level, 3) - 15*Math.pow(level, 2) + 100*level - 140);
            case SLOW:
                return (int) ((5*Math.pow(level, 3))/4.0);
            default:
                if (level <= 15) {
                    return (int) (Math.pow(level, 3)*((Math.floor((level + 1)/3.0)+24)/50.0));
                } else  if (level <= 36) {
                    return (int) (Math.pow(level, 3)*((level + 14)/50.0));
                } else {
                    return (int) (Math.pow(level, 3)*((Math.floor(level/2.0) + 32)/50.0));
                }
        }
    }

    protected static double getNatureMod(int index, Nature nature) {
	    if (Nature.HELPFUL_NATURES.get(index).contains(nature)) {
	        return 1.1;
        } else if (Nature.HINDERING_NATURES.get(index).contains(nature)) {
	        return 0.9;
        } else {
	        return 1;
        }
    }

    protected int[] calculateStats() {
	    int[] baseStats = getBaseStats();
	    int[] toReturn = new int[6];
	    int hp = (int) Math.floor((2* baseStats[HP_INDEX] + IVs[HP_INDEX] + Math.floor(EVs[HP_INDEX/4]))*level)/100 + level + 10;
        toReturn[HP_INDEX] = hp;
	    for (int i = 1; i < toReturn.length; i++) {
            toReturn[i] = (int) ((Math.floor(((2* baseStats[i] + IVs[i] + Math.floor(EVs[i]/4))*level)/100) + 5) * getNatureMod(i, nature));
        }

	    return toReturn;
    }

    protected List<Move> getStartingMoves() throws IllegalAccessException, InstantiationException {
        List<Move> moveSet = new ArrayList<>();
        Map<Integer, Class<? extends Move>[]> learnset = getLearnset();
        for (int levelLearned : learnset.keySet()) {
            if (levelLearned > level) {
                break;
            }
            Class<? extends Move>[] movesList = learnset.get(levelLearned);
            for (Class<? extends Move> move : movesList) {
                if (moveSet.size() == 4) {
                    moveSet.remove(0);
                }
                moveSet.add(move.newInstance());
            }
        }
        return moveSet;
    }

	//For new Pokémon
	public Pokémon(int level, Owner owner, InputHelper inputHelper, GamePrinter gamePrinter) {
		this.level = level;
		for (int i = 0; i < EVs.length; i++) {
		    EVs[i] = 0;
        }
		for (int i = 0; i < IVs.length; i++) {
		    IVs[i] = ThreadLocalRandom.current().nextInt(0, MAX_IV + 1);
        }
        currentXP = getXPAtLevel(this.level, getGrowthRate());
		nature = Nature.getRandomNature();
		stats = calculateStats();
		currentHP = stats[HP_INDEX];
		Ability[] potentialAbilities = getPotentialAbilities();
		abilitySlot = ThreadLocalRandom.current().nextInt(potentialAbilities.length);
		ability = potentialAbilities[abilitySlot];
        status = Status.NONE;
        toxicCounter = 1;
        nickname = getSpeciesName();
        critStage = 0;
        sleepTimer = 0;
        try {
            moveSet = getStartingMoves();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        this.owner = owner;
        this.inputHelper = inputHelper;
        this.gamePrinter = gamePrinter;
        this.item = null;
        int genderRoll = ThreadLocalRandom.current().nextInt(1, 101);
        if (getGenderRatio() == -1) {
            gender = Gender.NONE;
        } else if (getGenderRatio() >= genderRoll) {
            gender = Gender.MALE;
        } else {
            gender = Gender.FEMALE;
        }
        evolveTrigger = false;
        totalEVs = 0;
        uniqueID = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);

    }

    public Pokémon(int level, Owner owner, Item item, InputHelper inputHelper, GamePrinter gamePrinter) {
        this.level = level;
        for (int i = 0; i < EVs.length; i++) {
            EVs[i] = 0;
        }
        for (int i = 0; i < IVs.length; i++) {
            IVs[i] = ThreadLocalRandom.current().nextInt(0, MAX_IV + 1);
        }
        currentXP = getXPAtLevel(this.level, getGrowthRate());
        nature = Nature.getRandomNature();
        stats = calculateStats();
        currentHP = stats[HP_INDEX];
        Ability[] potentialAbilities = getPotentialAbilities();
        abilitySlot = ThreadLocalRandom.current().nextInt(potentialAbilities.length);
        ability = potentialAbilities[abilitySlot];
        status = Status.NONE;
        toxicCounter = 1;
        nickname = getSpeciesName();
        critStage = 0;
        sleepTimer = 0;
        try {
            moveSet = getStartingMoves();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        this.owner = owner;
        this.inputHelper = inputHelper;
        this.gamePrinter = gamePrinter;
        this.item = item;
        int genderRoll = ThreadLocalRandom.current().nextInt(1, 101);
        if (getGenderRatio() == -1) {
            gender = Gender.NONE;
        } else if (getGenderRatio() > genderRoll) {
            gender = Gender.MALE;
        } else {
            gender = Gender.FEMALE;
        }
        evolveTrigger = false;
        totalEVs = 0;
        uniqueID = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);

    }

    //For evolving
    public Pokémon (Pokémon mon) {
        this.level = mon.getLevel();
        this.EVs = mon.getEVs();
        this.IVs = mon.getIVs();
        this.currentXP = mon.getCurrentXP();
        this.nature = mon.getNature();
        this.stats = calculateStats();
        float previousHPPercent = (float)mon.getCurrentHP()/ (float)mon.getMaxHP();
        this.currentHP = (int) previousHPPercent*this.getMaxHP();
        abilitySlot = mon.getAbilitySlot();
        ability = getPotentialAbilities()[abilitySlot];
        status = mon.getStatus();
        toxicCounter = 1;
        nickname = mon.getNickname();
        critStage = 0;
        sleepTimer = 0;
        this.owner = mon.getOwner();
        this.inputHelper = mon.inputHelper;
        this.gamePrinter = mon.gamePrinter;
        this.moveSet = mon.getMoveSet();
        if (getLearnset().containsKey(level)) {
            try {
                attemptLearn(getLearnset().get(level));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        this.item = mon.getItem();
        evolveTrigger = false;
        totalEVs = mon.getTotalEVs();
        gender = mon.getGender();
        this.uniqueID = mon.getUniqueID();
    }

    public void takeHit(int dmg) {
        setCurrentHP(currentHP - dmg);
    }

    public void heal(int amt) {
        setCurrentHP(currentHP + amt);
    }



    public void endTurnStatusDamage() {
        if (status == Status.POISONED) {
            setCurrentHP(currentHP - (int) (stats[HP_INDEX] / 8));
        } else if (status == Status.BURNED) {
            setCurrentHP(currentHP - (int) (stats[HP_INDEX] / 8));
        } else if (status == Status.BADLY_POISONED) {
            setCurrentHP(currentHP - (int) ((toxicCounter/16)*stats[HP_INDEX]));
        }

    }

    public void setCurrentHP(int hp) {
        if (hp <= 0) {
            currentHP = 0;
            status = Status.FAINTED;
        } else if (hp > stats[HP_INDEX]){
            currentHP = stats[HP_INDEX];
        } else {
            currentHP = hp;
        }
    }

    public void gainEXP(int exp) {
        //System.out.println("exp check " + (gamePrinter == null));
        //gamePrinter.printMoveLearned(new Tackle());
        gamePrinter.printEXPGain(this, exp);
        currentXP += exp;
        while (currentXP >= getXPAtLevel(level + 1, getGrowthRate()) && level < 100) {
            levelUp();
        }
    }

    protected void levelUp() {
        level += 1;
        gamePrinter.printLevel_UP(this);
        stats = calculateStats();
        Map<Integer, Class<? extends Move>[]> learnset = getLearnset();
        if (learnset.containsKey(level)) {
            try {
                attemptLearn(learnset.get(level));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        if (getEvolveMethod() == EvolveMethod.LEVEL && level >= getEvolveNumber()) {
            evolveTrigger = true;
        }
    }

    protected void attemptLearn(Class<? extends Move>[] moveList) throws IllegalAccessException, InstantiationException {
        for (Class<? extends Move> move : moveList) {
            Move potentialNewMove = move.newInstance();
            if (moveSet.size() < 4) {
                gamePrinter.printMoveLearned(potentialNewMove);
                moveSet.add(potentialNewMove);
            } else {
                gamePrinter.printLearnMoveAttempt(this, potentialNewMove);
                if (inputHelper.getYesOrNo(String.format(NEW_MOVE_PROMPT, this, potentialNewMove, this, this, potentialNewMove))) {
                    int toForget = inputHelper.getMoveToBeDeleted(moveSet);
                    gamePrinter.printMoveOverwrite(this, moveSet.get(toForget), potentialNewMove);
                    moveSet.remove(toForget);
                    moveSet.add(toForget, potentialNewMove);
                } else {
                    gamePrinter.printNoNewMove(this, potentialNewMove);
                }
            }
        }
    }

    public void onCatch() {
        this.owner = Owner.PLAYER;
        if (inputHelper.getYesOrNo("Would you like to give " + this + " a nickname?")) {
            nickname = inputHelper.getNickname(this);
        }
    }


    public void callBack() {
        toxicCounter = 1;
        critStage = 0;
        //If the Pokémon fainted while in the middle of a multi-turn move, it needs to be reset
        //Similar with pursuit
        for (Move move : moveSet) {
            if (move instanceof ChargeMove) {
                if (((ChargeMove) move).getPartOneDone()) {
                    ((ChargeMove) move).togglePartOneDone();
                }
            } else if (move.getClass() == Pursuit.class) {
                ((Pursuit) move).setSpecialEffectActive(false);
            }
        }

    }

    public void incrementToxicCounter() {
        toxicCounter++;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getOwnerMsg() {
        switch (owner) {
            case PLAYER:
                return "Your";
            case WILD:
                return "The wild";
            default:
                return "The opposing";
        }
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    /**
     * When coming out of a battle, update the Pokémon's state to be accurate from the clone that did battle
     * @param clone The clone that actually battled -- should be the exact same class as the caller
     */
    public void integrate(Pokémon clone) {
        currentHP = clone.currentHP;
        currentXP = clone.getCurrentXP();
        level = clone.getLevel();
        moveSet = clone.getMoveSet();
        item = clone.getItem();
        stats = clone.getStats();
        status = clone.getStatus();
        EVs = clone.getEVs();
        sleepTimer = clone.getSleepTimer();
        evolveTrigger = clone.getEvolveTrigger();
        callBack();
    }

    public int getMaxHP() {
        return stats[HP_INDEX];
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean getEvolveTrigger() {
        return evolveTrigger;
    }

    public void setEvolveTrigger(boolean evolveTrigger) {
        this.evolveTrigger = evolveTrigger;
    }

    public void addEVs(int amt, int index) {
        if (totalEVs == MAX_EV_TOTAL) {
            return;
        } else if (EVs[index] == MAX_EV_SINGLE_STAT){
            return;
        } else {
            EVs[index] += amt;
            if (EVs[index] > MAX_EV_SINGLE_STAT) {
                EVs[index] = MAX_EV_SINGLE_STAT;
            }
        }
    }

    public void setEVs(int[] evs) {
        this.EVs = evs;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public boolean equals(Pokémon other) {
        return uniqueID == other.getUniqueID();
    }

    public void fullyHeal() {
        currentHP = stats[0];
        for (Move move : moveSet) {
            move.refillPP();
        }
        this.status = Status.NONE;
    }

    public String toString() {
        return nickname;
    }

    public boolean hasNickname() {
        return this.nickname != null && !this.nickname.equals(this.getSpeciesName());
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    }

    public void setCurrentXP(int xp) {
        this.currentXP = xp;
    }

}

	
	

