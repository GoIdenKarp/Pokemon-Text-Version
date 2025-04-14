package events;

import trainer.Trainer;

import java.util.List;

public class TrainerEvent extends SubEvent {

    private Trainer trainer;

    public TrainerEvent(Trainer trainer, List<String> beforeMain, List<String> afterMain) {
        super(beforeMain, afterMain);
        this.trainer = trainer;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
}
