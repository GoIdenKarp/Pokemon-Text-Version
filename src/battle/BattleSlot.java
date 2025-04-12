package battle;

import actions.Action;
import actions.MoveAction;
import enums.BindingEffect;
import enums.Invulnerabilty;
import enums.Status;
import moves.Move;
import pokémon.Pokémon;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by AriG on 6/8/17.
 */
public class BattleSlot {

    private static final int MIN_CONFUSION_TIME = 1;
    private static final int MAX_CONFUSION_TIME = 4;
    private static final double LOWER_RANDOM_ATTACK_BOUND = 0.85;
    private static final double UPPER_RANDOM_ATTACK_BOUND = 1.000000000000001;
    private static final int LOWER_RANDOM_CRIT_BOUND = 1;
    private static final int UPPER_RANDOM_CRIT_BOUND = 17;
    private static final int LOWER_RANDOM_STAT_BOUND = 1;
    private static final int UPPER_RANDOM_STAT_BOUND = 101;


    private int cfnTimer;
    private boolean confused;
    private boolean infatuated;
    private int[] statMods;
    private ArrayList<BattleSlot> opposingSlots;
    private Pokémon mon;
    private Pokémon original;
    private double attackRoll;
    private int critRoll;
    private int statusRoll;
    private Move lastMoveUsed;
    private Class<? extends Move> lastMoveCalled;
    private boolean didUseMove;
    private Invulnerabilty invulnerabilty;
    private boolean isFlinching;
    private boolean leechSeeded;
    private Map<BindingEffect, Integer> bindingEffects;
    private HashSet<Pokémon> opponents;
    private boolean needsMove;
    private boolean needsAction;
    private boolean usedFocusEnergy;
    private int damageTakenThisTurn;
    private MoveAction moveHitByThisTurn;
    private boolean isRoosting;
    private boolean isRaging;
    private boolean taunted;
    private int tauntCounter;
    private boolean encored;
    private int encoreCounter;
    //If you use your last PP during an encore, encore will end that turn, even if it would normally last longer
    private boolean forceEndEncore;
    private Side side;
    private Action action;
    //private MoveAction tempMoveStorage
    private BattleSlot leachingSlot;
    private boolean flashFire;




