package actions;

import battle.BattleSlot;

/**
 * Created by AriG on 6/7/17.
 */
public abstract class Action {

    public abstract int getPriority();

    private BattleSlot user;

    public Action(BattleSlot user) {
        this.user = user;
    }

    public BattleSlot getUserSlot() {
        return user;
    }

}
