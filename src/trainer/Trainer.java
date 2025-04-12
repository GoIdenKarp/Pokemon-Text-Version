package trainer;

import enums.Owner;
import pokémon.Pokémon;
import pokémon.PokémonFactory;

import java.util.ArrayList;
import java.util.List;

public class Trainer {

	private String type;
	private String name;
	private String greeting;
	//This is what they say when YOU win
	private String winMsg;
	//This is what they say when YOU lose
	private String loseMsg;
	private int prizeMoney;
	private boolean isDoubleBattle;
	private List<PartySlot> party;

	
	public Trainer(String type, String name, String greeting, String winMsg, String loseMsg, int prizeMoney, boolean isDoubleBattle, List<PartySlot> party) {
		this.type = type;
		this.name = name;
		this.greeting = greeting;
		this.winMsg = winMsg;
		this.loseMsg = loseMsg;
		this.prizeMoney = prizeMoney;
		this.isDoubleBattle = isDoubleBattle;
		this.party = party;
	}

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getGreeting() {
	    return greeting;
    }

    public String getWinMsg() {
        return winMsg;
    }

    public String getLoseMsg() {
        return loseMsg;
    }

    public int getPrizeMoney() {
	    return prizeMoney;
    }

    public List<PartySlot> getParty() {
        return party;
    }

    public ArrayList<Pokémon> generateParty(PokémonFactory factory) {
	    ArrayList<Pokémon> toReturn = new ArrayList<>();
	    for (int i = 0; i < party.size(); i++) {
	        PartySlot currentSlot = party.get(i);
	        Pokémon mon = factory.makePokémon(currentSlot.getMon(), currentSlot.getLevel(), Owner.TRAINER);
	        if (currentSlot.getItem() != null) {
	            mon.setItem(currentSlot.getItem());
            }
            if (currentSlot.getMoveSet() != null) {
	            mon.setMoveSet(currentSlot.getMoveSet());
            }
            toReturn.add(mon);
        }
        return toReturn;
    }

    public boolean isDoubleBattle() {
        return isDoubleBattle;
    }

    public String toString() {
	    return type + " " + name;
    }
}
