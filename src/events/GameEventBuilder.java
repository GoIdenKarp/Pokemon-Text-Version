package events;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameEventBuilder {

    private LinkedList<SubEvent> subEvents;
    private List<String> eventFlagsToLift;
    private List<MovementFlag> movementFlagsToLift;
    private String startFlag;

    public GameEventBuilder() {
        subEvents = new LinkedList<>();
        eventFlagsToLift = new ArrayList<>();
        movementFlagsToLift = new ArrayList<>();
        startFlag = "";
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

    public GameEvent build() {
        return new GameEvent(subEvents, movementFlagsToLift, eventFlagsToLift, startFlag);
    }
}
