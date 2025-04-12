package events;

import java.util.LinkedList;
import java.util.List;

//One "Event" in the game, represented as a list of SubEvents
public class GameEvent {

    //Master list holds all the things that happen in an event
    //If it has to be reset (due to a failure), it can be
    private final LinkedList<SubEvent> masterList;
    private LinkedList<SubEvent> runningEvent;
    private List<MovementFlag> movementFlagsToLift;
    private List<String> eventFlagsToLift;
    private boolean resetOnFail;
    private String canStartFlag;

    public GameEvent(LinkedList<SubEvent> masterList, List<MovementFlag> movementFlagsToLift,
                     List<String> eventFlagsToLift, String canStartFlag) {
        this.masterList = masterList;
        this.runningEvent = masterList;
        this.movementFlagsToLift = movementFlagsToLift;
        this.eventFlagsToLift = eventFlagsToLift;
        this.canStartFlag = canStartFlag;
        resetOnFail = true;
    }

    public GameEvent(LinkedList<SubEvent> masterList, List<MovementFlag> movementFlagsToLift,
                     List<String> eventFlagsToLift) {
        this.masterList = masterList;
        this.runningEvent = masterList;
        this.movementFlagsToLift = movementFlagsToLift;
        this.eventFlagsToLift = eventFlagsToLift;
        this.canStartFlag = null;
        resetOnFail = true;
    }


    public void reset() {
        runningEvent = masterList;
    }

    public SubEvent next() {
        if (runningEvent.isEmpty()) {
            return null;
        } else {
            return runningEvent.peek();
        }
    }

    public boolean isRunning() {
        return !runningEvent.isEmpty();
    }

    /**
     * This method is called whenever the player passes a SubEvent, letting us remove it from the list
     * @return true if the last subEvent was removed (the event is over), false otherwise
     */
    public boolean subEventPassed() {
        runningEvent.poll();
        if (runningEvent.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public LinkedList<SubEvent> getMasterList() {
        return masterList;
    }

    public boolean resetOnFail() {
        return resetOnFail;
    }

    public List<MovementFlag> getMovementFlagsToLift() {
        return movementFlagsToLift;
    }

    public List<String> getEventFlagsToLift() {
        return eventFlagsToLift;
    }

    public String getCanStartFlag() {
        return canStartFlag;
    }
}
