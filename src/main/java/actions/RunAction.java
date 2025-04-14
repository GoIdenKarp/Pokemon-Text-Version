package actions;

import battle.BattleSlot;

public class RunAction extends Action{

    private static final int PRIORITY = Integer.MAX_VALUE;

    public RunAction(BattleSlot user) {
        super(user);
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
