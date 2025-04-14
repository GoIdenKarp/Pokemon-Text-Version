package game;

import items.Bag;
import pokémon.Pokémon;

import java.util.ArrayList;

public class Player {

    private static final int STARTING_MONEY = 3000;

    private int money;
    private ArrayList<String> badges;
    private String playerName;
    private String rivalName;
    private ArrayList<Pokémon> party;
    private ArrayList<Pokémon> PC;
    private Bag bag;
    Pokémon tempCaughtStorage;


    public Player(String playerName, String rivalName) {
        this.playerName = playerName;
        this.rivalName = rivalName;
        this.money = STARTING_MONEY;
        this.bag = new Bag();
        this.party = new ArrayList<>();
        this.PC = new ArrayList<>();
        this.badges = new ArrayList<>();
        this.tempCaughtStorage = null;
    }

    public Player(String playerName, String rivalName, int money, ArrayList<String> badges, ArrayList<Pokémon> party, ArrayList<Pokémon> PC, Bag bag) {
        this.playerName = playerName;
        this.rivalName = rivalName;
        this.money = money;
        this.badges = badges;
        this.party = party;
        this.PC = PC;
        this.bag = bag;
    }

    public int getMoney() {
        return money;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getRivalName() {
        return rivalName;
    }

    public ArrayList<Pokémon> getParty() {
        return party;
    }

    public Bag getBag() {
        return bag;
    }

    public void addMoney(int toGain) {
        money += toGain;
    }

    public void loseMoney(int toLose) {
        money -= toLose;
    }

    public void setParty(ArrayList<Pokémon> party) {
        this.party = party;
    }

    //We only need to access when we also want to clear it to clear it
    public Pokémon retrieveTempStorage() {
        Pokémon mon = tempCaughtStorage;
        tempCaughtStorage = null;
        return mon;
    }

    public void setTempCaughtStorage(Pokémon tempCaughtStorage) {
        this.tempCaughtStorage = tempCaughtStorage;
    }

    public ArrayList<String> getBadges() {
        return badges;
    }

    public void setBadges(ArrayList<String> badges) {
        this.badges = badges;
    }

    public int getBadgeCount() {
        return badges.size();
    }

    public ArrayList<Pokémon> getPC() {
        return PC;
    }

    public void setPC(ArrayList<Pokémon> PC) {
        this.PC = PC;
    }

    public void addBadge(String badge) {
        this.badges.add(badge);
    }
}
