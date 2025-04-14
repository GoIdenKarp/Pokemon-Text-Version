package events;

import trainer.PartySlot;
import trainer.Trainer;

import java.util.List;

public class RivalBattleEvent extends SubEvent {

    private List<List<PartySlot>> potentialParties;
    private String winMsg;
    private String greeting;
    private String loseMsg;
    private int prizeMoney;

    public RivalBattleEvent(List<List<PartySlot>> potentialParties, String greeting, String winMsg, String loseMsg,
                            int prizeMoney, List<String> beforeMain, List<String> afterMain) {
        super(beforeMain, afterMain);
        this.potentialParties = potentialParties;
        this.greeting = greeting;
        this.winMsg = winMsg;
        this.loseMsg = loseMsg;
        this.prizeMoney = prizeMoney;
    }

    public Trainer getRival(int party, String name) {
        return new Trainer("Pokémon Trainer", name, greeting, winMsg, loseMsg, prizeMoney, false, potentialParties.get(party));
    }

    public List<List<PartySlot>> getPotentialParties() {
        return potentialParties;
    }

    public void setPotentialParties(List<List<PartySlot>> potentialParties) {
        this.potentialParties = potentialParties;
    }

    public String getWinMsg() {
        return winMsg;
    }

    public void setWinMsg(String winMsg) {
        this.winMsg = winMsg;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getLoseMsg() {
        return loseMsg;
    }

    public void setLoseMsg(String loseMsg) {
        this.loseMsg = loseMsg;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public void setPrizeMoney(int prizeMoney) {
        this.prizeMoney = prizeMoney;
    }
}
