package actions;

import battle.BattleSlot;
import pokémon.Pokémon;
import trainer.Trainer;

/**
 * Created by AriG on 6/7/17.
 */
public class SwapAction extends Action{


    private static final int PRIORIRTY = 7;

    private Pokémon toSwitchIn;
    private BattleSlot user;
    private Trainer trainer;

    public SwapAction(BattleSlot user, Pokémon toSwitchIn) {
        super(user);
        this.toSwitchIn = toSwitchIn;
        this.trainer = null;
    }

    public SwapAction(BattleSlot user, Pokémon toSwitchIn, Trainer trainer) {
        super(user);
        this.toSwitchIn = toSwitchIn;
        this.trainer = trainer;
    }

    @Override
    public int getPriority() {
        return PRIORIRTY;
    }

    public Pokémon getToSwitchIn() {
        return toSwitchIn;
    }

    public Trainer getTrainer() {
        return trainer;
    }
}