    public BattleSlot(Pokémon original, Side side) {
        if (original != null) {
            this.original = original;
            try {
                mon = this.original.copy();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        } else {
            this.original = null;
            this.mon = null;

        }
        //HP, ATK, DEF, SPATK, SPDEF, SPD, ACC, EVAS
        this.statMods = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        cfnTimer = 0;
        confused = false;
        infatuated = false;
        opposingSlots = new ArrayList<>();
        lastMoveCalled = null;
        lastMoveCalled = null;
        didUseMove = false;
        invulnerabilty = Invulnerabilty.NONE;
        isFlinching = false;
        leechSeeded = false;
        bindingEffects = new HashMap<>();
        opponents = new HashSet<>();
        needsMove = true;
        needsAction = true;
        usedFocusEnergy = false;
        damageTakenThisTurn = 0;
        isRoosting = false;
        isRaging = false;
        this.side = side;
        leachingSlot = null;
        taunted = false;
        tauntCounter = 0;
        moveHitByThisTurn = null;
        encored = false;
        encoreCounter = 0;
        forceEndEncore = false;
        flashFire = false;
    }

    public boolean isRaging() {
        return isRaging;
    }

    public void setRaging(boolean raging) {
        isRaging = raging;
    }

    public void setOpposingSlots(ArrayList<BattleSlot> slots) {
        opposingSlots = slots;
    }

    public ArrayList<BattleSlot> getOpposingSlots() {
        return opposingSlots;
    }

    public Pokémon getPokémon() {
        return mon;
    }

    public int getStatMod(int index) {
        return statMods[index];
    }

    public void setStatMod(int index, int newMod) {
        statMods[index] = newMod;
    }

    public void setPokémon(Pokémon incoming) {
        original.integrate(mon);
        original = incoming;
        try {
            this.mon = original.copy();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            this.mon = null;
            this.original = null;
        }
    }

    public boolean isFlashFire() {
        return flashFire;
    }

    public void reset() {
        this.statMods = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        isFlinching = false;
        confused = false;
        infatuated = false;
        bindingEffects = new HashMap<>();
        leechSeeded = false;
        leachingSlot = null;
        opponents = new HashSet<>();
        needsMove = true;
        needsAction = true;
        lastMoveCalled = null;
        lastMoveUsed = null;
        usedFocusEnergy = false;
        damageTakenThisTurn = 0;
        //tempMoveStorage = null;
        action = null;
        taunted = false;
        tauntCounter = 0;
        flashFire = false;
        encored = false;
    }

    public boolean isFainted() {
        return mon.getStatus() == Status.FAINTED;
    }

    public boolean confuse(boolean ignoreSafeguard) {
        if (isFainted()) {
            return false;
        }
        if (confused) {
            return false;
        } else if (getSide().isSafeguardUp() && !ignoreSafeguard) {
            return false;
        } else {
            confused = true;
            cfnTimer = ThreadLocalRandom.current().nextInt(MIN_CONFUSION_TIME, MAX_CONFUSION_TIME + 1);
            return true;
        }


    }

    public boolean isConfused() {
        return confused;
    }

    public boolean isInfatuated() {
        return infatuated;
    }

    //Returns true if confusion ends this turn, false otherwise
    public boolean decreaseCfnTimer() {
        cfnTimer--;
        if (cfnTimer == 0) {
            confused = false;
        }
        return cfnTimer == 0;
    }

    public void setUpRolls() {
        attackRoll = ThreadLocalRandom.current().nextDouble(LOWER_RANDOM_ATTACK_BOUND, UPPER_RANDOM_ATTACK_BOUND);
        critRoll = ThreadLocalRandom.current().nextInt(LOWER_RANDOM_CRIT_BOUND, UPPER_RANDOM_CRIT_BOUND);
        statusRoll = ThreadLocalRandom.current().nextInt(LOWER_RANDOM_STAT_BOUND, UPPER_RANDOM_STAT_BOUND);
    }

    public void reRollCrit() {
        critRoll = ThreadLocalRandom.current().nextInt(LOWER_RANDOM_CRIT_BOUND, UPPER_RANDOM_CRIT_BOUND);
    }

    public double getAttackRoll() {
        return attackRoll;
    }

    public int getCritRoll() {
        return critRoll;
    }

    public int getStatusRoll() {
        return statusRoll;
    }

    public Move getLastMoveUsed() {
        return lastMoveUsed;
    }

    public void setLastMoveUsed(Move lastMoveUsed) {
        this.lastMoveUsed = lastMoveUsed;
    }

    public Class<? extends Move> getLastMoveCalled() {
        return lastMoveCalled;
    }

    public void setLastMoveCalled(Class<? extends Move> lastMoveCalled) {
        this.lastMoveCalled = lastMoveCalled;
    }

    public boolean didUseMove() {
        return didUseMove;
    }

    public void setDidUseMove(boolean didUseMove) {
        this.didUseMove = didUseMove;
    }

    public Invulnerabilty getInvulnerabilty() {
        return invulnerabilty;
    }

    public void setInvulnerabilty(Invulnerabilty invulnerabilty) {
        this.invulnerabilty = invulnerabilty;
    }

    public boolean isFlinching() {
        return isFlinching;
    }

    public void setFlinched() {
        isFlinching = true;
    }

    public void resetFlinch() {
        isFlinching = false;
    }

    public void resetBinding() {
        bindingEffects = new HashMap<>();
    }

    public Map<BindingEffect, Integer> getBindingEffects() {
        return bindingEffects;
    }

    public void addNewBindingEffect(BindingEffect effect, int duration) {
        bindingEffects.put(effect, duration);
    }

    public void removeBindingEffect(BindingEffect effect) {
        bindingEffects.remove(effect);
    }

    public boolean isLeechSeeded() {
        return leechSeeded;
    }

    public void setLeechSeeded(BattleSlot leachingSlot) {
        this.leechSeeded = true;
        this.leachingSlot = leachingSlot;
    }

    public void addOpponent(Pokémon opponent) {
        if (opponent != null) {
            opponents.add(opponent);
        }
    }

    public Set<Pokémon> getOpponents() {
        return opponents;
    }

    public boolean needsMove() {
        return needsMove;
    }

    public void setNeedsMove(boolean needsMove) {
        this.needsMove = needsMove;
    }

    public boolean needsAction() {
        return needsAction;
    }

    public void setNeedsAction(boolean needsAction) {
        this.needsAction = needsAction;
    }

    public void integrate() {
        if (original != null) {
            original.integrate(mon);
        }
    }

    public boolean usedFocusEnergy() {
        return usedFocusEnergy;
    }

    public void setUsedFocusEnergy(boolean usedFocusEnergy) {
        this.usedFocusEnergy = usedFocusEnergy;
    }

    public int getDamageTakenThisTurn() {
        return damageTakenThisTurn;
    }

    public void setDamageTakenThisTurn(int damageTakenThisTurn) {
        this.damageTakenThisTurn = damageTakenThisTurn;
    }

    public boolean isRoosting() {
        return isRoosting;
    }

    public void setRoosting(boolean roosting) {
        isRoosting = roosting;
    }

    public Pokémon getOriginal() {
        return original;
    }

    public Side getSide() {
        return side;
    }

//    public MoveAction getTempMoveStorage() {
//        return tempMoveStorage;
//    }
//
//    public void setTempMoveStorage(MoveAction tempMoveStorage) {
//        this.tempMoveStorage = tempMoveStorage;
//    }

    public BattleSlot getLeachingSlot() {
        return leachingSlot;
    }

    public void removePersonalHazards() {
        leechSeeded = false;
        leachingSlot = null;
        resetBinding();
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public boolean isTaunted() {
        return taunted;
    }

    public void setTaunt() {
        taunted = true;
        tauntCounter = 3;
    }

    public boolean decrementTaunt() {
        if (tauntCounter == 0) {
            return false;
        } else {
            tauntCounter--;
            if (tauntCounter==0) {
                taunted = false;
                return true;
            } else {
                return false;
            }
        }
    }

    public MoveAction getMoveHitByThisTurn() {
        return moveHitByThisTurn;
    }

    public void setMoveHitByThisTurn(MoveAction moveHitByThisTurn) {
        this.moveHitByThisTurn = moveHitByThisTurn;
    }

    public void startTurn() {
        damageTakenThisTurn = 0;
        moveHitByThisTurn = null;
    }

    public boolean isEncored() {
        return encored;
    }

    public void setEncored() {
        encored = true;
        encoreCounter = 3;
    }

    public boolean decrementEncore() {
        if (forceEndEncore) {
            encoreCounter = 0;
            return true;
        } else if (encoreCounter == 0) {
            return false;
        } else {
            encoreCounter--;
            if (encoreCounter == 0) {
                encored = false;
                return true;
            } else {
                return false;
            }
        }
    }

    public void activateFlashFire() {
        this.flashFire = true;
    }

    public void setForceEndEncore(boolean forceEndEncore) {
        this.forceEndEncore = forceEndEncore;
    }
}
