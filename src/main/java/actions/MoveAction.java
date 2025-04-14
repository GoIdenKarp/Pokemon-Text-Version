package actions;

import battle.BattleSlot;
import moves.Move;

import java.util.ArrayList;

/**
 * Created by AriG on 6/8/17.
 */
public class MoveAction extends Action {

    private Move move;
    private ArrayList<BattleSlot> targetSlots;
    private boolean wasCrit;

    public MoveAction(BattleSlot user, Move move, ArrayList<BattleSlot> targetSlots) {
        super(user);
        this.move = move;
        this.targetSlots = targetSlots;
        wasCrit = false;
    }

    public MoveAction(BattleSlot user, Move move, BattleSlot targetSlot) {
        super(user);
        this.move = move;
        this.targetSlots = new ArrayList<>();
        targetSlots.add(targetSlot);
        wasCrit = false;
    }

    @Override
    public int getPriority() {
        return move.getPriority();
    }

    public Move getMove() {
        return move;
    }

    public ArrayList<BattleSlot> getTargetSlots() {
        return targetSlots;
    }

    public void setTargetSlots(ArrayList<BattleSlot> targetSlots) {
        this.targetSlots = targetSlots;
    }

    /**
     * Gets the first BattleSlot, to be used when there can only be one that matters
     */
    public BattleSlot getTargetSlot() {
        return targetSlots.get(0);
    }

    /**
     * Easy method to set the target as a single BattleSlot, without having to create an ArrayList
     */
    public void setTargetSlot(BattleSlot slot) {
        targetSlots = new ArrayList<>();
        targetSlots.add(slot);
    }

    public boolean wasCrit() {
        return wasCrit;
    }

    public void setWasCrit(boolean wasCrit) {
        this.wasCrit = wasCrit;
    }
}
