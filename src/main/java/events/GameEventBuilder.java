package events;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameEventBuilder {

    private LinkedList<SubEvent> subEvents;
    private List<String> eventFlagsToLift;
    private List<MovementFlag> movementFlagsToLift;
    private String startFlag;
    private boolean resetOnFail;
    private boolean ignoreFail;

    public GameEventBuilder() {
        subEvents = new LinkedList<>();
        eventFlagsToLift = new ArrayList<>();
        movementFlagsToLift = new ArrayList<>();
        startFlag = "";
        resetOnFail = true;
        ignoreFail = false;
    }

    public void addSubEvents(LinkedList<SubEvent> subEvents) {
        this.subEvents = subEvents;
    }

    public void addEventFlags(List<String> eventFlagsToLift) {
        this.eventFlagsToLift = eventFlagsToLift;
    }

    public void addMovementFlags(List<MovementFlag> movementFlagsToLift) {
        this.movementFlagsToLift = movementFlagsToLift;
    }

    public void addStartFlag(String startFlag) {
        this.startFlag = startFlag;
    }

    public void addResetOnFail(boolean resetOnFail) {
        this.resetOnFail = resetOnFail;
    }

    public void addIgnoreFail(boolean ignoreFail) {
        this.ignoreFail = ignoreFail;
    }

    public GameEvent build() {
        return new GameEvent(subEvents, movementFlagsToLift, eventFlagsToLift, startFlag, resetOnFail, ignoreFail);
    }
}
